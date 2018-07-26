package com.ms.platform.server.config.service.api;

import com.ms.common.bo.exception.BusinessException;
import com.ms.platform.server.config.api.AppNamespaceService;
import com.ms.platform.server.config.api.ServerConfigService;
import com.ms.platform.server.config.api.ServerConfigSynService;
import com.ms.platform.server.config.common.enums.ServiceConfigStatus;
import com.ms.platform.server.config.model.AppNamespace;
import com.ms.platform.server.config.model.ServerConfig;
import com.ms.platform.server.config.service.process.ConfigZkProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Created by Joey on 2017/8/3 0003.
 */
@Service
public class ServerConfigSynServiceImpl implements ServerConfigSynService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ServerConfigService serverConfigService;
    @Autowired
    private AppNamespaceService appNamespaceService;
    @Autowired
    private ConfigZkProcess configZkProcess;

    @Override
    public void configSync(Long appNamespaceId) throws Exception{

        AppNamespace appNamespace = appNamespaceService.getDetail(appNamespaceId);
        if(null == appNamespace){
            throw new BusinessException(BusinessException.Errors.DEFAULT_ERROR,"应用空间记录不存在");
        }

        List<ServerConfig> serverConfigList =  serverConfigService.findByAppNamespaceId(appNamespaceId);
        if(null == serverConfigList || serverConfigList.isEmpty()){
            throw new BusinessException(BusinessException.Errors.DEFAULT_ERROR,"配置列表为空");
        }
        String appId = appNamespace.getAppId();
        try{
            configZkProcess.syncConfig(appId,appNamespace.getName(),appNamespace.getPassword(),serverConfigList);
        }catch (Exception e){
            logger.error("同步配置错误:{}",e);
            throw e;
        }

        appNamespaceService.modifyStatus(appNamespaceId,ServiceConfigStatus.SYNC);
    }


}
