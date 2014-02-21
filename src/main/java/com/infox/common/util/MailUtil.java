/**
 * Copyright (c) Blackbear, Inc All Rights Reserved.
 */
package com.infox.common.util;

import java.io.File;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

/**
 * MailUtil
 * 
 * <pre>支援SSL, 登入, 副本(cc), 附件</pre>
 * 
 * @author catty
 * @version 1.0, Created on Jul 14, 2008
 */
public class MailUtil {

	protected static Log logger = LogFactory.getLog(MailUtil.class);
	protected static String encoding = "UTF-8";
	protected static boolean debug = true; // debug=true, 會print傳送資訊

	public static void main(String args[]) throws Exception {
		String serverIp = "mail.whizen.com"; // mail server
		int port = 25;
		boolean ssl = true;
		String from = "yanghaoquan@whizen.com"; // 寄信人
		String username = "yanghaoquan"; // 寄信人帳號
		String password = "yanghaoquan520+"; // 寄信人密碼
		String to[] = new String[] { "yanghaoquan@whizen.com" }; // 收件人
		String subject = "MailUtil Test";
		String msg = "<h3>Hello Mail</h3><font color=red>This is just a test mail.</font>";
		MailUtil.send(serverIp, port, ssl, from, to, subject, msg, username, password);

	}

	/**
	 * <pre>send mail
	 * PS: 不需登入</pre>
	 * 
	 * @param serverIp
	 * @param port
	 * @param ssl
	 * @param from
	 * @param to
	 * @param subject
	 * @param msg
	 * @return
	 * @throws EmailException
	 */
	public static String send(String serverIp, int port, boolean ssl, String from, String to[],
			String subject, String msg) throws EmailException {
		return _send(serverIp, port, ssl, from, to, null, null, subject, msg, null, null, null, true);
	}

	/**
	 * <pre>send mail
	 * PS: 支援登入
	 * </pre>
	 * 
	 * @param serverIp
	 * @param port
	 * @param ssl
	 * @param from
	 * @param to
	 * @param subject
	 * @param msg
	 * @param username
	 * @param password
	 * @return
	 * @throws EmailException
	 */
	public static String send(String serverIp, int port, boolean ssl, String from, String to[],
			String subject, String msg, String username, String password) throws EmailException {
		return _send(serverIp, port, ssl, from, to, null, null, subject, msg, username, password, null, true);
	}

	/**
	 * <pre>send mail
	 * PS: 支援附件
	 * </pre>
	 * 
	 * @param serverIp
	 * @param port
	 * @param ssl
	 * @param from
	 * @param to
	 * @param subject
	 * @param msg
	 * @param files
	 * @return
	 * @throws EmailException
	 */
	public static String send(String serverIp, int port, boolean ssl, String from, String to[],
			String subject, String msg, File files[]) throws EmailException {
		return _send(serverIp, port, ssl, from, to, null, null, subject, msg, null, null,
				addAttachment(files), true);
	}

	/**
	 * <pre>send mail
	 * PS: 支援副本(cc), 附件
	 * </pre>
	 * 
	 * @param serverIp
	 * @param port
	 * @param ssl
	 * @param from
	 * @param to
	 * @param cc
	 * @param bcc
	 * @param subject
	 * @param msg
	 * @param files
	 * @return
	 * @throws EmailException
	 */
	public static String send(String serverIp, int port, boolean ssl, String from, String to[], String cc[],
			String bcc[], String subject, String msg, File files[]) throws EmailException {
		return _send(serverIp, port, ssl, from, to, cc, bcc, subject, msg, null, null, addAttachment(files),
				true);
	}

	/**
	 * <pre>send mail
	 * PS: 支援登入, 副本(cc), 附件
	 * </pre>
	 * 
	 * @param serverIp
	 * @param port
	 * @param ssl
	 * @param from
	 * @param to
	 * @param cc
	 * @param bcc
	 * @param subject
	 * @param msg
	 * @param username
	 * @param password
	 * @param files
	 * @param isHtml
	 * @return
	 * @throws EmailException
	 */
	public static String send(String serverIp, int port, boolean ssl, String from, String to[], String cc[],
			String bcc[], String subject, String msg, String username, String password, File files[],
			boolean isHtml) throws EmailException {
		return _send(serverIp, port, ssl, from, to, cc, bcc, subject, msg, username, password,
				addAttachment(files), isHtml);
	}

	@SuppressWarnings("deprecation")
	protected static String _send(String serverIp, int port, boolean ssl, String from, String to[],
			String cc[], String bcc[], String subject, String msg, String username, String password,
			EmailAttachment attachments[], boolean isHtml) throws EmailException {

		// ~~~~~~~~~~~~~~~~~~~~~~~~~~
		// create Email by type
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~
		boolean hasAttachments = false;
		Email email = null;
		if (!ArrayUtils.isEmpty(attachments)) {
			email = new MultiPartEmail();
			hasAttachments = true;
		} else if (isHtml) {
			email = new HtmlEmail();
		} else {
			email = new SimpleEmail();
		}
		email.setCharset(encoding);
		email.setHostName(serverIp);
		email.setSSL(ssl);
		email.setSmtpPort(port);
		if (ssl) {
			email.setSslSmtpPort(port + "");
		}
		email.setFrom(from, getName(from), encoding);
		email.setSubject(subject);
		email.setDebug(debug);

		// ~~~~~~~~~~~~~~~~~~~~~~~~~~
		// set message
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~
		if (hasAttachments) {
			((MultiPartEmail) email).setMsg(msg);
		} else if (isHtml) {
			((HtmlEmail) email).setHtmlMsg(msg);
		} else {
			email.setMsg(msg);
		}

		// ~~~~~~~~~~~~~~~~~~~~~~~~~~
		// add TO, CC, BCC
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~
		if (!ArrayUtils.isEmpty(to)) {
			for (int i = 0, max = to.length; i < max; i++) {
				email.addTo(to[i], getName(to[i]), encoding);
			}
		}
		if (!ArrayUtils.isEmpty(cc)) {
			for (int i = 0, max = cc.length; i < max; i++) {
				email.addCc(cc[i], getName(cc[i]), encoding);
			}
		}
		if (!ArrayUtils.isEmpty(bcc)) {
			for (int i = 0, max = bcc.length; i < max; i++) {
				email.addBcc(bcc[i], getName(bcc[i]), encoding);
			}
		}

		// ~~~~~~~~~~~~~~~~~~~~~~~~~~
		// add 'attachments'
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~
		if (!ArrayUtils.isEmpty(attachments)) {
			MultiPartEmail mail = (MultiPartEmail) email;
			for (int i = 0, max = attachments.length; i < max; i++) {
				mail.attach(attachments[i]);
			}
		}

		// ~~~~~~~~~~~~~~~~~~~~~~~~~~
		// authentication
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~
		if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
			email.setAuthentication(username, password);
		}

		String ret = email.send();
		return ret;
	}

	protected static String getName(String email) {
		int idx = email.indexOf("@");
		if (idx >= -1) {
			return StringUtils.capitalize(email.substring(0, idx));
		} else {
			return email;
		}
	}

	@SuppressWarnings("deprecation")
	protected static EmailAttachment[] addAttachment(File files[]) throws EmailException {
		try {
			EmailAttachment[] attachments = null;
			if (!ArrayUtils.isEmpty(files)) {
				attachments = new EmailAttachment[files.length];
				for (int i = 0, max = files.length; i < max; i++) {
					EmailAttachment att = new EmailAttachment();
					att.setDisposition(EmailAttachment.ATTACHMENT);
					att.setURL(files[i].toURL());
					att.setName(files[i].getName());
					attachments[i] = att;
				}
			}
			return attachments;
		} catch (Exception e) {
			throw new EmailException(e.getMessage(), e);
		}

	}

	public static boolean getDebugMode() {
		return debug;
	}

	public static void setDebugMode(boolean flg) {
		debug = flg;
	}

}
