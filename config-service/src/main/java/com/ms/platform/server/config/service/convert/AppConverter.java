package com.ms.platform.server.config.service.convert;

import com.ms.platform.server.config.dal.entity.AppEntity;
import com.ms.platform.server.config.model.App;
import com.ms.platform.server.config.request.AppRequest;
import org.springframework.beans.BeanUtils;

/**
 * Created by Joey on 2017/8/3 0003.
 */
public class AppConverter {

    public static AppEntity toAppEntity(AppRequest app){
        AppEntity appEntity = new AppEntity();
        BeanUtils.copyProperties(app,appEntity);
        return appEntity;
    }

}
