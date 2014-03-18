package com.infox.sysmgr.service;

import java.util.List;

import com.infox.common.web.page.DataGrid;
import com.infox.sysmgr.web.form.RoleForm;

public interface Role1ServiceI {
	
	public void add(RoleForm form) throws Exception ;
	
	public void delete(String id) throws Exception ;
	
	public void edit(RoleForm form) throws Exception ;
	
	public RoleForm get(String id) throws Exception ;
	
	public DataGrid role_datagrid(RoleForm form) throws Exception ;
	
	public List<RoleForm> treegrid(RoleForm form ,String mode) throws Exception ;

	public void set_grant(RoleForm form) throws Exception ;
	
	public RoleForm getPermission(RoleForm form) throws Exception ;
	
}
