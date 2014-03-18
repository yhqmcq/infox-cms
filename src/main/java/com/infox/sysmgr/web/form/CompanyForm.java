package com.infox.sysmgr.web.form;

import java.util.Date;

import com.infox.common.web.page.EasyuiTree;

public class CompanyForm extends EasyuiTree<CompanyForm> {
	
	private String id ;
	
	private String name ;
	
	private Date created = new Date() ;
	
	private String pname ;

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

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}
	
	

}
