package com.ms.platform.server.config.dal.entity;

import com.ms.common.jpa.AuditEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * Created by Joey on 2017/8/11 0011.
 */
@Entity
@Table(name = "config_role")
@EntityListeners({AuditingEntityListener.class})
public class RoleEntity extends AuditEntity<Long> {

    private static final long serialVersionUID = 1L;

    //主键、自动生成
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false, nullable = false)
    private Long id;

    @Column(name = "name",unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "resource_ids",length=200)
    private String resourceIds;

    @Column(name = "available")
    private Boolean available = Boolean.FALSE;

    //@Column(name = "level")
    //private Integer level;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }
}
