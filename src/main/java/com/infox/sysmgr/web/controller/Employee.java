package com.infox.sysmgr.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.infox.common.util.Constants;
import com.infox.common.util.IpUtil;
import com.infox.common.web.BaseController;
import com.infox.common.web.page.DataGrid;
import com.infox.common.web.page.Json;
import com.infox.common.web.page.LoginInfoSession;
import com.infox.sysmgr.service.EmployeeServiceI;
import com.infox.sysmgr.web.form.EmployeeForm;

@Controller
@RequestMapping("/sysmgr/employee")
public class Employee extends BaseController {
	
	@Autowired
	private EmployeeServiceI empservice ;
	
	@RequestMapping("/emp_main.do")
	public String emp_main() throws Exception {
		return  Constants.SYSTEM + "emp_main" ;
	}
	
	@RequestMapping("/emp_form.do")
	public String emp_form(EmployeeForm form, HttpServletRequest request) throws Exception {
		if(null != form.getId() && !"".equals(form.getId())) {
			request.setAttribute("id", form.getId()) ;
		}
		return Constants.SYSTEM + "emp_form" ;
	}
	
	@RequestMapping("/get.do")
	@ResponseBody
	public EmployeeForm get(EmployeeForm form, HttpServletRequest request) throws Exception {
		return this.empservice.get(form.getId()) ;
	}
	
	@RequestMapping("/emp_grant_main.do")
	public String emp_grant_main() throws Exception {
		return  Constants.SYSTEM + "emp_grant" ;
	}
	
	@RequestMapping("/add.do")
	@ResponseBody
	synchronized public Json add(EmployeeForm form) throws Exception {
		Json j = new Json() ;
		try {
			this.empservice.add(form) ;
			j.setStatus(true) ;
		} catch (Exception e) {
			throw e;
		}
		return j ;
	}
	
	@RequestMapping("/edit.do")
	@ResponseBody
	public Json edit(EmployeeForm form) throws Exception {
		Json j = new Json() ;
		try {
			this.empservice.edit(form) ;
			j.setStatus(true) ;
		} catch (Exception e) {
			throw e;
		}
		return j ;
	}
	
	@RequestMapping("/delete.do")
	@ResponseBody
	public Json delete(String ids) throws Exception {
		Json j = new Json();
		try {
			if(null != ids && !ids.equalsIgnoreCase("")) {
				String[] id = ids.split(",") ;
				for(int i=0;i<id.length;i++) {
					this.empservice.delete(id[i]) ;
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
	public DataGrid datagrid(EmployeeForm form) throws Exception {
		return this.empservice.datagrid(form) ;
	}
	
	/**
	 * 登陆验证
	 * @throws Exception 
	 */
	@RequestMapping("/doNotNeedSession_login.do")
	@ResponseBody
	public Json doNotNeedSession_login(EmployeeForm form, HttpServletRequest request) throws Exception {
		Json j = new Json();
		try {
			
			//验证码比较
			String c = (String)request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
			if(form.getKaptcha().equalsIgnoreCase(c)) {
				EmployeeForm emp = this.empservice.login(form);
				if (emp != null) {
					if("Y".equalsIgnoreCase(emp.getStatus())) {
						request.getSession().removeAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
						
						emp.setIp(IpUtil.getIpAddr(request)) ;
						LoginInfoSession LoginInfo = new LoginInfoSession();
						LoginInfo.setEmp(emp) ;
						LoginInfo.setResourceList(this.empservice.MyPermission(emp.getId(), emp.getAccount()));
						request.getSession().setAttribute(Constants.SESSION_INFO_NAME, LoginInfo);
						
						j.setStatus(true);
						j.setMsg("登陆成功！");
						j.setObj(LoginInfo);
					} else {
						j.setStatus(false);
						j.setMsg("账号未激活,请与管理员联系!");
					}
				} else {
					j.setStatus(false);
					j.setMsg("用户名或密码错误！");
				}
			} else {
				j.setStatus(false);
				j.setMsg("验证码错误！");
			}
			return j ;
		} catch (Exception e) {
			throw new Exception("发生错误：" + e.getMessage()) ;
		}
	}
	
	/**
	 * 修改我的密码
	 * @param session
	 * @param oldPwd
	 * @param pwd
	 */
	@RequestMapping("/doNotNeedAuth_editMyUserPwd.do")
	@ResponseBody
	public Json doNotNeedAuth_editMyUserPwd(EmployeeForm user, HttpServletRequest request) {
		Json j = new Json();
		try {
			if (request.getSession() != null) {
				LoginInfoSession sessionInfo = (LoginInfoSession) request.getSession().getAttribute(Constants.SESSION_INFO_NAME);
				if (sessionInfo != null) {
					if (this.empservice.editCurrentUserPwd(sessionInfo, user.getOldPwd(), user.getPassword())) {
						j.setStatus(true);
						j.setMsg("编辑密码成功，下次登录生效！");
					} else {
						j.setStatus(false);
						j.setMsg("原密码错误！");
					}
				} else {
					j.setStatus(false);
					j.setMsg("登录超时，请重新登录！");
				}
			} else {
				j.setStatus(false);
				j.setMsg("登录超时，请重新登录！");
			}
			
		} catch (Exception e) {
			j.setStatus(false);
			j.setMsg("修改我的密码失败。<br>" + e.getMessage());
		}
		
		return j ;
	}
	
	/**
	 * 注销登录
	 */
	@RequestMapping("/doNotNeedSession_logout.do")
	@ResponseBody
	public Json doNotNeedSession_logout(HttpServletRequest request) {
		Json j = new Json();
		if (request.getSession() != null) {
			request.getSession().invalidate();
			j.setStatus(true);
		} else {
			j.setStatus(false);
		}
		return j ;
	}
	
	
	@RequestMapping("/getPermission.do")
	@ResponseBody
	public EmployeeForm getPermission(EmployeeForm form) throws Exception {
		return this.empservice.getPermission(form) ;
	}
	

	@RequestMapping("/set_grant.do")
	@ResponseBody
	public Json set_grant(EmployeeForm form) throws Exception {
		Json j = new Json();
		try {
			this.empservice.set_grant(form) ;
			j.setStatus(true);
			j.setMsg("权限分配成功！");
		} catch (Exception e) {
			j.setStatus(false);
			j.setMsg("权限分配失败：" + e.getMessage());
		}
		return j ;
	}

}
