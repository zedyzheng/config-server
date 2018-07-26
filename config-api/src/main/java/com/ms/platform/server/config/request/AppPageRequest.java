package com.ms.platform.server.config.request;

import com.ms.common.utils.page.PageRequestCustom;

/**
 * Created by Joey on 2017/8/3 0003.
 */
public class AppPageRequest extends PageRequestCustom {

    private Long id;

    private String appId;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
