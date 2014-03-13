package com.infox.common.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 说明：创建一个密码验证器
 * <br/>作者：杨浩泉
 * <br/>日期：2009-9-5
 */
public class MyAuthenticator extends Authenticator{
	
	private String username ;
	
	private String password ;
	
	
	public MyAuthenticator(String username,String password) {
		this.username = username ;
		this.password = password ;
	}

	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(username, password) ;
	}
	
}
