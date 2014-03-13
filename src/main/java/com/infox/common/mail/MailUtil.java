package com.infox.common.mail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.infox.common.util.HtmlUtil;
import com.infox.common.util.ValidateEmail;

/**
 * 说明：邮件发送
 * <br/>作者：杨浩泉
 * <br/>日期：2009-9-5
 */
@Component
public class MailUtil {
	private static final Logger logger = Logger.getLogger(MailUtil.class);
	
	private static int success = 0 ;
	
	private static int unsuccess = 0 ;
	
	/**
	 * 邮件发送
	 * @param mv 待发送的信息
	 * @throws Exception 
	 */
	public static boolean send(MailVO mv) throws Exception {
		
		if(null != mv) {
			
			//获得MailSMTP的缺省配置信息
			MailConfiguraton config = MailConfiguraton.getInstance() ;
			try {
				//设置邮件的发送协议
				Properties pros = System.getProperties() ;
				pros.setProperty("mail.smtp.host", config.getMailSMTPHost()) ;
				pros.setProperty("mail.smtp.port", config.getMailSMTPPort()) ;
				pros.setProperty("mail.smtp.auth", config.getMailAuth()) ;
				
				String aliasName = (null != mv.getAliasName() && !"".equals(mv.getAliasName().trim()) ? mv.getAliasName().trim() : config.getMailAliasName()) ;
				String username = (null != mv.getUsername() && !"".equals(mv.getUsername().trim()) ? mv.getUsername().trim() : config.getUserNameAdmin()) ;
				String password = (null != mv.getPassword() && !"".equals(mv.getPassword().trim()) ? mv.getPassword().trim() : config.getPassWordAdmin()) ;
				String from = (null != mv.getFromAddr() && !"".equals(mv.getFromAddr()) ? mv.getFromAddr():config.getMailFromAdmin()) ;
				
				if(null == from) {
					throw new Exception("发件人地址为空！") ;
				}
				if(null == username || null == password) {
					throw new Exception("验证账号或密码为空！") ;
				}
				
				logger.info("****************************发送电子邮件****************************");
				logger.info("标题：" + mv.getSubject() + "\t 发件人：" + from) ;
				//如果不指定发件人地址，则以默认的地址发送
				if(null == mv.getFromAddr() || mv.getFromAddr().trim().equals("")) {
					username = config.getUserNameAdmin() ;
					password = config.getPassWordAdmin() ;
				}
				
				//设置邮件发送的类型
				String mimeType = ("text/html".equals(mv.getContentType())?MimeTypeEnum.HTML.getType():MimeTypeEnum.TEXT.getType()) +"; charset=UTF-8" ;
				
				//指定邮件优先级
				String priority =  (mv.getPriority().trim().equals("URG")?MailPriorityEnum.URG.getName():MailPriorityEnum.NORMAL.getName()) ;
				
				//获得邮件内容
				String mailCtx = mv.getContent() ;
			
				//如果是纯文本类型，则去除所有的HTML标签代码
				if(null != mv.getContentType() && mv.getContentType().equals("text/plain")) {
					mailCtx = HtmlUtil.HtmltoText(mailCtx) ;
				}
				
				//获取基于Properties Session会话对象，并通过MyAuthenticator类来创建一个账号密码验证器
				Session session = Session.getInstance(pros,new MyAuthenticator(username,password)) ;
				//session.setDebug(true) ;
				
				//从Session创建一个MimeMessage对象
				Message msg = new MimeMessage(session) ;
	
				//设置发件人的地址，以及别名，并进行编码
				msg.setFrom(new InternetAddress(from,MimeUtility.encodeWord(aliasName,"UTF-8","Q"))) ;
				//Address from_address=new InternetAddress(from, aliasName);
				//msg.setFrom(from_address);
				
				//设置收件人和抄送以及密送人地址
				setRecipient(mv,msg) ;
	
				//指定邮件优先级 1:紧急 3:普通 5:缓慢
				msg.setHeader("X-Priority", priority);
				
				//设置邮件主题，并进行编码
				msg.setSubject(mv.getSubject()) ;
				
				//设置邮件发送日期
				msg.setSentDate(new Date()) ;
				
				//Multipart作为信息载体的容器，将MimeBodyPart添加到Multipart对象中
				Multipart mp = new MimeMultipart() ;
				
				//MimeBodyPart对象做为信息的载体，MimeBodyPart被认为有两部分
				MimeBodyPart mb1 = new MimeBodyPart() ;
				
				//第一部分为普通信息的载体，并设置MIME类型为text/plain或text/html、multipart/mixed
				mb1.setContent(mailCtx, mimeType) ;
				
				//将第一部分添加到Multipart容器中
				mp.addBodyPart(mb1) ;
				
				//是否有附件，附件信息作为MimeBodyPart的第二部分
				if(null != mv.getAttachFileNames() && mv.getAttachFileNames().length > 0) {
					//设置附件，发送单个附件
					//mb2.attachFile("d:\\互联网新闻开放协议.txt") ;
					//mb2.setFileName(MimeUtility.encodeText("互联网新闻开放协议","UTF-8", "B")) ;
					
					for(int i=0; i<mv.getAttachFileNames().length; i++) {
						//如果该邮件带有附件，需创建第二个MimeBodyPart对象，用来做附件的载体
						MimeBodyPart mb2 = new MimeBodyPart() ;
						
						// 获得附件
						DataSource source = new FileDataSource(mv.getAttachFileNames()[i]) ;
						//设置附件的数据处理器
						mb2.setDataHandler(new DataHandler(source)) ;
						
						// 设置附件文件名
						//mb2.setFileName(MimeUtility.encodeText(source.getName(),"UTF-8","B")) ;
						mb2.setFileName(MimeUtility.encodeText(source.getName())) ;
						
						//加入第二部分到容器中
						mp.addBodyPart(mb2) ;
					}
				}
				
				//将Multipart对象设置到Message中进行发送
				msg.setContent(mp) ;
				
				//通过Transport对象来 发送邮件
				Transport.send(msg) ;
				
				logger.info("****************************邮件发送完成****************************");
				System.out.println("\r\n");
				return true ;
			} catch (AddressException e) {
				e.printStackTrace();
			} catch (NoSuchProviderException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false ;
	}
	
	/**
	 * 设置收件人和抄送以及密送人地址
	 * @param mv
	 * @param msg
	 * @throws MessagingException
	 */
	public static void setRecipient(MailVO mv,Message msg) throws MessagingException {
		
		if(null != mv.getRecipientTO() && !"".equals(mv.getRecipientTO())) {
			//获取所有收件人地址
			List<InternetAddress> list = recipientAddress(mv.getRecipientTO()) ;
			//设置可以群发和单发收件人
			InternetAddress[] toAddress = (InternetAddress[])list.toArray(new InternetAddress[list.size()]) ;
			//设置收件人地址
			msg.addRecipients(RecipientType.TO, toAddress) ;
			
			logger.info("收件人数量："+success + "    收件地址错误数量：" + unsuccess);
			logger.info("所有收件人地址：");
			Object[] array = list.toArray() ;
			if(null != array && array.length > 0) {
				for (int i = 0; i < array.length; i++) {
					logger.info(array[i]);
				}
			} 
			success = 0 ; unsuccess = 0 ;
		}
		if(null != mv.getRecipientCC() && !"".equals(mv.getRecipientCC())) {
			System.out.println();
			
			//获取所有抄送人地址
			List<InternetAddress> list = recipientAddress(mv.getRecipientCC()) ;
			//设置可以群发和单发抄送人
			InternetAddress[] ccAddress = (InternetAddress[])list.toArray(new InternetAddress[list.size()]) ;
			//设置抄送人地址
			msg.addRecipients(RecipientType.CC, ccAddress) ;
			
			logger.info("抄送人数量："+success + "    抄送地址错误数量：" + unsuccess);
			logger.info("所有抄送人地址：");
			Object[] array = list.toArray() ;
			if(null != array && array.length > 0) {
				for (int i = 0; i < array.length; i++) {
					logger.info(array[i]);
				}
			} 
			success = 0 ; unsuccess = 0 ;
		}
		if(null != mv.getRecipientBCC() && !"".equals(mv.getRecipientBCC())) {
			System.out.println();
			
			//获取所有暗送人地址
			List<InternetAddress> list = recipientAddress(mv.getRecipientBCC()) ;
			//设置可以群发和单发暗送人
			InternetAddress[] bccAddress = (InternetAddress[])list.toArray(new InternetAddress[list.size()]) ;
			//设置暗送人的地址
			msg.addRecipients(RecipientType.BCC, bccAddress) ;
			
			logger.info("暗送人数量："+success + "    暗送地址错误数量：" + unsuccess);
			logger.info("所有暗送人地址：");
			StringBuffer strbuf = new StringBuffer() ;
			Object[] array = list.toArray() ;
			if(null != array && array.length > 0) {
				for (int i = 0; i < array.length; i++) {
					if(i%3==0) strbuf.append("\r\n\t\t\t") ;
					strbuf.append(array[i]+",") ;
					logger.info(array[i]) ;
				}
			}
			success = 0 ; unsuccess = 0 ;
		}
	}
	
	/**
	 * 解析群发或单发的邮箱地址
	 * @param toAddr 待解析的邮箱地址，多个邮箱地址以","分隔
	 * @return 集合List<InternetAddress>
	 */
	public static List<InternetAddress> recipientAddress(String toAddr) {
		List<InternetAddress> list = new ArrayList<InternetAddress>() ;
		String[] to = toAddr.split(",") ;
		
		for(int i=0; i<to.length; i++) {
			try {
				if(!"".equals(to[i]) && ValidateEmail.checkMail(to[i])) {
					success++ ;
					list.add(new InternetAddress(to[i])) ;
				} else {
					unsuccess++ ;
					logger.info("【失败==》邮箱地址不符合标准，请检查:"+to[i]+"】");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list ;
	}
	
	
	public static void main(String[] args) {
		
		String form = "yanghaoquan@whizen.com" ;
		String to = "yhqmcq@126.com,yanghaoquan@whizen.com" ;
		String bcc = "yanghaoquan@whizen.com" ;
		
		MailVO mv = new MailVO() ;
		mv.setAliasName("孤独的狼") ;
		mv.setUsername("yanghaoquan") ;
		mv.setPassword("yhqmcq520+") ;
		mv.setFromAddr(form) ;
		mv.setSubject("hello————Test") ;
		mv.setContent("<h1>AAA</h1><img src='cid:c:/精神可嘉.log'/>") ;
		mv.setRecipientTO(to) ;
		mv.setRecipientCC(bcc) ;
		mv.setRecipientBCC(bcc) ;
		//mailinfo.setContentType("text/plain") ;
		mv.setPriority(MailPriorityEnum.URG.getName()) ;
		
		mv.setAttachFileNames(new String[]{"c:/精神可嘉.log"}) ;
		
		try {
			send(mv) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
