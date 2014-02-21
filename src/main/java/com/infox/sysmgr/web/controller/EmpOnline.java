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
import com.infox.sysmgr.service.EmployeeOnlineServiceI;
import com.infox.sysmgr.service.EmployeeServiceI;
import com.infox.sysmgr.web.form.EmpOnlineForm;
import com.infox.sysmgr.web.form.EmployeeForm;

@Controller
@RequestMapping("/sysmgr/emponline")
public class EmpOnline extends BaseController {
	
	@Autowired
	private EmployeeOnlineServiceI empOnlineservice ;
	
	@Autowired
	private EmployeeServiceI empservice ;
	
	@RequestMapping("/online_main.do")
	public String online_main() throws Exception {
		return  Constants.SYSTEM + "online_main" ;
	}
	
	@RequestMapping("/doNotNeedAuth_get.do")
	@ResponseBody
	public EmpOnlineForm get(EmpOnlineForm form, HttpServletRequest request) throws Exception {
		return this.empOnlineservice.get(form.getId()) ;
	}
	
	@RequestMapping("/doNotNeedAuth_delete.do")
	@ResponseBody
	public Json delete(String ids) throws Exception {
		Json j = new Json();
		try {
			if(null != ids && !ids.equalsIgnoreCase("")) {
				String[] id = ids.split(",") ;
				for(int i=0;i<id.length;i++) {
					this.empOnlineservice.delete(id[i]) ;
				}
				j.setStatus(true);
			}
		} catch (Exception e) {
			throw e;
		}
		return j ;
	}
	
	@RequestMapping("/datagrid.do")
	@ResponseBody
	public DataGrid datagrid(EmpOnlineForm form) throws Exception {
		return this.empOnlineservice.datagrid(form) ;
	}
	
	@RequestMapping("/doNotNeedSession_datagrid.do")
	@ResponseBody
	public DataGrid doNotNeedSession_datagrid() throws Exception {
		EmployeeForm form =  new EmployeeForm() ;
		form.setOnlineState("1") ;
		return this.empservice.datagrid(form);
	}
	
	

}
