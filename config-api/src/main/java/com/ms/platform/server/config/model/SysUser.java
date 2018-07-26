package com.ms.platform.server.config.model;

import java.util.Date;
import java.util.List;

public class SysUser {

	private Long id;

	private String userName;
	
//	@JsonIgnore
//	private String password;

	private String salt;

	private List<Role> roles;

	private String accessAppId;

	private Boolean deleted;

	private Date lastModifiedDate;

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

	public Boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getAccessAppId() {
		return accessAppId;
	}

	public void setAccessAppId(String accessAppId) {
		this.accessAppId = accessAppId;
	}
}
