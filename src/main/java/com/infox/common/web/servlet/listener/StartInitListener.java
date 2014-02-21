package com.infox.common.web.servlet.listener;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.infox.common.util.Constants;
import com.infox.common.web.page.LoginInfoSession;
import com.infox.sysmgr.service.EmployeeOnlineServiceI;
import com.infox.sysmgr.service.MenuServiceI;
import com.infox.sysmgr.web.form.EmpOnlineForm;

/**
 * 服务器启动初始化
 * @author Administrator
 *
 */
@WebListener
public class StartInitListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {
	
	private Logger logger = Logger.getLogger(StartInitListener.class) ;

	private static ApplicationContext ctx = null;
	
	@Override
	public void contextInitialized(ServletContextEvent evt) {
		logger.info("#######[服务器启动]######");
		ctx = WebApplicationContextUtils.getWebApplicationContext(evt.getServletContext());
		
		initialized(evt);
	}

	@Override
	public void contextDestroyed(ServletContextEvent evt) {
		logger.info("#######[服务器关闭]######");
	}
	
	public void initialized(ServletContextEvent evt) {
		generateMenus(evt) ;
		createParentFolder(evt) ;
	}
	
	/**
	 * 创建站点目录和文件管理目录
	 * @param evt
	 */
	public void createParentFolder(ServletContextEvent evt) {
		String parentPath = new File(evt.getServletContext().getRealPath("/")).getParent() ;
		
		logger.info("#######[启动初始化]######当前项目环境的父目录创建["+Constants.WWWROOT_RELAESE+"]目录") ;
		File wwwroot = new File(parentPath+File.separator+Constants.WWWROOT_RELAESE) ;
		if(!wwwroot.exists()) {
			wwwroot.mkdirs() ;
		}
		logger.info("#######[启动初始化]######当前项目环境的父目录创建["+Constants.FILE_ROOT+"]目录") ;
		File fileroot = new File(parentPath+File.separator+Constants.FILE_ROOT) ;
		if(!fileroot.exists()) {
			fileroot.mkdirs() ;
		}
	}
	
	
	/**
	 * 生成JSON 系统导航菜单
	 * 方法描述 : 
	 * 创建者：杨浩泉 
	 * 项目名称： destiny-cms
	 * 类名： InitServlet.java
	 * 版本： v1.0
	 * 创建时间： 2013-12-23 下午3:11:06 void
	 */
	public void generateMenus(ServletContextEvent evt) {
		logger.info("#######[启动初始化]######生成系统导航菜单") ;
		MenuServiceI ms = (MenuServiceI) ctx.getBean("menuServiceImpl");
		try {
			ms.exportMenusAll(evt.getServletContext()) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sessionCreated(HttpSessionEvent arg0) { }

	/**
	 * 向session里增加属性时调用(用户成功登陆后会调用)
	 */
	@Override
	public void attributeAdded(HttpSessionBindingEvent evt) {
		try {
			//设置会话超时时间
			HttpSession session = evt.getSession();
			session.setMaxInactiveInterval(1200) ;
			
			String keyName = evt.getName();
			if (Constants.SESSION_INFO_NAME.equals(keyName)) {// 如果存入的属性是sessionInfo的话
				LoginInfoSession sessionInfo = (LoginInfoSession) session.getAttribute(keyName);
				if (sessionInfo != null) {
					EmployeeOnlineServiceI empOnlineService = (EmployeeOnlineServiceI) ctx.getBean("employeeOnlineServiceImpl");
					EmpOnlineForm online = new EmpOnlineForm();
					online.setEmpid(sessionInfo.getEmp().getId()) ;
					online.setType("1");// 登录
					online.setIp(sessionInfo.getEmp().getIp()) ;
					online.setAccount(sessionInfo.getEmp().getAccount());
					online.setTruename(sessionInfo.getEmp().getTruename()) ;
					online.setIp(sessionInfo.getEmp().getIp());
					empOnlineService.add(online);
				}
				logger.info("用户登录成功，将用户会话存入Session[Key:"+keyName+" Account:"+sessionInfo.getEmp().getAccount()+" Name:"+sessionInfo.getEmp().getTruename()+"]");
			}
		} catch (BeansException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * session销毁(用户退出系统时会调用)
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent evt) {
		try {
			HttpSession session = evt.getSession();
			if (session != null) {
				
				LoginInfoSession sessionInfo = (LoginInfoSession) session.getAttribute(Constants.SESSION_INFO_NAME);
				if (sessionInfo != null) {
					EmployeeOnlineServiceI empOnlineService = (EmployeeOnlineServiceI) ctx.getBean("employeeOnlineServiceImpl");
					EmpOnlineForm online = new EmpOnlineForm();
					online.setEmpid(sessionInfo.getEmp().getId()) ;
					online.setIp(sessionInfo.getEmp().getIp()) ;
					online.setType("0");// 注销
					online.setAccount(sessionInfo.getEmp().getAccount());
					online.setTruename(sessionInfo.getEmp().getTruename()) ;
					online.setIp(sessionInfo.getEmp().getIp());
					empOnlineService.add(online);
					
					logger.info("用户会话失效，将用户从Session中移除[Account:"+sessionInfo.getEmp().getAccount()+" Name:"+sessionInfo.getEmp().getTruename()+"]");
				}
			}
		} catch (BeansException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent arg0) {
		
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent arg0) {
		
	}

}
