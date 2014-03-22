package com.infox.sysmgr.service;

import java.util.List;

import com.infox.common.web.page.DataGrid;
import com.infox.common.web.page.Json;
import com.infox.sysmgr.web.form.RoleForm;

public interface RoleServiceI {
	
	public Json add(RoleForm form) throws Exception ;
	
	public Json delete(RoleForm form) throws Exception ;
	
	public Json edit(RoleForm form) throws Exception ;
	
	public RoleForm get(RoleForm form) throws Exception ;
	
	public DataGrid datagrid(RoleForm form) throws Exception ;
	
	public List<RoleForm> treegrid(RoleForm form) throws Exception ;

	public Json set_grant(RoleForm form) throws Exception ;
	
	public RoleForm getPermission(RoleForm form) throws Exception ;
	
}
