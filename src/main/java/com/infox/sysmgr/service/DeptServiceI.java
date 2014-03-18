package com.infox.sysmgr.service;

import java.util.List;

import com.infox.common.web.page.Json;
import com.infox.sysmgr.web.form.DeptForm;

public interface DeptServiceI {

	public Json add(DeptForm form) ;
	
	public Json delete(String id) ;
	
	public Json edit(DeptForm form) ;
	
	public DeptForm get(String id) ;
	
	public List<DeptForm> treegrid(DeptForm form ,String mode) ;
	
}
