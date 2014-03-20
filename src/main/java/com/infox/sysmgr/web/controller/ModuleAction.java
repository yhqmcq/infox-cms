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
import com.infox.sysmgr.service.ModuleServiceI;
import com.infox.sysmgr.web.form.ModuleForm;

@Controller
@RequestMapping("/sysmgr/moduleAction")
public class ModuleAction extends BaseController {
	
	@Autowired
	private ModuleServiceI moduleService ;
	
	@RequestMapping("/module_main.do")
	public String module_main() throws Exception {
		return Constants.SYSTEM + "module_main" ;
	}
	
	@RequestMapping("/module_form.do")
	public String module_form(ModuleForm form, HttpServletRequest request) throws Exception {
		if(null != form.getId() && !"".equals(form.getId())) {
			request.setAttribute("id", form.getId()) ;
		}
		return Constants.SYSTEM + "module_form" ;
	}
	
	@RequestMapping("/get.do")
	@ResponseBody
	public ModuleForm get(ModuleForm form, HttpServletRequest request) throws Exception {
		return this.moduleService.get(form.getId()) ;
	}
	
	@RequestMapping("/add.do")
	@ResponseBody
	public Json add(ModuleForm form, HttpServletRequest request) throws Exception {
		Json j = new Json() ;
		try {
			this.moduleService.save(form) ;
			this.export_menu(request) ;
			j.setStatus(true) ;
		} catch (Exception e) {
			throw e;
		}
		return j ;
	}
	
	@RequestMapping("/delete.do")
	@ResponseBody
	public Json delete(ModuleForm form, HttpServletRequest request) throws Exception {
		Json j = new Json() ;
		try {
			this.moduleService.delete(form.getId()) ;
			j.setStatus(true) ;
			this.export_menu(request) ;
		} catch (Exception e) {
			throw e;
		}
		return j ;
	}
	
	@RequestMapping("/edit.do")
	@ResponseBody
	public Json edit(ModuleForm form, HttpServletRequest request) throws Exception {
		Json j = new Json() ;
		try {
			this.moduleService.edit(form) ;
			this.export_menu(request) ;
			j.setStatus(true) ;
		} catch (Exception e) {
			throw e;
		}
		return j ;
	}
	
	@RequestMapping("/ondrop.do")
	@ResponseBody
	public Json ondrop(ModuleForm form, HttpServletRequest request) throws Exception {
		Json j = new Json() ;
		try {
			this.moduleService.ondrop(form) ;
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
	public List<ModuleForm> treegrid(ModuleForm form, HttpServletRequest request) throws Exception {
		return this.moduleService.findMenusAll(form) ;
	}
	
	@RequestMapping("/export_menu.do")
	public void export_menu(HttpServletRequest request) throws Exception {
		this.moduleService.exportMenusAll(request.getServletContext()) ;
	}

}
