package com.ms.platform.server.config.dal.entity;

import com.ms.common.jpa.AuditEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * TODO
 * Created by Joey on 2017/11/15 0015.
 */
@Entity
@Table(name = "config_resource")
@EntityListeners({AuditingEntityListener.class})
public class ResourceEntity extends AuditEntity<Long> {

    //主键、自动生成
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false, nullable = false)
    private Long id;

    @Column(name = "name",unique = true)
    private String name;

    //资源类型
    @Column
    private String type;

    //显示顺序
    @Column
    private Integer priority;

    @Column(name = "parent_id")
    private Long parentId;

    //父编号列表
    @Column(name = "parent_ids",nullable=false,length=200)
    private String parentIds;

    //权限字符串
    @Column(name = "permission")
    private String permission;

    @Column(name = "available")
    private Boolean available = Boolean.FALSE;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }
}
