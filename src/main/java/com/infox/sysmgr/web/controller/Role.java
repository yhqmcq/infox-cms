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
import com.infox.sysmgr.service.Role1ServiceI;
import com.infox.sysmgr.web.form.RoleForm;

@Controller
@RequestMapping("/sysmgr/role")
public class Role extends BaseController{
	
	@Autowired
	private Role1ServiceI roleservice ;
	
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
		return this.roleservice.get(form.getId()) ;
	}
	
	@RequestMapping("/add.do")
	@ResponseBody
	public Json add(RoleForm form) throws Exception {
		Json json = new Json() ;
		try {
			this.roleservice.add(form) ;
			json.setStatus(true) ;
		} catch (Exception e) {
			throw e;
		}
		return json ;
	}
	
	@RequestMapping("/edit.do")
	@ResponseBody
	public Json edit(RoleForm form) throws Exception {
		Json json = new Json() ;
		try {
			this.roleservice.edit(form) ;
			json.setStatus(true) ;
		} catch (Exception e) {
			throw e;
		}
		return json ;
	}
	
	@RequestMapping("/delete.do")
	@ResponseBody
	public Json delete(String id) throws Exception {
		Json j = new Json();
		try {
			this.roleservice.delete(id) ;
			j.setStatus(true);
		} catch (Exception e) {
			throw e;
		}
		return j ;
	}
	
	@RequestMapping("/treegrid.do")
	@ResponseBody
	public List<RoleForm> treegrid(RoleForm form ,String mode) throws Exception {
		return this.roleservice.treegrid(form ,mode) ;
	}
	
	@RequestMapping("/getPermission.do")
	@ResponseBody
	public RoleForm getPermission(RoleForm form) throws Exception {
		return this.roleservice.getPermission(form) ;
	}
	
	@RequestMapping("/set_grant.do")
	@ResponseBody
	public Json set_grant(RoleForm form) throws Exception {
		Json j = new Json();
		try {
			this.roleservice.set_grant(form) ;
			j.setStatus(true);
			j.setMsg("权限分配成功！");
		} catch (Exception e) {
			j.setStatus(false);
			j.setMsg("权限分配失败：" + e.getMessage());
		}
		return j ;
	}
	
}
