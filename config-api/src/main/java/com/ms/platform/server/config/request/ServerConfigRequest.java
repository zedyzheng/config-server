package com.ms.platform.server.config.request;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Joey on 2017/8/8 0008.
 */
public class ServerConfigRequest implements Serializable {

    //属性key
    private String itemKey;

    //属性值
    @NotEmpty
    private String itemValue;

    private String comment;

    //排序
    private Integer ordered = 1;

    @NotNull
    private Long appNamespaceId;

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

    public Long getAppNamespaceId() {
        return appNamespaceId;
    }

    public void setAppNamespaceId(Long appNamespaceId) {
        this.appNamespaceId = appNamespaceId;
    }

    public Integer getOrdered() {
        return ordered;
    }

    public void setOrdered(Integer ordered) {
        this.ordered = ordered;
    }
}
