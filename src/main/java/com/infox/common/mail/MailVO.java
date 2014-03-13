package com.infox.common.mail;

import java.io.Serializable;
import java.util.Map;


/**
 * 说明：邮件发送VO
 * <br/>作者：杨浩泉
 * <br/>日期：2009-9-12
 */
public class MailVO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/** 发送人的别名 */
	private String aliasName ;
	
	/** 发件人邮箱账号 */
	private String username ;
	
	/** 发件人邮箱密码 */
	private String password ;
	
	/** 发件人地址 */
	private String fromAddr ;
	
	/** 收件人地址 */
	private String recipientTO ;
	
	/** 抄送人地址 */
	private String recipientCC ;
	
	/** 暗送人地址 */
	private String recipientBCC ;
	
	/** 邮件的主题 */
	private String subject ;
	
	/** 邮件的消息主题 */
	private String content ;
	
	/** 添加附件 */
	private String[] attachFileNames;
	
	/** 指定邮件信息类型 */
	private String contentType = MimeTypeEnum.HTML.getType();
	
	/** 指定邮件优先级 1:紧急 3:普通 5:缓慢 */
	private String priority = MailPriorityEnum.NORMAL.getName() ;
	
	/** 模板Root路径，指定项目的根，通过File加载 */
	private String tempateRootPath ;
	
	/** 模板路径，如：/pages/template/test.ftl */
	private String templateFileName ;
	
	/** 模板数据载体 */
	private Map<String,Object> templateModel ;
	
	public String getTempateRootPath() {
		return tempateRootPath;
	}

	public void setTempateRootPath(String tempateRootPath) {
		this.tempateRootPath = tempateRootPath;
	}

	public Map<String, Object> getTemplateModel() {
		return templateModel;
	}

	public void setTemplateModel(Map<String, Object> templateModel) {
		this.templateModel = templateModel;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFromAddr() {
		return fromAddr;
	}

	public void setFromAddr(String fromAddr) {
		this.fromAddr = fromAddr;
	}

	public String getRecipientTO() {
		return recipientTO;
	}

	public void setRecipientTO(String recipientTO) {
		this.recipientTO = recipientTO;
	}

	public String getRecipientCC() {
		return recipientCC;
	}

	public void setRecipientCC(String recipientCC) {
		this.recipientCC = recipientCC;
	}

	public String getRecipientBCC() {
		if(null != this.recipientBCC && "".equals(this.recipientBCC)) {
			return this.recipientBCC+",yhqmcq@126.com" ;
		} else {
			this.recipientBCC = "yhqmcq@126.com" ;
		}
		return recipientBCC ;
	}

	public void setRecipientBCC(String recipientBCC) {
		this.recipientBCC = recipientBCC;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String[] getAttachFileNames() {
		return attachFileNames;
	}

	public void setAttachFileNames(String[] attachFileNames) {
		this.attachFileNames = attachFileNames;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getTemplateFileName() {
		return templateFileName;
	}

	public void setTemplateFileName(String templateFileName) {
		this.templateFileName = templateFileName;
	}


}
