package com.infox.sysmgr.service;

import java.util.List;

import javax.servlet.ServletContext;

import com.infox.sysmgr.web.form.MenuForm;


public interface MenuServiceI {

	public void add(MenuForm form) throws Exception ;
	
	public void delete(String id) throws Exception ;
	
	public void edit(MenuForm form) throws Exception ;
	
	public void ondrop(MenuForm form) throws Exception ;
	
	public MenuForm get(String id) throws Exception ;
	
	public List<MenuForm> findMenusAll(MenuForm form) throws Exception ;
	
	public void exportMenusAll(ServletContext sc) throws Exception ;
	
}
