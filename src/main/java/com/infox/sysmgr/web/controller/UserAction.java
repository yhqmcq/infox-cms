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
	
	/**
	 * 登陆验证
	 * @throws Exception 
	 */
	@RequestMapping("/doNotNeedSession_login.do")
	@ResponseBody
	public Json doNotNeedSession_login(UserForm form, HttpServletRequest request) throws Exception {
		Json j = new Json();
		try {
			
			//验证码比较
			String c = (String)request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
			if(form.getKaptcha().equalsIgnoreCase(c)) {
				UserForm user = this.userService.login(form);
				if (user != null) {
					if(0 == user.getStatus()) {
						request.getSession().removeAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
						
						user.setIp(IpUtil.getIpAddr(request)) ;
						LoginInfoSession LoginInfo = new LoginInfoSession();
						LoginInfo.setUser(user) ;
						LoginInfo.setResourceList(this.userService.MyPermission(user.getId(), user.getAccount()));
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
	public Json doNotNeedAuth_editMyUserPwd(UserForm user, HttpServletRequest request) {
		Json j = new Json();
		try {
			if (request.getSession() != null) {
				LoginInfoSession sessionInfo = (LoginInfoSession) request.getSession().getAttribute(Constants.SESSION_INFO_NAME);
				if (sessionInfo != null) {
					if (this.userService.editCurrentUserPwd(sessionInfo, user.getOldPwd(), user.getPassword())) {
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
	
	
}
