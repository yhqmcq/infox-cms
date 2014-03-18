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
 * 系统中所有操作的动作表
 * 创建者： 杨浩泉
 * 创建时间： 2014-3-17 下午9:21:03
 * 版本号： v1.0
 */
@Entity
@Table(name = "INFOX_SYSMGR_ACTION")
@DynamicUpdate(true)
@DynamicInsert(true)
public class ActionEntity {

	private String id ;
	
	private String actionName ;
	
	private String actionValue ;

	private Date created = new Date() ;
	
	private ModuleEntity module ;
	
	@ManyToOne
	@JoinColumn(name="MODULE_ID")
	public ModuleEntity getModule() {
		return module;
	}

	public void setModule(ModuleEntity module) {
		this.module = module;
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

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getActionValue() {
		return actionValue;
	}

	public void setActionValue(String actionValue) {
		this.actionValue = actionValue;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
	
}
