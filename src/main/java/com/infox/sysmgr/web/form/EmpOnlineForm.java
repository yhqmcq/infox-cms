package com.infox.sysmgr.web.form;

import java.util.Date;

import com.infox.common.web.page.PageHelper;

public class EmpOnlineForm extends PageHelper {

	private String id;
	
	private String empid ;// 用户ID 
	
	private String ip;// 用户IP
	
	private String account;// 用户登录名
	
	private String truename; //真实姓名
	
	private String type;// 1.登录0.注销
	
	private Date logindate = new Date() ;//登陆时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public String getEmpid() {
		return empid;
	}

	public void setEmpid(String empid) {
		this.empid = empid;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getLogindate() {
		return logindate;
	}

	public void setLogindate(Date logindate) {
		this.logindate = logindate;
	}
	
	
	
}
