package com.ms.platform.server.config.service.api;

import com.ms.common.bo.exception.BusinessException;
import com.ms.common.utils.Md5Utils;
import com.ms.common.web.exception.NoAuthorityException;
import com.ms.platform.server.config.api.AuthService;
import com.ms.platform.server.config.api.UserService;
import com.ms.platform.server.config.common.constants.Constants;
import com.ms.platform.server.config.common.constants.Permissions;
import com.ms.platform.server.config.common.enums.OperationType;
import com.ms.platform.server.config.common.utils.RegexValidateUtil;
import com.ms.platform.server.config.dal.entity.UserEntity;
import com.ms.platform.server.config.dal.repository.UserDao;
import com.ms.platform.server.config.model.Role;
import com.ms.platform.server.config.model.SysUser;
import com.ms.platform.server.config.model.Token;
import com.ms.platform.server.config.request.LoginRequest;
import com.ms.platform.server.config.request.PasswordsRequest;
import com.ms.platform.server.config.service.manager.UserRoleManager;
import com.ms.platform.server.config.service.security.TokenProvider;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserDao userDao;
	@Autowired
	private UserRoleManager userRoleManager;
	@Autowired
	private UserService userService;

	@Autowired
	private TokenProvider tokenProvider;
//	@Autowired
//    private Environment environment;
	@Value("${security.jwt.expirationInSeconds}")
	private Integer expiration;

	/**
	 * 使用用户名和密码登录
	 * @return token
	 */
	@Override
	public Token login(@NotNull LoginRequest loginRequest) {

		if (StringUtils.isEmpty(loginRequest.getUserName())
				|| StringUtils.isEmpty(loginRequest.getPassword())) {
			throw new BusinessException(BusinessException.Errors.UN_PROCESSABLE, "用户名、密码不能为空");
		}
		if (RegexValidateUtil.checkPassword(loginRequest.getPassword(),6,16)==-1) {
			throw new BusinessException(BusinessException.Errors.UN_PROCESSABLE, "密码格式错误");
		}

		SysUser user = userService.findByUserNameAndPassword(loginRequest.getUserName(),loginRequest.getPassword());
		if(user==null){
			throw new BusinessException(BusinessException.Errors.UN_PROCESSABLE, "用户或者密码不正确");
		}
		if(null!=user.isDeleted() && user.isDeleted()){
			throw new BusinessException(BusinessException.Errors.UN_PROCESSABLE, "用户已经被删除");
		}

		return createToken(user.getId(),user.getRoles(),user.getAccessAppId());
	}

	/**
	 * 刷新token.
	 * @param strToken 旧strToken
	 * @return 新strToken
	 */
	@Override
	public Token refreshToken(@NotNull String strToken) throws BusinessException{
		//logger.info("请求参数:{}",strToken);
		long userId = getTokenUserId(strToken);
		UserEntity userEntity = userDao.findOne(userId);
		if(userEntity==null){
			throw new BusinessException(BusinessException.Errors.UN_PROCESSABLE, "用户不存在");
		}
		String accessToken = this.tokenProvider.refreshToken(strToken);
		if(accessToken==null){
			throw new BusinessException(BusinessException.Errors.UN_PROCESSABLE, "TOKEN无效");
		}
		Token newToken = new Token(accessToken,expiration,userId);
		return newToken;
	}
	
	private Token createToken(long userId,List<Role> roles,String accessAppId){

		List<String> roleNames = new ArrayList<>();
		roles.forEach(role->{
			roleNames.add(role.getName());
		});

		//拼装accessToken
		String accessToken = tokenProvider.createToken(userId+"",roleNames,accessAppId);
		Token newToken = new Token(accessToken,expiration,userId);
		return newToken;
	}

	/**
	 * 查询登录信息
	 * 
	 * @param strToken
	 * @return
	 */
	@Override
	public long getTokenUserId(@NotNull String strToken) throws BusinessException{
		//logger.info("请求参数:{}",strToken);
		String strUserId = this.tokenProvider.getTokenUserId(strToken);
		if(null == strUserId){
			throw new BusinessException(BusinessException.Errors.UN_PROCESSABLE, "TOKEN无效");
		}
		long userId = Long.valueOf(strUserId);
		return userId;
	}
	
//	/**
//	 * 注销登录
//	 *
//	 * @param strToken
//	 */
//	@Override
//	public void logout(@NotNull String strToken) {
//		baseCacheRepository.delete(CachePrefixConstants.YMC_BASE_TOKEN,strToken);
//	}

	/**
	 * 通过旧密码修改密码
	 */
	@Transactional
	@Override
	public void modifyPassword(@NotNull PasswordsRequest passwords) throws BusinessException{

		if (RegexValidateUtil.checkPassword(passwords.getOldPassword(),6,16)==-1) {
			throw new BusinessException(BusinessException.Errors.UN_PROCESSABLE, "旧密码格式错误");
		}
		if (RegexValidateUtil.checkPassword(passwords.getNewPassword(),6,16)==-1) {
			throw new BusinessException(BusinessException.Errors.UN_PROCESSABLE, "密码格式错误");
		}

		UserEntity userEntity = userDao.findByUserName(passwords.getUserName());
		if(userEntity == null){
			throw new BusinessException(BusinessException.Errors.UN_PROCESSABLE, "用户不存在");
		}

		String dbPassword = userEntity.getPassword();
		if(dbPassword.compareTo(Md5Utils.getMD5(passwords.getOldPassword()))!=0){
			throw new BusinessException(BusinessException.Errors.UN_PROCESSABLE, "密码错误");
		}
		userEntity.setPassword( Md5Utils.getMD5(passwords.getNewPassword()));
		userDao.save(userEntity);
	}

	@Override
	public void checkToken(String strToken,String strOperationType,String appId) throws BusinessException {
		//logger.info("请求参数:{}",strToken);
		if (StringUtils.isEmpty(strToken)) {
			throw new NoAuthorityException("token is null");
		}
		try{
			Claims claims = this.tokenProvider.parseJWT(strToken);
			if(null == claims){
				throw new NoAuthorityException("无效的token");
			}
			if(!claims.containsKey(Constants.CLAIM_KEY_ROLE)){
				throw new NoAuthorityException("无效的token");
			}
			Object objAccessAppId = claims.get(Constants.CLAIM_KEY_ACCESS_APPID);
			if(null!=objAccessAppId && null!=appId && !objAccessAppId.toString().contains(appId)){
				throw new NoAuthorityException("没有应用访问权限");
			}
			List<String> strRoles = claims.get(Constants.CLAIM_KEY_ROLE,List.class);
			if(null == strRoles){
				throw new NoAuthorityException("没有操作权限");
			}
			if(null!=strOperationType){
				OperationType operationType = OperationType.getResultEnum(strOperationType);
				if(null == operationType){
					throw new NoAuthorityException("无效的operationType");
				}
				if(operationType == OperationType.READ || operationType == OperationType.LOAD){
					if(!strRoles.contains(Permissions.ROLE_VIEW)){
						throw new NoAuthorityException("没有操作权限");
					}
				}else{
					if(!strRoles.contains(Permissions.ROLE_ADMIN) && !strRoles.contains(Permissions.ROLE_USER)){
						throw new NoAuthorityException("没有操作权限");
					}
				}
			}
		}catch (Exception e){
			throw new NoAuthorityException("无效的token");
		}
	}

}
