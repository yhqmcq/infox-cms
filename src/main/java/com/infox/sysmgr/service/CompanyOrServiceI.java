package com.infox.sysmgr.service;

import java.util.List;

import com.infox.sysmgr.web.form.CompanyForm;

public interface CompanyOrServiceI {
	
	public void add(CompanyForm form) throws Exception ;
	
	public void delete(String id) throws Exception ;
	
	public void edit(CompanyForm form) throws Exception ;
	
	public CompanyForm get(String id) throws Exception ;
	
	public List<CompanyForm> treegrid(CompanyForm form ,String mode) throws Exception ;

}
