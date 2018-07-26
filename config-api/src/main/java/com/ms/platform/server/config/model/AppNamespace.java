package com.ms.platform.server.config.model;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Joey on 2017/7/12 0012.
 */
public class AppNamespace implements Serializable {

    private Long id;

    //环境变量 dev,test,pre,prod
    @NotEmpty
    private String name;

    //注释
    private String comment;

    @ApiModelProperty("访问密码")
    private String password;

    //appId
    @NotEmpty
    private String appId;

    //同步状态 0:未同步,1:已同步,2:有修改  ServiceConfigStatus
    @ApiModelProperty("同步状态 0:未同步,1:已同步,2:有修改")
    private Integer status;

    @ApiModelProperty("同步时间")
    private Date syncDate;

    private Date lastModifiedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getSyncDate() {
        return syncDate;
    }

    public void setSyncDate(Date syncDate) {
        this.syncDate = syncDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
