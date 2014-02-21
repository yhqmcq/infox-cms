package com.infox.sysmgr.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "INFOX_SYSMGR_ORG")
@DynamicUpdate(true)
@DynamicInsert(true)
public class OrganizationEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String id ;
	
	/** 组织名称 */
	private String fullname ;
	
	/** 组织编码 */
	private String code ;
	
	/** 英文名称 */
	private String ename ;
	
	/** 简称 */
	private String sname ;

	/** 组织图标 */
	private String iconCls ;
	
	/** 电话 */
	private String tel;
	
	/** 传真 */
	private String fax;
	
	/** 描述 */
	private String description ;
	
	/** 创建者 */
	private String creater ;
	
	/** 修改者 */
	private String modifyer ;
	
	/** 父组织名称 */
	private String pname ;
	
	private Date created = new Date() ;
	
	private Date lastmod = new Date() ;
	
	private OrganizationEntity org ;
	
	private Set<OrganizationEntity> orgs = new HashSet<OrganizationEntity>() ;
	
	private Set<EmployeeEntity> users = new HashSet<EmployeeEntity>() ;
	
	@OneToMany(mappedBy = "org", fetch = FetchType.LAZY)
	@OrderBy("created desc")
	public Set<EmployeeEntity> getUsers() {
		return users;
	}

	public void setUsers(Set<EmployeeEntity> users) {
		this.users = users;
	}

	@ManyToOne
	@JoinColumn(name = "ORG_PID")
	public OrganizationEntity getOrg() {
		return org;
	}

	public void setOrg(OrganizationEntity org) {
		this.org = org;
	}

	@OneToMany(mappedBy = "org", fetch = FetchType.LAZY)
	@OrderBy("created desc")
	public Set<OrganizationEntity> getOrgs() {
		return orgs;
	}

	public void setOrgs(Set<OrganizationEntity> orgs) {
		this.orgs = orgs;
	}

	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	
}
