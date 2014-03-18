package com.infox.sysmgr.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.infox.common.util.RandomUtils;

/**
 * 由Module与Role组成权限角色中间表
 * 创建者： 杨浩泉
 * 创建时间： 2014-3-17 下午9:21:03
 * 版本号： v1.0
 */
@Entity
@Table(name = "INFOX_SYSMGR_ROLE_PERMIT")
@DynamicUpdate(true)
@DynamicInsert(true)
public class RolePermitEntity {

	private String id ;
	
	private Date created = new Date() ;

	private RoleEntity role ;
	
	private ModuleEntity module ;
	
	@ManyToOne
	@JoinColumn(name="MODULE_ID")
	public ModuleEntity getModule() {
		return module;
	}

	public void setModule(ModuleEntity module) {
		this.module = module;
	}
	
	@ManyToOne
	@JoinColumn(name="ROLE_ID")
	public RoleEntity getRole() {
		return role;
	}

	public void setRole(RoleEntity role) {
		this.role = role;
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

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getCreated() {
		return created;
	}
	
}
