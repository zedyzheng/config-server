package com.ms.platform.server.config.request;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * Created by Joey on 2017/8/8 0008.
 */
public class CommonServerConfigRequest implements Serializable {

    @NotEmpty
    private String appIds;//多个使用,分开

    public String getAppIds() {
        return appIds;
    }

    public void setAppIds(String appIds) {
        this.appIds = appIds;
    }
}
