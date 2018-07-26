package com.ms.platform.server.config.service.convert;

import com.ms.platform.server.config.dal.entity.RoleEntity;
import com.ms.platform.server.config.request.RoleRequest;
import org.springframework.beans.BeanUtils;

/**
 * Created by Joey on 2017/8/3 0003.
 */
public class RoleConverter {

    public static RoleEntity toAppEntity(RoleRequest app){
        RoleEntity appEntity = new RoleEntity();
        BeanUtils.copyProperties(app,appEntity);
        return appEntity;
    }

}
