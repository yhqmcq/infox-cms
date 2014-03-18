package com.infox.sysmgr.service;

import java.util.List;

import com.infox.common.web.page.Json;
import com.infox.sysmgr.web.form.CompanyForm;

public interface CompanyServiceI {

	public Json add(CompanyForm form) ;
	
	public Json delete(String id) ;
	
	public Json edit(CompanyForm form) ;
	
	public CompanyForm get(String id) ;
	
	public List<CompanyForm> treegrid(CompanyForm form ,String mode) ;
	
}
