package com.infox.sysmgr.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "INFOX_SYSMGR_EMP")
@DynamicUpdate(true)
@DynamicInsert(true)
public class EmployeeEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String id ;
	
	private String account ;
	
	private String password ;
	
	private String status ;
	
	private String truename ;
	
	private String sex ;
	
	private String email ;
	
	private String tel ;
	
	private String description ;
	
	private Date created = new Date() ;
	
	private Date lastmod = new Date() ;
	
	private String creater;
	
	private String modifyer;
	
	private String orgname ;
	
	private String onlineState = "0" ;
	
	private OrganizationEntity org ;
	
	private Set<RoleEntity> roles = new HashSet<RoleEntity>(0) ;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "INFOX_SYSMGR_EMP_ROLE", joinColumns = { @JoinColumn(name = "EMP_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID", nullable = false, updatable = false) })
	public Set<RoleEntity> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleEntity> roles) {
		this.roles = roles;
	}

	public String getOnlineState() {
		return onlineState;
	}

	public void setOnlineState(String onlineState) {
		this.onlineState = onlineState;
	}

	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getLastmod() {
		return lastmod;
	}

	public void setLastmod(Date lastmod) {
		this.lastmod = lastmod;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getModifyer() {
		return modifyer;
	}

	public void setModifyer(String modifyer) {
		this.modifyer = modifyer;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	@ManyToOne
	@JoinColumn(name = "ORG_PID")
	public OrganizationEntity getOrg() {
		return org;
	}

	public void setOrg(OrganizationEntity org) {
		this.org = org;
	}
	
	

}
