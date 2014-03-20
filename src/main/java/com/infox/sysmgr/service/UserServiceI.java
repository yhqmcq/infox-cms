package com.infox.sysmgr.service;

import com.infox.common.web.page.DataGrid;
import com.infox.common.web.page.Json;
import com.infox.sysmgr.web.form.UserForm;

public interface UserServiceI {

	public Json add(UserForm form) ;
	
	public Json delete(UserForm form) ;
	
	public Json edit(UserForm form) ;
	
	public UserForm get(String id) ;
	
	public UserForm get(UserForm form) ;
	
	public DataGrid datagrid(UserForm form) ;
}
