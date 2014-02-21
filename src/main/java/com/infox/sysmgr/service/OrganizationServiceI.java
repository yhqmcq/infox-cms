package com.infox.sysmgr.service;

import java.util.List;

import com.infox.sysmgr.web.form.OrganizationForm;

public interface OrganizationServiceI {
	
	public void add(OrganizationForm form) throws Exception ;
	
	public void delete(String id) throws Exception ;
	
	public void edit(OrganizationForm form) throws Exception ;
	
	public OrganizationForm get(String id) throws Exception ;
	
	public List<OrganizationForm> org_treegrid(OrganizationForm form ,String mode) throws Exception ;

}
