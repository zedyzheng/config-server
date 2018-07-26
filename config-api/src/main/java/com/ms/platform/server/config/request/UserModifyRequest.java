package com.ms.platform.server.config.request;

import java.io.Serializable;

/**
 * Created by Joey on 2017/8/11 0011.
 */
public class UserModifyRequest implements Serializable {

    //角色权限,多个以,隔开 Permissions
    private String roles;

    private String accessAppId;

    //private Boolean deleted = false;

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getAccessAppId() {
        return accessAppId;
    }

    public void setAccessAppId(String accessAppId) {
        this.accessAppId = accessAppId;
    }
}
