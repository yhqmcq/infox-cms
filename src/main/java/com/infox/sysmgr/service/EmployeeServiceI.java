package com.infox.sysmgr.service;

import java.util.List;

import com.infox.common.web.page.DataGrid;
import com.infox.common.web.page.LoginInfoSession;
import com.infox.sysmgr.web.form.EmployeeForm;

public interface EmployeeServiceI {

	public void add(EmployeeForm form) throws Exception ;
	
	public void delete(String id) throws Exception ;
	
	public void edit(EmployeeForm form) throws Exception ;
	
	public EmployeeForm get(String id) throws Exception ;
	
	public DataGrid datagrid(EmployeeForm form) throws Exception ;
	
	public void set_grant(EmployeeForm form) throws Exception ;
	
	public EmployeeForm getPermission(EmployeeForm form) throws Exception ;
	
	public EmployeeForm login(EmployeeForm user) throws Exception  ;
	
	public List<String> MyPermission(String id, String username) throws Exception ;
	
	public boolean editCurrentUserPwd(LoginInfoSession sessionInfo, String oldPwd, String pwd) throws Exception ;
}
