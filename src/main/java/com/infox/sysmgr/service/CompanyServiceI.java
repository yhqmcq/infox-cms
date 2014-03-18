package com.infox.sysmgr.service;

import java.util.List;

import com.infox.common.web.page.Json;
import com.infox.sysmgr.web.form.Company1Form;

public interface CompanyServiceI {

	public Json add(Company1Form form) throws Exception ;
	
	public Json delete(String id) throws Exception ;
	
	public Json edit(Company1Form form) throws Exception ;
	
	public Company1Form get(String id) throws Exception ;
	
	public List<Company1Form> treegrid(Company1Form form ,String mode) throws Exception ;
	
}
