package com.infox.sysmgr.service;

import java.util.List;

import javax.servlet.ServletContext;

import com.infox.common.web.page.Json;
import com.infox.sysmgr.web.form.ModuleForm;

public interface ModuleServiceI {

	public Json save(ModuleForm form) ;
	
	public Json delete(String id) ;
	
	public Json edit(ModuleForm form) ;
	
	public Json ondrop(ModuleForm form) ;
	
	public ModuleForm get(String id) ;
	
	public List<ModuleForm> findMenusAll(ModuleForm form) ;
	
	public void exportMenusAll(ServletContext sc) ;
	
}
