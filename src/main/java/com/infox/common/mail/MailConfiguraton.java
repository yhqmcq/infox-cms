package com.infox.common.mail;

import com.infox.common.util.ConfigUtil;

/**
 * 说明：单例模式，加载配置信息
 * <br/>作者：杨浩泉
 * <br/>日期：2009-10-1
 */
public class MailConfiguraton {
	
	private String mailSMTPHost ;	/** 缺省的主机名称 */
	
	private String mailSMTPPort ;	/** 缺省的SMTP端口号 */
	
	private String mailPOP3Host ;	/** 缺省的主机名称 */
	
	private String mailPOP3Port ;	/** 缺省的POP3端口号 */
	
	private String mailPOP3Protocol ;	/** 邮箱连接协议 */
	
	private String mailAuth ;			/** 是否需要验证 */
	
	private String mailAliasName ;		/** 是否需要验证 */
	
	private String mailFromAdmin ;	/** 缺省为管理员发送人地址 */
	
	private String userNameAdmin ;	/** 缺省为管理员发送人的邮箱账号 */
	
	private String passWordAdmin ;	/** 缺省为管理员发送人的邮箱密码 */
	
	private static MailConfiguraton intance = null ; /** 加载属性文件中的SMTP信息 */
	
	public static MailConfiguraton getInstance() {
		if(null == intance) {
			intance = new MailConfiguraton() ;
			return intance ;
		} else {
			return intance ;
		}
	}
	
	private MailConfiguraton() {
		this.setMailSMTPHost(ConfigUtil.get("mail.smtp.host")) ;
		this.setMailSMTPPort(ConfigUtil.get("mail.smtp.port")) ;
		this.setMailPOP3Host( ConfigUtil.get("mail.pop3.host")) ;
		this.setMailPOP3Port(ConfigUtil.get("mail.pop3.port")) ;
		this.setMailPOP3Protocol(ConfigUtil.get("mail.pop3.protocol")) ;
		this.setMailAuth(ConfigUtil.get("mail.smtp.auth")) ;
		this.setMailAliasName(ConfigUtil.get("mail.aliasname")) ;
		this.setMailFromAdmin( ConfigUtil.get("mail.from")) ;
		this.setUserNameAdmin(ConfigUtil.get("mail.username")) ;
		this.setPassWordAdmin(ConfigUtil.get("mail.password")) ;
	}

	/** 获取邮箱域名 */
	public static String getEmailDomain() {
		String domain = ConfigUtil.get("mail.smtp.host") ;
		return "@"+domain.substring(domain.indexOf(".")+1, domain.length()) ; 
	}
	
	public String getMailSMTPHost() {
		return mailSMTPHost;
	}

	public void setMailSMTPHost(String mailSMTPHost) {
		this.mailSMTPHost = mailSMTPHost;
	}

	public String getMailSMTPPort() {
		return mailSMTPPort;
	}

	public String getMailAliasName() {
		return mailAliasName;
	}

	public void setMailAliasName(String mailAliasName) {
		this.mailAliasName = mailAliasName;
	}

	public void setMailSMTPPort(String mailSMTPPort) {
		this.mailSMTPPort = mailSMTPPort;
	}

	public String getMailPOP3Host() {
		return mailPOP3Host;
	}

	public void setMailPOP3Host(String mailPOP3Host) {
		this.mailPOP3Host = mailPOP3Host;
	}

	public String getMailPOP3Port() {
		return mailPOP3Port;
	}

	public void setMailPOP3Port(String mailPOP3Port) {
		this.mailPOP3Port = mailPOP3Port;
	}

	public String getMailPOP3Protocol() {
		return mailPOP3Protocol;
	}

	public void setMailPOP3Protocol(String mailPOP3Protocol) {
		this.mailPOP3Protocol = mailPOP3Protocol;
	}

	public String getMailAuth() {
		return mailAuth;
	}

	public void setMailAuth(String mailAuth) {
		this.mailAuth = mailAuth;
	}

	public String getMailFromAdmin() {
		return mailFromAdmin;
	}

	public void setMailFromAdmin(String mailFromAdmin) {
		this.mailFromAdmin = mailFromAdmin;
	}

	public String getUserNameAdmin() {
		return userNameAdmin;
	}

	public void setUserNameAdmin(String userNameAdmin) {
		this.userNameAdmin = userNameAdmin;
	}

	public String getPassWordAdmin() {
		return passWordAdmin;
	}

	public void setPassWordAdmin(String passWordAdmin) {
		this.passWordAdmin = passWordAdmin;
	}

}
