package com.infox.sysmgr.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.infox.common.util.Constants;
import com.infox.common.web.BaseController;
import com.infox.common.web.page.Json;
import com.infox.sysmgr.service.MenuServiceI;
import com.infox.sysmgr.web.form.MenuForm;

@Controller
@RequestMapping("/sysmgr/menu")
public class Menu extends BaseController {

	@Autowired
	private MenuServiceI menuService ;
	
	@RequestMapping("/menu_main.do")
	public String menu_main() throws Exception {
		return Constants.SYSTEM + "menu_main" ;
	}
	
	@RequestMapping("/menu_form.do")
	public String menu_form(MenuForm form, HttpServletRequest request) throws Exception {
		if(null != form.getId() && !"".equals(form.getId())) {
			request.setAttribute("id", form.getId()) ;
		}
		return Constants.SYSTEM + "menu_form" ;
	}
	
	@RequestMapping("/get.do")
	@ResponseBody
	public MenuForm get(MenuForm form, HttpServletRequest request) throws Exception {
		return this.menuService.get(form.getId()) ;
	}
	
	@RequestMapping("/add.do")
	@ResponseBody
	public Json add(MenuForm form, HttpServletRequest request) throws Exception {
		Json j = new Json() ;
		try {
			this.menuService.add(form) ;
			this.export_menu(request) ;
			j.setStatus(true) ;
		} catch (Exception e) {
			throw e;
		}
		return j ;
	}
	
	@RequestMapping("/delete.do")
	@ResponseBody
	public Json delete(MenuForm form, HttpServletRequest request) throws Exception {
		Json j = new Json() ;
		try {
			this.menuService.delete(form.getId()) ;
			j.setStatus(true) ;
			this.export_menu(request) ;
		} catch (Exception e) {
			throw e;
		}
		return j ;
	}
	
	@RequestMapping("/edit.do")
	@ResponseBody
	public Json edit(MenuForm form, HttpServletRequest request) throws Exception {
		Json j = new Json() ;
		try {
			this.menuService.edit(form) ;
			this.export_menu(request) ;
			j.setStatus(true) ;
		} catch (Exception e) {
			throw e;
		}
		return j ;
	}
	
	@RequestMapping("/ondrop.do")
	@ResponseBody
	public Json ondrop(MenuForm form, HttpServletRequest request) throws Exception {
		Json j = new Json() ;
		try {
			this.menuService.ondrop(form) ;
			this.export_menu(request) ;
			j.setStatus(true) ;
		} catch (Exception e) {
			e.printStackTrace() ;
			throw e;
		}
		return j ;
	}
	
	@RequestMapping("/treegrid.do")
	@ResponseBody
	public List<MenuForm> treegrid(MenuForm form, HttpServletRequest request) throws Exception {
		return this.menuService.findMenusAll(form) ;
	}
	
	@RequestMapping("/export_menu.do")
	public void export_menu(HttpServletRequest request) throws Exception {
		this.menuService.exportMenusAll(request.getServletContext()) ;
	}
}
