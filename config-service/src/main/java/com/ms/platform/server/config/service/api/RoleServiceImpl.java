package com.ms.platform.server.config.service.api;

import com.ms.common.bo.exception.BusinessException;
import com.ms.platform.server.config.api.RoleService;
import com.ms.platform.server.config.dal.entity.RoleEntity;
import com.ms.platform.server.config.dal.repository.RoleDao;
import com.ms.platform.server.config.model.Role;
import com.ms.platform.server.config.request.RoleRequest;
import com.ms.platform.server.config.service.convert.RoleConverter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joey on 2017/11/10 0010.
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public List<Role> findAll() throws BusinessException {

        List<Role> roleList = new ArrayList<>();
        List<RoleEntity> roleEntityList =  roleDao.findAll();
        roleEntityList.forEach(roleEntity -> {
            Role role = new Role();
            BeanUtils.copyProperties(roleEntity,role);
            roleList.add(role);
        });

        return roleList;
    }

    @Override
    public Long add(RoleRequest roleRequest) throws BusinessException {
        if(null!=roleDao.findByName(roleRequest.getName())){
            throw new BusinessException(BusinessException.Errors.DEFAULT_ERROR,"role已经存在");
        }

        RoleEntity roleEntity = RoleConverter.toAppEntity(roleRequest);
        roleDao.save(roleEntity);
        return roleEntity.getId();
    }

    @Transactional
    @Override
    public void modify(Long id, RoleRequest roleRequest) throws BusinessException {
        if(null!=roleRequest.getName()){
            if(null!=roleDao.findByName(roleRequest.getName())){
                throw new BusinessException(BusinessException.Errors.DEFAULT_ERROR,"role已经存在");
            }
        }
        RoleEntity roleEntity = roleDao.findOne(id);
        if(null==roleEntity){
            throw new BusinessException(BusinessException.Errors.DEFAULT_ERROR,"角色不存在");
        }
        //if(null!=roleRequest.getName()){
        //    roleEntity.setName(roleRequest.getName());
        //}
        if(null!=roleRequest.getDescription()){
            roleEntity.setDescription(roleRequest.getDescription());
        }
        if(null!=roleRequest.getResourceIds()){
            roleEntity.setResourceIds(roleRequest.getResourceIds());
        }
        if(null!=roleRequest.getAvailable()){
            roleEntity.setAvailable(roleRequest.getAvailable());
        }
        roleDao.save(roleEntity);
    }

    @Override
    public List<Role> findRolesByIds(String roleIds) throws BusinessException {
        List<Role> roles = new ArrayList<>();
        String[] lsRoleIds = StringUtils.split(roleIds,",");
        for (int i = 0; i < lsRoleIds.length; i++) {
            RoleEntity roleEntity = roleDao.findOne(Long.valueOf(lsRoleIds[i]));

            Role role = new Role();
            BeanUtils.copyProperties(roleEntity,role);

            roles.add(role);//ROLE_ADMIN,ROLE_USER
        }

        return roles;
    }
}
