package com.infox.sysmgr.web.form;

import java.util.List;

import com.infox.common.web.page.PageHelper;

public class RoleForm extends PageHelper{
	
	private String id ;
	
	private String name ;
	
	private String description ;
	
	private String creater ;
	
	private String modifyer ;
	
	private Integer seq ;
	
	private String pid ;
	
	private String pname ;
	
	private String menuIds ;
	
	private String menuNames ;
	
	private String iconCls = "ext_group";
	private String text;
	private String state ;// open,closed
	private boolean checked = false;
	private Object attributes;
	private List<RoleForm> children;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Object getAttributes() {
		return attributes;
	}

	public void setAttributes(Object attributes) {
		this.attributes = attributes;
	}


	public List<RoleForm> getChildren() {
		return children;
	}

	public void setChildren(List<RoleForm> children) {
		this.children = children;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(String menuIds) {
		this.menuIds = menuIds;
	}

	public String getMenuNames() {
		return menuNames;
	}

	public void setMenuNames(String menuNames) {
		this.menuNames = menuNames;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	
}
