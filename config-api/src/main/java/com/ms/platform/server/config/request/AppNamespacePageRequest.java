package com.ms.platform.server.config.request;

import com.ms.common.utils.page.PageRequestCustom;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by Joey on 2017/8/3 0003.
 */
public class AppNamespacePageRequest extends PageRequestCustom {

    private Long id;

    private String appId;

    private String name;

    //同步状态 0:未同步,1:已同步,2:有修改  ServiceConfigStatus
    @ApiModelProperty("同步状态 0:未同步,1:已同步,2:有修改")
    private Integer status;

//    @ApiModelProperty("同步时间")
//    private Date syncDate;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
