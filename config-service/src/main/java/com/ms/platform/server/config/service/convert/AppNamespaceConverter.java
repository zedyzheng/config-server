package com.ms.platform.server.config.service.convert;

import com.ms.common.utils.Md5Utils;
import com.ms.platform.server.config.dal.entity.AppNamespaceEntity;
import com.ms.platform.server.config.model.AppNamespace;
import com.ms.platform.server.config.request.AppNamespaceRequest;
import org.springframework.beans.BeanUtils;

/**
 * Created by Joey on 2017/8/3 0003.
 */
public class AppNamespaceConverter {

    public static AppNamespaceEntity toAppEntity(AppNamespaceRequest appNamespace){
        AppNamespaceEntity appEntity = new AppNamespaceEntity();
        BeanUtils.copyProperties(appNamespace,appEntity);
        appEntity.setPassword(Md5Utils.getMD5(appNamespace.getPassword()));
        return appEntity;
    }

}
