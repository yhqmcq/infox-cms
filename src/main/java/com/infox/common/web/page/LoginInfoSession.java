package com.infox.common.web.page;

import java.util.List;

import com.infox.sysmgr.web.form.EmployeeForm;

/**
 * session信息模型
 * 
 * @author 孙宇
 * 
 */
public class LoginInfoSession implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private EmployeeForm emp ;
	
	private List<String> resourceList;// 用户可以访问的资源地址列表

	public EmployeeForm getEmp() {
		return emp;
	}

	public void setEmp(EmployeeForm emp) {
		this.emp = emp;
	}

	public List<String> getResourceList() {
		return resourceList;
	}

	public void setResourceList(List<String> resourceList) {
		this.resourceList = resourceList;
	}

	

}
