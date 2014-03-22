package com.infox.sysmgr.web.form;

import com.infox.common.web.page.PageHelper;

public class RoleForm extends PageHelper {

	private String id ;
	
	private String name ;
	
	private String moduleIds ;
	
	private String moduleNames ;

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

	public String getModuleIds() {
		return moduleIds;
	}

	public void setModuleIds(String moduleIds) {
		this.moduleIds = moduleIds;
	}

	public String getModuleNames() {
		return moduleNames;
	}

	public void setModuleNames(String moduleNames) {
		this.moduleNames = moduleNames;
	}
	
	
	
}
