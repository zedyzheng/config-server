package com.ms.platform.server.config.dal.entity;

import com.ms.common.jpa.AuditEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * 日志审计表
 * Created by Joey on 2017/8/3 0003.
 */
@Entity
@Table(name = "config_operator_logs")
@EntityListeners({AuditingEntityListener.class})
public class OperatorLogsEntity extends AuditEntity<Long> {

    private static final long serialVersionUID = 1L;

    //主键、自动生成
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false, nullable = false)
    private Long id;

    //表名
    @Column(name = "entity_name")
    private String entityName;

    //操作类型
    @Column(name = "operator_name")
    private String operatorName;

    //描述
    @Column(name = "description",columnDefinition = "NVARCHAR(MAX)")
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
