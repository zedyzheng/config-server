package com.ms.platform.server.config.api;

/**
 * Created by Joey on 2017/8/3 0003.
 */
public interface ServerConfigSynService {

    void configSync(Long appNamespaceId) throws Exception;

}
