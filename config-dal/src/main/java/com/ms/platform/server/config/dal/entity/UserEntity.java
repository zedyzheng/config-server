package com.ms.platform.server.config.dal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ms.common.jpa.AuditEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;

@Entity
@Table(name = "config_user")
@EntityListeners({AuditingEntityListener.class})
public class UserEntity extends AuditEntity<Long> {

	private static final long serialVersionUID = 1L;

	//主键、自动生成
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", insertable = false, updatable = false, nullable = false)
	private Long id;

	@Column(name = "user_name",unique=true,nullable=false,length=30)
	private String userName;
	
	@Column(nullable=false,length=200)
	private String password;

	@Column(nullable=false,length=200)
	private String salt;

	@Column(name = "role_ids",length=200)
	private String roleIds;

	@Column(name = "access_appid",length=200)
	private String accessAppId;

	@Column(name = "deleted")
	private Boolean deleted = Boolean.FALSE;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getAccessAppId() {
		return accessAppId;
	}

	public void setAccessAppId(String accessAppId) {
		this.accessAppId = accessAppId;
	}
}
