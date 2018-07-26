package com.ms.platform.server.config.dal.entity;

import com.ms.common.jpa.AuditEntity;
import com.ms.common.jpa.EntityCrudEventPublisher;
import org.hibernate.annotations.Nationalized;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * 配置项目
 * Created by Joey on 2017/7/12 0012.
 */
@Entity
@Table(name = "config_serverconfig")
@EntityListeners({AuditingEntityListener.class,EntityCrudEventPublisher.class})
public class ServerConfigEntity extends AuditEntity<Long> {

    private static final long serialVersionUID = 1L;

    //主键、自动生成
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false, nullable = false)
    private Long id;

    //属性key
    @Column(name = "item_key")
    private String itemKey;

    //属性值
    @Column(name = "item_value",columnDefinition = "NVARCHAR(MAX)")
    private String itemValue;

    //备注
    @Column(name = "comment")
    private String comment;

    //排序
    @Column(name = "ordered")
    private Integer ordered;

    //appId
    @Column(name = "app_id")
    private String appId;

    //
    @Column(name = "app_namespace_id")
    private Long appNamespaceId;

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

    public Integer getOrdered() {
        return ordered;
    }

    public void setOrdered(Integer ordered) {
        this.ordered = ordered;
    }
}
