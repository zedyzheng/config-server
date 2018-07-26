package com.ms.platform.server.config.request;

import com.ms.common.utils.page.PageRequestCustom;

/**
 * Created by Joey on 2017/8/3 0003.
 */
public class ServerConfigPageRequest extends PageRequestCustom {

    private Long id;

    //属性key
    private String key;

    private String appId;

    private String appNamespace;

    private Long appNamespaceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Long getAppNamespaceId() {
        return appNamespaceId;
    }

    public void setAppNamespaceId(Long appNamespaceId) {
        this.appNamespaceId = appNamespaceId;
    }

    public String getAppNamespace() {
        return appNamespace;
    }

    public void setAppNamespace(String appNamespace) {
        this.appNamespace = appNamespace;
    }
}
