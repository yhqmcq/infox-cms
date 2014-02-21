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
import com.infox.sysmgr.service.OrganizationServiceI;
import com.infox.sysmgr.web.form.OrganizationForm;

@Controller
@RequestMapping("/sysmgr/org")
public class Organization extends BaseController {
	
	@Autowired
	private OrganizationServiceI orgservice ;
	
	@RequestMapping("/org_main.do")
	public String org_main() throws Exception {
		return Constants.SYSTEM + "org_main" ;
	}
	
	@RequestMapping("/org_form.do")
	public String org_form(OrganizationForm form, HttpServletRequest request) throws Exception {
		if(null != form.getId() && !"".equals(form.getId())) {
			request.setAttribute("id", form.getId()) ;
		}
		return Constants.SYSTEM + "org_form" ;
	}
	
	@RequestMapping("/get.do")
	@ResponseBody
	public OrganizationForm get(OrganizationForm form, HttpServletRequest request) throws Exception {
		return this.orgservice.get(form.getId()) ;
	}
	
	@RequestMapping("/add.do")
	@ResponseBody
	public Json add(OrganizationForm form) throws Exception {
		Json json = new Json() ;
		try {
			this.orgservice.add(form) ;
			json.setStatus(true) ;
		} catch (Exception e) {
			throw e;
		}
		return json ;
	}
	
	@RequestMapping("/edit.do")
	@ResponseBody
	public Json edit(OrganizationForm form) throws Exception {
		Json json = new Json() ;
		try {
			this.orgservice.edit(form) ;
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
			this.orgservice.delete(id) ;
			j.setStatus(true);
		} catch (Exception e) {
			throw e;
		}
		return j ;
	}
	
	@RequestMapping("/treegrid.do")
	@ResponseBody
	public List<OrganizationForm> treegrid(OrganizationForm form ,String mode) throws Exception {
		return this.orgservice.org_treegrid(form ,mode) ;
	}

}
