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
import com.infox.sysmgr.service.CompanyOrServiceI;
import com.infox.sysmgr.web.form.Company1Form;

@Controller
@RequestMapping("/sysmgr/company")
public class CompanyOrg extends BaseController {
	
	@Autowired
	private CompanyOrServiceI orgservice ;
	
	@RequestMapping("/company_main.do")
	public String company_main() throws Exception {
		return Constants.SYSTEM + "company_main" ;
	}
	
	@RequestMapping("/company_form.do")
	public String company_form(Company1Form form, HttpServletRequest request) throws Exception {
		if(null != form.getId() && !"".equals(form.getId())) {
			request.setAttribute("id", form.getId()) ;
		}
		return Constants.SYSTEM + "company_form" ;
	}
	
	@RequestMapping("/get.do")
	@ResponseBody
	public Company1Form get(Company1Form form, HttpServletRequest request) throws Exception {
		return this.orgservice.get(form.getId()) ;
	}
	
	@RequestMapping("/add.do")
	@ResponseBody
	public Json add(Company1Form form) throws Exception {
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
	public Json edit(Company1Form form) throws Exception {
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
	public List<Company1Form> treegrid(Company1Form form ,String mode) throws Exception {
		return this.orgservice.treegrid(form ,mode) ;
	}

}
