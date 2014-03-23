package com.infox.sysmgr.service;

import java.util.List;

import com.infox.common.web.page.DataGrid;
import com.infox.common.web.page.Json;
import com.infox.common.web.page.LoginInfoSession;
import com.infox.sysmgr.web.form.UserForm;

public interface UserServiceI {

	public Json add(UserForm form) ;
	
	public Json delete(UserForm form) ;
	
	public Json edit(UserForm form) ;
	
	public UserForm get(String id) ;
	
	public UserForm get(UserForm form) ;
	
	public DataGrid datagrid(UserForm form) ;
	
	public Json set_permit(UserForm form) ;
	
	public UserForm getPermission(UserForm form) ;
	
	public UserForm login(UserForm user) ;
	
	public List<String> MyPermission(String id, String username) throws Exception ;
	
	public boolean editCurrentUserPwd(LoginInfoSession sessionInfo, String oldPwd, String pwd) ;
}
