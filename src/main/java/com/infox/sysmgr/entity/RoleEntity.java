package com.infox.sysmgr.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
	
	private String roleName ;
	
	private String roleDescription ;
	
	private Date created = new Date() ;
	
	private Set<RolePermitEntity> permits = new HashSet<RolePermitEntity>(0) ;
	
	
	@OneToMany
	@JoinColumn(name="ROLE_ID")
	public Set<RolePermitEntity> getPermits() {
		return permits;
	}

	public void setPermits(Set<RolePermitEntity> permits) {
		this.permits = permits;
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

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}
	
}
