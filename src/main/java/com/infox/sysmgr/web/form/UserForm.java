package com.infox.sysmgr.web.form;

import java.util.Date;

import com.infox.common.web.page.PageHelper;

public class UserForm extends PageHelper {

	private String id;

	private String name;

	private Date created ;
	
	private String dept_id ;
	
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getDept_id() {
		return dept_id;
	}

	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
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
	
	

}
