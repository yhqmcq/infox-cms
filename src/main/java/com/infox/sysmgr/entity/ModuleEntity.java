package com.infox.sysmgr.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.infox.common.util.RandomUtils;

/**
 * 系统模块表（资源表）
 * 创建者： 杨浩泉
 * 创建时间： 2014-3-17 下午9:21:03
 * 版本号： v1.0
 */
@Entity
@Table(name = "INFOX_SYSMGR_MODULE")
@DynamicUpdate(true)
@DynamicInsert(true)
public class ModuleEntity {

	private String id ;
	
	/** 模块名称 */
	private String moduleName ;
	
	/** 模块之(sys_user) */
	private String moduleValue ;
	
	/** 资源链接地址 */
	private String linkUrl ;
	
	private String description ;
	
	/** 类型（类别、菜单、操作） */
	private String type ;
	
	/** 排序 */
	private Integer seq ;
	
	/** 是否启用 */
	private String disused ;
	
	/** 菜单图标 */
	private String iconCls ;
	
	/** tree{open,closed} */
	private String state ;

	private Date created = new Date() ;
	
	private Set<ModuleEntity> modules = new HashSet<ModuleEntity>() ;
	
	private ModuleEntity module ;
	
	private Set<ActionEntity> actions = new HashSet<ActionEntity>(0) ;
	
	private Set<RolePermitEntity> role_permits = new HashSet<RolePermitEntity>(0) ;
	
	private Set<UserPermitEntity> user_permit = new HashSet<UserPermitEntity>(0) ;
	

	@OneToMany
	@JoinColumn(name="MODULE_PID")
	public Set<ModuleEntity> getModules() {
		return modules;
	}

	public void setModules(Set<ModuleEntity> modules) {
		this.modules = modules;
	}

	@ManyToOne
	@JoinColumn(name="MODULE_PID")
	public ModuleEntity getModule() {
		return module;
	}

	public void setModule(ModuleEntity module) {
		this.module = module;
	}

	@OneToMany
	@JoinColumn(name="MODULE_ID")
	public Set<UserPermitEntity> getUser_permit() {
		return user_permit;
	}

	public void setUser_permit(Set<UserPermitEntity> user_permit) {
		this.user_permit = user_permit;
	}
	
	@OneToMany
	@JoinColumn(name="MODULE_ID")
	public Set<ActionEntity> getActions() {
		return actions;
	}

	public Date getCreated() {
		return created;
	}

	public String getDisused() {
		return disused;
	}

	public String getIconCls() {
		return iconCls;
	}

	@Id
	public String getId() {
		if (this.id != null) {
			return this.id;
		}
		return RandomUtils.generateNumber(6);
	}

	public String getLinkUrl() {
		return linkUrl;
	}
	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getModuleName() {
		return moduleName;
	}

	public String getModuleValue() {
		return moduleValue;
	}

	@OneToMany
	@JoinColumn(name="MODULE_ID")
	public Set<RolePermitEntity> getRole_permits() {
		return role_permits;
	}

	public Integer getSeq() {
		return seq;
	}

	public String getState() {
		return state;
	}

	public String getType() {
		return type;
	}

	public void setActions(Set<ActionEntity> actions) {
		this.actions = actions;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public void setDisused(String disused) {
		this.disused = disused;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public void setModuleValue(String moduleValue) {
		this.moduleValue = moduleValue;
	}

	public void setRole_permits(Set<RolePermitEntity> role_permits) {
		this.role_permits = role_permits;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
