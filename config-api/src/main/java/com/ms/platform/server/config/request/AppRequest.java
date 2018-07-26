package com.ms.platform.server.config.request;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * Created by Joey on 2017/8/8 0008.
 */
public class AppRequest implements Serializable {

    //应用id 唯一
    @NotEmpty
    private String appId;

    //应用名称
    @NotEmpty
    private String name;

    //部门名称
    @NotEmpty
    private String orgName;

    //所属人
    private String ownerName;

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

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
