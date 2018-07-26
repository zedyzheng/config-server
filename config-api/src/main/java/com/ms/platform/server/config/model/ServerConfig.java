package com.ms.platform.server.config.model;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

/**
 * Created by Joey on 2017/7/12 0012.
 */
public class ServerConfig {

    private Long id;

    //属性key
    @NotEmpty
    private String itemKey;

    //属性值
    @NotEmpty
    private String itemValue;

    private String comment;

    private Integer ordered;

    private String appId;

    @NotEmpty
    private Long appNamespaceId;

    private Date lastModifiedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemKey() {
        return itemKey;
    }

    public void setItemKey(String itemKey) {
        this.itemKey = itemKey;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
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

    public Long getAppNamespaceId() {
        return appNamespaceId;
    }

    public void setAppNamespaceId(Long appNamespaceId) {
        this.appNamespaceId = appNamespaceId;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Integer getOrdered() {
        return ordered;
    }

    public void setOrdered(Integer ordered) {
        this.ordered = ordered;
    }

    @Override
    public String toString() {
        return "ServerConfig{" +
                "id=" + id +
                ", itemKey='" + itemKey + '\'' +
                ", itemValue='" + itemValue + '\'' +
                ", comment='" + comment + '\'' +
                ", ordered=" + ordered +
                ", appId='" + appId + '\'' +
                ", appNamespaceId=" + appNamespaceId +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}
