package com.infox.sysmgr.web.form;

import java.util.Date;

import com.infox.common.web.page.EasyuiTree;

public class ModuleForm extends EasyuiTree<ModuleForm> {

	private String id;

	/** 模块名称 */
	private String moduleName;

	/** 模块之(sys_user) */
	private String moduleValue;

	/** 资源链接地址 */
	private String linkUrl;

	private String description;

	/** 类型（菜单、操作） */
	private String type;

	/** 排序 */
	private Integer seq;

	/** 是否启用 */
	private String disused;

	/** 菜单图标 */
	private String iconCls;

	/** tree{open,closed} */
	private String state;

	private Date created = new Date();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleValue() {
		return moduleValue;
	}

	public void setModuleValue(String moduleValue) {
		this.moduleValue = moduleValue;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getDisused() {
		return disused;
	}

	public void setDisused(String disused) {
		this.disused = disused;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
	
	

}
