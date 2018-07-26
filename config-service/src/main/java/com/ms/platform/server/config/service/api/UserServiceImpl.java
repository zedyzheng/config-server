package com.ms.platform.server.config.service.api;

import com.ms.common.bo.exception.BusinessException;
import com.ms.common.utils.Md5Utils;
import com.ms.common.utils.page.PageCustom;
import com.ms.platform.server.config.api.RoleService;
import com.ms.platform.server.config.api.UserService;
import com.ms.platform.server.config.common.utils.RegexValidateUtil;
import com.ms.platform.server.config.dal.entity.UserEntity;
import com.ms.platform.server.config.dal.repository.UserDao;
import com.ms.platform.server.config.model.Role;
import com.ms.platform.server.config.model.SysUser;
import com.ms.platform.server.config.request.PasswordsAdminRequest;
import com.ms.platform.server.config.request.UserModifyRequest;
import com.ms.platform.server.config.request.UserPageRequest;
import com.ms.platform.server.config.request.UserRequest;
import com.ms.platform.server.config.service.manager.UserRoleManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joey on 2017/8/11 0011.
 */
@Service
public class UserServiceImpl implements UserService {

    //private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserRoleManager userManager;
    @Autowired
    private RoleService roleService;
//    @Autowired
//    private CacheManage cacheManage;

    @Override
    public SysUser findByUserId(long id) {

        UserEntity userEntity = userDao.findOne(id);
        if(userEntity==null){
            throw new BusinessException(BusinessException.Errors.DEFAULT_ERROR, "记录不存在");
        }

        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userEntity,sysUser);
        if(null!=userEntity.getRoleIds()){
            List<Role> roleNameList = roleService.findRolesByIds(userEntity.getRoleIds());
            if(null!=roleNameList){
                sysUser.setRoles(roleNameList);
            }
        }
        return sysUser;
    }

    //    @Override
//    public SysUser findByUserName(String userName) {
//        return null;
//    }
//
    @Override
    public SysUser findByUserNameAndPassword(String userName, String password) {

        //logger.info("{},{}",userName,Md5Utils.getMD5(password));
        UserEntity userEntity = userDao.findByUserName(userName);
        if(userEntity==null){
            throw new BusinessException(BusinessException.Errors.DEFAULT_ERROR, "用户不存在");
        }
        if(!userEntity.getPassword().equals(Md5Utils.getMD5(password))){
            throw new BusinessException(BusinessException.Errors.DEFAULT_ERROR, "密码错误");
        }
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userEntity,sysUser);
        if(null!=userEntity.getRoleIds()){
            List<Role> roleNameList = roleService.findRolesByIds(userEntity.getRoleIds());
            if(null!=roleNameList){
                sysUser.setRoles(roleNameList);
            }
        }
        return sysUser;
    }

    @Override
    public Long addUserAndSelectRole(UserRequest userRequest) throws BusinessException {
        return userManager.add(userRequest);
    }

    @Override
    public void resetPassword(PasswordsAdminRequest passwords) {
        if (RegexValidateUtil.checkPassword(passwords.getNewPassword(),6,16)==-1) {
            throw new BusinessException(BusinessException.Errors.DEFAULT_ERROR,"密码格式错误");
        }
        UserEntity userEntity = userDao.findOne(passwords.getUserId());
        userEntity.setPassword(Md5Utils.getMD5(passwords.getNewPassword()));
        //AuditorProcess.fillAuditEntityAuditor(userEntity,false);
        userDao.save(userEntity);
    }

    @Override
    public void modify(long id,UserModifyRequest userRequest) throws BusinessException {
        userManager.modify(id,userRequest);
    }

    @Transactional
    @Override
    public void delete(Long id) throws BusinessException {
        UserEntity userEntity = userDao.findOne(id);
        if(userEntity.getUserName().equals("admin")){
            throw new BusinessException(BusinessException.Errors.DEFAULT_ERROR,"不能删除管理员");
        }
        userEntity.setDeleted(true);
        //AuditorProcess.fillAuditEntityAuditor(userEntity,false);
        userDao.save(userEntity);
    }

    @Override
    public PageCustom<SysUser> pages(UserPageRequest pageRequest) throws BusinessException {
        if(pageRequest.getPageSize()<=0 || pageRequest.getPageSize()>200){
            throw new BusinessException(BusinessException.Errors.UN_PROCESSABLE, "pageSize最大值为200");
        }

        Specification<UserEntity> specification = (root, query, cb) -> {
            List<Predicate> all = new ArrayList<>();
            // 获取未删除的属性
            if (null!=pageRequest.getId() && pageRequest.getId()>0) {
                all.add(cb.equal(root.get("id").as(Long.class), pageRequest.getId()));
            }
            if (!StringUtils.isEmpty(pageRequest.getUserName())) {
                all.add(cb.equal(root.get("userName").as(String.class), pageRequest.getUserName()));
            }
            all.add(cb.equal(root.get("deleted").as(Boolean.class),false));
            Predicate[] predicates = new Predicate[all.size()];
            return cb.and(all.toArray(predicates));
        };

        Pageable pageable = new PageRequest(pageRequest.getPageIndex() - 1, pageRequest.getPageSize(),
                new Sort(Sort.Direction.DESC, "lastModifiedDate"));

        List<SysUser> excelTaskList = new ArrayList<>();
        Page<UserEntity> pages = userDao.findAll(specification, pageable);

        if (pages != null && pages.getSize() > 0) {
            pages.forEach(userEntity -> {
                SysUser sysUser = new SysUser();
                BeanUtils.copyProperties(userEntity,sysUser);
                if(null!=userEntity.getRoleIds()){
                    List<Role> roleNameList = roleService.findRolesByIds(userEntity.getRoleIds());
                    if(null!=roleNameList){
                        sysUser.setRoles(roleNameList);
                    }
                }
                excelTaskList.add(sysUser);
            });
        }

        return new PageCustom<>(pages != null ? pages.getTotalElements() : 0, pageRequest, excelTaskList);
    }
}
