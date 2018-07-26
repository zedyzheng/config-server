package com.ms.platform.server.config.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 日志审计表
 * Created by Joey on 2017/8/3 0003.
 */
public class OperatorLogs implements Serializable {

    private static final long serialVersionUID = 1L;

    //主键、自动生成
    private Long id;

    //表名
    private String entityName;

    //操作类型
    private String operatorName;

    private String description;

    private Date lastModifiedDate;

    protected Long createBy;

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

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }
}
