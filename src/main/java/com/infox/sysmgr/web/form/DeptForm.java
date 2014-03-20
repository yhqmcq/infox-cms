package com.infox.sysmgr.web.form;

import java.util.Date;
import java.util.List;

import com.infox.common.web.page.EasyuiTree;

public class DeptForm extends EasyuiTree<DeptForm> {
	
	private String id ;
	
	private String name ;
	
	private List<DeptForm> depts ;
	
	private Date created = new Date() ;
	
	private String company_id ;
	
	public List<DeptForm> getDepts() {
		return depts;
	}

	public void setDepts(List<DeptForm> depts) {
		this.depts = depts;
	}

	public String getCompany_id() {
		return company_id;
	}

	public void setCompany_id(String company_id) {
		this.company_id = company_id;
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

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

}
