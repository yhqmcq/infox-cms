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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.infox.common.util.RandomUtils;
/**
 * @程序编写者：杨浩泉
 * @日期：2013-6-4
 * @类说明：角色
 */
@Entity
@Table(name = "INFOX_SYSMGR_ROLE")
@DynamicUpdate(true)
@DynamicInsert(true)
public class RoleEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id ;
	
	private String name ;
	
	private String description ;
	
	private String creater ;
	
	private String modifyer ;
	
	private Integer seq ;
	
	private String iconCls ;
	
	private RoleEntity role ;
	
	private Set<RoleEntity> roles = new HashSet<RoleEntity>(0) ;
	
	private Set<MenuEntity> menus = new HashSet<MenuEntity>(0) ;
	
	private Set<EmployeeEntity> emps = new HashSet<EmployeeEntity>(0) ;
	
	private Date createdatetime = new Date() ;

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

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PID")
	public RoleEntity getRole() {
		return role;
	}

	public void setRole(RoleEntity role) {
		this.role = role;
	}

	@OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
	public Set<RoleEntity> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleEntity> roles) {
		this.roles = roles;
	}

	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "INFOX_SYSMGR_ROLE_MENU", joinColumns = { @JoinColumn(name = "ROLE_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "MENU_ID", nullable = false, updatable = false) })
	public Set<MenuEntity> getMenus() {
		return menus;
	}

	public void setMenus(Set<MenuEntity> menus) {
		this.menus = menus;
	}

	public Date getCreatedatetime() {
		return createdatetime;
	}

	public void setCreatedatetime(Date createdatetime) {
		this.createdatetime = createdatetime;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "INFOX_SYSMGR_EMP_ROLE", joinColumns = { @JoinColumn(name = "ROLE_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "EMP_ID", nullable = false, updatable = false) })
	public Set<EmployeeEntity> getEmps() {
		return emps;
	}

	public void setEmps(Set<EmployeeEntity> emps) {
		this.emps = emps;
	}

	
	
	
	

}
