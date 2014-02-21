package com.infox.sysmgr.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.infox.common.util.UUIDHexGenerator;

@Entity
@Table(name = "INFOX_SYSMGR_EMP_ONLINE")
@DynamicUpdate(true)
@DynamicInsert(true)
public class EmpOnlineEntity {
	
	private String id;
	
	private String empid ;
	
	private String ip;// 用户IP
	
	private String account;// 用户登录名
	
	private String truename; //真实姓名
	
	private String type;// 1.登录0.注销
	
	private Date logindate = new Date() ;//登陆时间
	
	@Id
	public String getId() {
		if (this.id != null) {
			return this.id;
		}
		return UUIDHexGenerator.generator().toString();
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getEmpid() {
		return empid;
	}

	public void setEmpid(String empid) {
		this.empid = empid;
	}

	public String getIp() {
		return ip;
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
