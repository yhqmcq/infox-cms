package com.infox.sysmgr.service;

import java.util.List;

import com.infox.sysmgr.web.form.Company1Form;

public interface CompanyOrServiceI {
	
	public void add(Company1Form form) throws Exception ;
	
	public void delete(String id) throws Exception ;
	
	public void edit(Company1Form form) throws Exception ;
	
	public Company1Form get(String id) throws Exception ;
	
	public List<Company1Form> treegrid(Company1Form form ,String mode) throws Exception ;

}
