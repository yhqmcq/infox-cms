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
import com.infox.sysmgr.service.CompanyServiceI;
import com.infox.sysmgr.web.form.CompanyForm;

@Controller
@RequestMapping("/sysmgr/companyAction")
public class CompanyAction extends BaseController {
	
	@Autowired
	private CompanyServiceI companyService ;
	
	@RequestMapping("/company_main.do")
	public String company_main() throws Exception {
		return Constants.SYSTEM + "company_main" ;
	}
	
	@RequestMapping("/company_form.do")
	public String company_form(CompanyForm form, HttpServletRequest request) throws Exception {
		if(null != form.getId() && !"".equals(form.getId())) {
			request.setAttribute("id", form.getId()) ;
		}
		return Constants.SYSTEM + "company_form" ;
	}
	
	@RequestMapping("/get.do")
	@ResponseBody
	public CompanyForm get(CompanyForm form, HttpServletRequest request) throws Exception {
		return this.companyService.get(form.getId()) ;
	}
	
	@RequestMapping("/add.do")
	@ResponseBody
	public Json add(CompanyForm form) throws Exception {
		return this.companyService.add(form) ;
	}
	
	@RequestMapping("/edit.do")
	@ResponseBody
	public Json edit(CompanyForm form) throws Exception {
		return this.companyService.edit(form) ;
	}
	
	@RequestMapping("/delete.do")
	@ResponseBody
	public Json delete(String id) throws Exception {
		return this.companyService.delete(id) ;
	}
	
	@RequestMapping("/treegrid.do")
	@ResponseBody
	public List<CompanyForm> treegrid(CompanyForm form ,String mode) throws Exception {
		return this.companyService.treegrid(form) ;
	}

}
