package com.ms.platform.server.config.dal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ms.common.jpa.AuditEntity;
import com.ms.common.jpa.EntityCrudEventPublisher;
import com.ms.platform.server.config.common.enums.ServiceConfigStatus;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * 应用namespace定义
 * Created by Joey on 2017/7/12 0012.
 */
@Entity
@Table(name = "config_namespace")
@EntityListeners({AuditingEntityListener.class,EntityCrudEventPublisher.class})
public class AppNamespaceEntity extends AuditEntity<Long> {

    private static final long serialVersionUID = 1L;

    //主键、自动生成
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false, nullable = false)
    private Long id;

    //环境变量 dev,test,pre,prod
    @Column(name = "name")
    private String name;

    //注释
    @Column(name = "comment")
    private String comment;

    @Column(nullable=false,length=60)
    private String password;

    //appId
    @Column(name = "app_id")
    private String appId;

    //同步状态 0:未同步,1:已同步,2:有修改
    @Column(name = "status")
    private Integer status = ServiceConfigStatus.UN_SYNC.ordinal();

    //同步时间
    @Column(name = "sync_date",insertable=false,updatable = false)
    protected Date syncDate;

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

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
