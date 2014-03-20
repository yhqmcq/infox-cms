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
 * 用户授权表（用户ID与角色、职位、项目、部门、及直接授予的权限）
 * 创建者： 杨浩泉
 * 创建时间： 2014-3-20 下午4:20:56
 * 版本号： v1.0
 */
@Entity
@Table(name = "INFOX_SYSMGR_USER_PERMIT")
@DynamicUpdate(true)
@DynamicInsert(true)
public class UserPermitEntity {
	
	private String id ;
	
	/** 用户 */
	private UserEntity user ;
	
	/** 角色 */
	private RoleEntity role ;
	
	/** 部门 */
	private CompanyEntity dept ;
	
	private Date created = new Date() ;

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

	@ManyToOne
	@JoinColumn(name="USER_ID")
	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	@ManyToOne
	@JoinColumn(name="ROLE_ID")
	public RoleEntity getRole() {
		return role;
	}

	public void setRole(RoleEntity role) {
		this.role = role;
	}

	@ManyToOne
	@JoinColumn(name="DEPT_ID")
	public CompanyEntity getDept() {
		return dept;
	}

	public void setDept(CompanyEntity dept) {
		this.dept = dept;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
	

}
