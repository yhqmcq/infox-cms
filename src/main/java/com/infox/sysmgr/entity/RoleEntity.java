package com.infox.sysmgr.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.infox.common.util.RandomUtils;

/**
 * 角色表，基本权限的集合。无上级与下级之分
 * 创建者： 杨浩泉
 * 创建时间： 2014-3-17 下午9:21:03
 * 版本号： v1.0
 */
@Entity
@Table(name = "INFOX_SYSMGR_ROLE")
@DynamicUpdate(true)
@DynamicInsert(true)
public class RoleEntity {

	private String id ;
	
	private String name ;
	
	private String description ;
	
	private Date created = new Date() ;
	
	private Set<UserEntity> users = new HashSet<UserEntity>(0) ;
	
	private Set<ModuleEntity> modules = new HashSet<ModuleEntity>(0) ;
	
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "INFOX_SYSMGR_ROLE_MODULE", joinColumns = { @JoinColumn(name = "ROLE_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "MODULE_ID", nullable = false, updatable = false) })
	public Set<ModuleEntity> getModules() {
		return modules;
	}

	public void setModules(Set<ModuleEntity> modules) {
		this.modules = modules;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "INFOX_SYSMGR_USER_ROLE_PERMIT", joinColumns = { @JoinColumn(name = "ROLE_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "USER_ID", nullable = false, updatable = false) })
	public Set<UserEntity> getUsers() {
		return users;
	}

	public void setUsers(Set<UserEntity> users) {
		this.users = users;
	}

	@Id
	public String getId() {
		if (this.id != null) {
			return this.id;
		}
		return RandomUtils.generateNumber(6);
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
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

	
}
