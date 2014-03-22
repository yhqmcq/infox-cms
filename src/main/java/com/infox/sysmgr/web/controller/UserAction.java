package com.infox.sysmgr.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.infox.common.util.Constants;
import com.infox.common.web.BaseController;
import com.infox.common.web.page.DataGrid;
import com.infox.common.web.page.Json;
import com.infox.sysmgr.service.UserServiceI;
import com.infox.sysmgr.web.form.UserForm;

@Controller
@RequestMapping("/sysmgr/userAction")
public class UserAction extends BaseController {

	@Autowired
	private UserServiceI userService ;
	
	@RequestMapping("/user_main.do")
	public String user_main(){
		return  Constants.SYSTEM + "user_main" ;
	}
	
	@RequestMapping("/user_form.do")
	public String user_form(UserForm form, HttpServletRequest request){
		if(null != form.getId() && !"".equals(form.getId())) {
			request.setAttribute("id", form.getId()) ;
		}
		return Constants.SYSTEM + "user_form" ;
	}
	
	@RequestMapping("/get.do")
	@ResponseBody
	public UserForm get(UserForm form, HttpServletRequest request){
		return this.userService.get(form.getId()) ;
	}
	
	@RequestMapping("/user_permit_main.do")
	public String user_permit_main(){
		return  Constants.SYSTEM + "user_permit" ;
	}
	
	@RequestMapping("/set_permit.do")
	@ResponseBody
	public Json set_permit(UserForm form) throws Exception {
		return this.userService.set_permit(form) ;
	}
	
	@RequestMapping("/add.do")
	@ResponseBody
	synchronized public Json add(UserForm form){
		return this.userService.add(form) ;
	}
	
	@RequestMapping("/edit.do")
	@ResponseBody
	public Json edit(UserForm form){
		return this.userService.edit(form) ;
	}
	
	@RequestMapping("/delete.do")
	@ResponseBody
	public Json delete(UserForm form){
		return this.userService.delete(form) ;
	}
	
	@RequestMapping("/datagrid.do")
	@ResponseBody
	public DataGrid datagrid(UserForm form){
		return this.userService.datagrid(form) ;
	}
	
	@RequestMapping("/getPermission.do")
	@ResponseBody
	public UserForm getPermission(UserForm form) throws Exception {
		return this.userService.getPermission(form) ;
	}
	
}
