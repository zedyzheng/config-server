package com.ms.platform.server.config.dal.entity;

import com.ms.common.jpa.AuditEntity;
import com.ms.common.jpa.EntityCrudEventPublisher;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * 应用表
 * Created by Joey on 2017/7/12 0012.
 */
@Entity
@Table(name = "config_app")
@EntityListeners({AuditingEntityListener.class})
public class AppEntity extends AuditEntity<Long> {

    private static final long serialVersionUID = 1L;

    //主键、自动生成
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false, nullable = false)
    private Long id;

    //应用id 唯一
    @Column(name = "app_id",unique=true)
    private String appId;

    //应用名称
    @Column(name = "name")
    private String name;

    //部门名称
    @Column(name = "org_name")
    private String orgName;

    //所属人
    @Column(name = "owner_name")
    private String ownerName;

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
