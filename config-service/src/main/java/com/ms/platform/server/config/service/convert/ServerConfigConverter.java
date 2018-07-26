package com.ms.platform.server.config.service.convert;

import com.ms.platform.server.config.dal.entity.ServerConfigEntity;
import com.ms.platform.server.config.request.ServerConfigRequest;
import org.springframework.beans.BeanUtils;

/**
 * Created by Joey on 2017/8/3 0003.
 */
public class ServerConfigConverter {

    public static ServerConfigEntity toAppEntity(ServerConfigRequest serverConfig) {
        ServerConfigEntity appEntity = new ServerConfigEntity();
        BeanUtils.copyProperties(serverConfig, appEntity);

        return appEntity;
    }

}
