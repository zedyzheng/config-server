package com.ms.platform.server.config.request;

import com.ms.common.utils.page.PageRequestCustom;

/**
 * Created by Joey on 2017/8/8 0008.
 */
public class OperatorLogsPageRequest extends PageRequestCustom {

    private Long id;

    //表名
    private String entityName;

    //操作类型
    private String operatorName;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
