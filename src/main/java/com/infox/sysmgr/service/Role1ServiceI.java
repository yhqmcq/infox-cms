package com.infox.sysmgr.service;

import java.util.List;

import com.infox.common.web.page.DataGrid;
import com.infox.sysmgr.web.form.Role1Form;

public interface Role1ServiceI {
	
	public void add(Role1Form form) throws Exception ;
	
	public void delete(String id) throws Exception ;
	
	public void edit(Role1Form form) throws Exception ;
	
	public Role1Form get(String id) throws Exception ;
	
	public DataGrid role_datagrid(Role1Form form) throws Exception ;
	
	public List<Role1Form> treegrid(Role1Form form ,String mode) throws Exception ;

	public void set_grant(Role1Form form) throws Exception ;
	
	public Role1Form getPermission(Role1Form form) throws Exception ;
	
}
