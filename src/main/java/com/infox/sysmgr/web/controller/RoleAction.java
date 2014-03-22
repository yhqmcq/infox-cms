package com.infox.sysmgr.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.infox.common.util.Constants;
import com.infox.common.web.BaseController;
import com.infox.common.web.page.DataGrid;
import com.infox.common.web.page.Json;
import com.infox.sysmgr.service.RoleServiceI;
import com.infox.sysmgr.web.form.RoleForm;

@Controller
@RequestMapping("/sysmgr/roleAction")
public class RoleAction extends BaseController{
	
	@Autowired
	private RoleServiceI roleService ;
	
	@RequestMapping("/role_main.do")
	public String role_main() throws Exception {
		return Constants.SYSTEM + "role_main" ;
	}
	
	@RequestMapping("/role_form.do")
	public String role_form(RoleForm form, HttpServletRequest request) throws Exception {
		if(null != form.getId() && !"".equals(form.getId())) {
			request.setAttribute("id", form.getId()) ;
		}
		return Constants.SYSTEM + "role_form" ;
	}
	
	@RequestMapping("/get.do")
	@ResponseBody
	public RoleForm get(RoleForm form, HttpServletRequest request) throws Exception {
		return this.roleService.get(form) ;
	}
	
	@RequestMapping("/add.do")
	@ResponseBody
	public Json add(RoleForm form) throws Exception {
		return this.roleService.add(form) ;
	}
	
	@RequestMapping("/edit.do")
	@ResponseBody
	public Json edit(RoleForm form) throws Exception {
		return this.roleService.edit(form) ;
	}
	
	@RequestMapping("/delete.do")
	@ResponseBody
	public Json delete(RoleForm form) throws Exception {
		return this.roleService.delete(form) ;
	}
	
	@RequestMapping("/treegrid.do")
	@ResponseBody
	public List<RoleForm> treegrid(RoleForm form) throws Exception {
		return this.roleService.treegrid(form) ;
	}
	
	@RequestMapping("/datagrid.do")
	@ResponseBody
	public DataGrid datagrid(RoleForm form) throws Exception {
		return this.roleService.datagrid(form) ;
	}
	
	@RequestMapping("/getPermission.do")
	@ResponseBody
	public RoleForm getPermission(RoleForm form) throws Exception {
		return this.roleService.getPermission(form) ;
	}
	
	@RequestMapping("/set_grant.do")
	@ResponseBody
	public Json set_grant(RoleForm form) throws Exception {
		return this.roleService.set_grant(form) ;
	}
	
}
