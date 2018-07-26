package com.ms.platform.server.config.service.process;


import com.ms.common.bo.exception.BusinessException;
import com.ms.common.web.annotation.Authority;
import com.ms.common.web.exception.NoAuthorityException;
import com.ms.common.web.processor.AuthorityProcess;
import com.ms.platform.server.config.common.constants.Constants;
import com.ms.platform.server.config.common.constants.Permissions;
import com.ms.platform.server.config.service.security.TokenProvider;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 自定义权限处理器，通过请求头中的Token来验证是否有权限
 */
@Component
public class AuthorityProcessor implements AuthorityProcess {

	@Autowired
	private TokenProvider tokenProvider;

	@Override
	public void validationByPermissions(HttpServletRequest servletRequest,Method method) throws NoAuthorityException {
		Authority authority = method.getAnnotation(Authority.class);
		if(null==authority){
			return;
		}
		String permission = authority.permission();

		String jwt = resolveToken(servletRequest);
		if (StringUtils.isEmpty(jwt)) {
			throw new NoAuthorityException("token is null");
		}
		Claims claims = null;
		try{
			claims = this.tokenProvider.parseJWT(jwt);
			if(null == claims){
				throw new NoAuthorityException("无效的token");
			}
		}catch (Exception e){
			throw new NoAuthorityException("无效的token");
		}
		boolean a = checkApp(claims);
		if(!a){
			throw new NoAuthorityException(BusinessException.Errors.NO_AUTHORIZATION.getCode(),"没有应用访问权限");
		}

		boolean b = checkRoles(claims,permission,servletRequest);
		if(!b){
			throw new NoAuthorityException(BusinessException.Errors.NO_AUTHORIZATION.getCode(),"没有权限执行这一操作");
		}

		servletRequest.setAttribute(Constants.CLAIM_KEY_USERID, claims.get(Constants.CLAIM_KEY_USERID));
	}
	
    private String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader(Constants.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            String jwt = bearerToken.substring(7, bearerToken.length());
            return jwt;
        }
        return null;
    }

    @Override
	public void validationByResources(HttpServletRequest request, String resources) throws NoAuthorityException {

	}

	private boolean checkApp(Claims claims){
		Object objAccessAppId = claims.get(Constants.CLAIM_KEY_ACCESS_APPID);
		if(null!=objAccessAppId && !objAccessAppId.toString().contains("config")){
			return false;
		}
		return true;
	}

	//检验权限
	private Boolean checkRoles(Claims claims,String permission,HttpServletRequest servletRequest) {
		servletRequest.setAttribute(Constants.QUERY_FLAG,"true");

		if(Permissions.ROLE_ADMIN.equals(permission) || Permissions.ROLE_USER.equals(permission) || Permissions.ROLE_LOGS.equals(permission)){
			if(claims.containsKey(Constants.CLAIM_KEY_ROLE)){
				List<String> strRoles = claims.get(Constants.CLAIM_KEY_ROLE,List.class);
				if(null == strRoles){
					return false;
				}
//				if(strRoles.contains(Permissions.ROLE_ADMIN)){
//					servletRequest.setAttribute(Constants.QUERY_FLAG,"false");
//				}
				return strRoles.contains(permission);
			}
			return false;
		}
		return true;
	}

}
