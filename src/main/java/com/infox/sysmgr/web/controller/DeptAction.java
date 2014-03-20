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
import com.infox.sysmgr.service.DeptServiceI;
import com.infox.sysmgr.web.form.DeptForm;

@Controller
@RequestMapping("/sysmgr/deptAction")
public class DeptAction extends BaseController {
	
	@Autowired
	private DeptServiceI deptService ;
	
	@RequestMapping("/dept_main.do")
	public String dept_main() throws Exception {
		return Constants.SYSTEM + "dept_main" ;
	}
	
	@RequestMapping("/dept_form.do")
	public String dept_form(DeptForm form, HttpServletRequest request) throws Exception {
		if(null != form.getId() && !"".equals(form.getId())) {
			request.setAttribute("id", form.getId()) ;
		}
		return Constants.SYSTEM + "dept_form" ;
	}
	
	@RequestMapping("/get.do")
	@ResponseBody
	public DeptForm get(DeptForm form, HttpServletRequest request) throws Exception {
		return this.deptService.get(form.getId()) ;
	}
	
	@RequestMapping("/add.do")
	@ResponseBody
	public Json add(DeptForm form) throws Exception {
		return this.deptService.add(form) ;
	}
	
	@RequestMapping("/edit.do")
	@ResponseBody
	public Json edit(DeptForm form) throws Exception {
		return this.deptService.edit(form) ;
	}
	
	@RequestMapping("/delete.do")
	@ResponseBody
	public Json delete(String id) throws Exception {
		return this.deptService.delete(id) ;
	}
	
	@RequestMapping("/treegrid.do")
	@ResponseBody
	public List<DeptForm> treegrid(DeptForm form ,String mode) throws Exception {
		return this.deptService.treegrid(form ,mode) ;
	}

}
