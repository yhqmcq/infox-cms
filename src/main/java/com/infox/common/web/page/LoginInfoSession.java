package com.infox.common.web.page;

import java.util.List;

import com.infox.sysmgr.web.form.UserForm;

/**
 * session信息模型
 * 
 * @author 孙宇
 * 
 */
public class LoginInfoSession implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private UserForm user ;
	
	private List<String> resourceList;// 用户可以访问的资源地址列表

	public UserForm getUser() {
		return user;
	}

	public void setUser(UserForm user) {
		this.user = user;
	}

	public List<String> getResourceList() {
		return resourceList;
	}

	public void setResourceList(List<String> resourceList) {
		this.resourceList = resourceList;
	}

	

}
