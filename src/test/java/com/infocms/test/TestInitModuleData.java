package com.infocms.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.infocms.junit.BaseJunitCase;
import com.infox.sysmgr.service.ModuleServiceI;
import com.infox.sysmgr.web.form.ModuleForm;

public class TestInitModuleData extends BaseJunitCase {
	
	@Autowired
	private ModuleServiceI mondulService ;

	@Test
	public void initModuleData() {
		/*
		ModuleForm form = new ModuleForm() ;
		form.setModuleName("系统管理") ;
		form.setModuleValue("sys_module_add") ;
		form.setType("R") ;
		
		this.mondulService.save(form) ;
		*/
		
		ModuleForm form = new ModuleForm() ;
		form.setPid("197101") ;
		form.setModuleName("模块管理") ;
		form.setModuleValue("sys_module_read") ;
		form.setType("F") ;

		this.mondulService.save(form) ;
	}
	
}
