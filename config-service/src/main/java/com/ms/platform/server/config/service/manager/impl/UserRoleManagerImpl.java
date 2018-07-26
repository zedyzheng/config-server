package com.ms.platform.server.config.service.manager.impl;

import com.ms.common.bo.exception.BusinessException;
import com.ms.common.utils.Md5Utils;
import com.ms.platform.server.config.common.constants.Permissions;
import com.ms.platform.server.config.common.utils.ConfigSupport;
import com.ms.platform.server.config.common.utils.RegexValidateUtil;
import com.ms.platform.server.config.dal.entity.RoleEntity;
import com.ms.platform.server.config.dal.entity.UserEntity;
import com.ms.platform.server.config.dal.repository.RoleDao;
import com.ms.platform.server.config.dal.repository.UserDao;
import com.ms.platform.server.config.request.UserModifyRequest;
import com.ms.platform.server.config.request.UserRequest;
import com.ms.platform.server.config.service.manager.UserRoleManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Joey on 2017/8/11 0011.
 */
@Service
public class UserRoleManagerImpl implements UserRoleManager {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;

    @Transactional
    @Override
    public Long add(UserRequest userRequest) throws BusinessException {
        if(!ConfigSupport.isUserName(userRequest.getUserName())){
            throw new BusinessException(BusinessException.Errors.DEFAULT_ERROR,"username只能包含字母和数字");
        }
        if (RegexValidateUtil.checkPassword(userRequest.getPassword(),6,16)==-1) {
            throw new BusinessException(BusinessException.Errors.DEFAULT_ERROR,"密码格式错误");
        }
        if(null!=userDao.findByUserName(userRequest.getUserName())){
            throw new BusinessException(BusinessException.Errors.DEFAULT_ERROR,"userName已经存在");
        }

        Set<Long> permissionList = new HashSet<>();
        //permissionList.add(Permissions.ROLE_VIEW);
        String inPermissions = userRequest.getRoles();
        if(null!=inPermissions){
            String[] permissions = inPermissions.split(",");
            for (int i = 0; i < permissions.length; i++) {
                permissionList.add(Long.valueOf(permissions[i]));
//                switch (permissions[i]){
//                    case Permissions.ROLE_USER:
//                        permissionList.add(permissions[i]);
//                        break;
//                    case Permissions.ROLE_VIEW:
//                        break;
//                }
            }
        }
        permissionList.remove(1);//不能添加管理员权限

        permissionList.forEach(permission->{
            RoleEntity roleEntity = roleDao.findOne(permission);
            if(null == roleEntity){
                throw new BusinessException(BusinessException.Errors.DEFAULT_ERROR,"权限记录不存在");
            }
        });

        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userRequest.getUserName());
        userEntity.setPassword(Md5Utils.getMD5(userRequest.getPassword()));
        userEntity.setAccessAppId(userRequest.getAccessAppId());
        userEntity.setDeleted(false);
        userEntity.setRoleIds(StringUtils.join(permissionList,","));
        userDao.save(userEntity);

        return userEntity.getId();
    }

    @Transactional
    @Override
    public void modify(long id,UserModifyRequest userRequest) throws BusinessException {
        UserEntity userEntity = userDao.findOne(id);
        if(null==userEntity){
            throw new BusinessException(BusinessException.Errors.DEFAULT_ERROR,"用户不存在");
        }

        Set<Long> permissionList = new HashSet<>();
        //permissionList.add(Permissions.ROLE_VIEW);
        String inPermissions = userRequest.getRoles();
        if(null!=inPermissions){
            String[] permissions = inPermissions.split(",");
            for (int i = 0; i < permissions.length; i++) {
                permissionList.add(Long.valueOf(permissions[i]));
//                switch (permissions[i]){
//                    case Permissions.ROLE_USER:
//                        permissionList.add(permissions[i]);
//                        break;
//                    case Permissions.ROLE_VIEW:
//                        break;
//                }
            }
        }
        permissionList.remove(1);//不能添加管理员权限 Permissions.ROLE_ADMIN

        permissionList.forEach(permission->{
            RoleEntity roleEntity = roleDao.findOne(Long.valueOf(permission));
            if(null == roleEntity){
                throw new BusinessException(BusinessException.Errors.DEFAULT_ERROR,"权限记录不存在");
            }
        });

        userEntity.setRoleIds(StringUtils.join(permissionList,","));
        userEntity.setAccessAppId(userRequest.getAccessAppId());
        userDao.save(userEntity);
    }

    @Override
    public List<String> findRolesByUserId(String roleIds) throws BusinessException {
        List<String> roles = new ArrayList<>();
        String[] lsRoleIds = StringUtils.split(roleIds,",");
        for (int i = 0; i < lsRoleIds.length; i++) {
            RoleEntity roleEntity = roleDao.findOne(Long.valueOf(lsRoleIds[i]));
            roles.add(roleEntity.getName());//ROLE_ADMIN,ROLE_USER
        }

        return roles;
    }
}
