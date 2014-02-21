package com.infox.sysmgr.service;

import java.util.List;

import com.infox.common.web.page.DataGrid;
import com.infox.sysmgr.entity.TaskEntity;
import com.infox.sysmgr.web.form.TaskForm;

public interface TaskSchedulerServiceI {

	public void add(TaskForm form) throws Exception ;
	
	public void delete(String id) throws Exception ;
	
	public void edit(TaskForm form) throws Exception ;
	
	public TaskForm get(String id) throws Exception ;
	
	public DataGrid datagrid(TaskForm form) throws Exception ;
	
	public List<TaskEntity> find(TaskForm form) throws Exception ;
	
}
