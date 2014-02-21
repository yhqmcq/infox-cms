package com.infox.sysmgr.service;

import com.infox.common.web.page.DataGrid;
import com.infox.sysmgr.web.form.EmpOnlineForm;

public interface EmployeeOnlineServiceI {

	public void add(EmpOnlineForm form) throws Exception ;
	
	public void delete(String id) throws Exception ;
	
	public void edit(EmpOnlineForm form) throws Exception ;
	
	public EmpOnlineForm get(String id) throws Exception ;
	
	public DataGrid datagrid(EmpOnlineForm form) throws Exception ;
}
