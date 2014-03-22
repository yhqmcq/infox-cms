package com.infox.sysmgr.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.infox.common.util.RandomUtils;

/**
 * 员工信息基本表
 * 创建者： 杨浩泉
 * 创建时间： 2014-3-17 下午9:20:27
 * 版本号： v1.0
 */
@Entity
@Table(name = "INFOX_SYSMGR_USER")
@DynamicUpdate(true)
@DynamicInsert(true)
public class UserEntity {

	private String id ;
	
	private String account ;
	
	private String password ;
	
	/** 账号状态（0，1） */
	private Integer status = new Integer(0);
	
	private Date created = new Date() ;
	
	private UserDetailEntity user_detail ;
	
	private CompanyEntity dept ;
	
	private Set<UserPermitEntity> user_permit = new HashSet<UserPermitEntity>(0) ;
	
	@ManyToOne
	@JoinColumn(name="DEPT_ID")
	public CompanyEntity getDept() {
		return dept;
	}

	public void setDept(CompanyEntity dept) {
		this.dept = dept;
	}

	@OneToMany
	@JoinColumn(name="USER_ID")
	public Set<UserPermitEntity> getUser_permit() {
		return user_permit;
	}

	public void setUser_permit(Set<UserPermitEntity> user_permit) {
		this.user_permit = user_permit;
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="DETAIL_ID")
	public UserDetailEntity getUser_detail() {
		return user_detail;
	}

	public void setUser_detail(UserDetailEntity user_detail) {
		this.user_detail = user_detail;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
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

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
