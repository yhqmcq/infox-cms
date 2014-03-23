package com.infox.common.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.infox.common.web.page.LoginInfoSession;

/**
 * 系统模块常用常量定义
 * @author Administrator
 *
 */
public class Constants {
	/**
	 * 用户登陆的Session Key
	 */
	public static final String SESSION_INFO_NAME= "LOGIN_USER_KEY";
	/**
	 * 静态站点根目录（服务器启动创建 web project）
	 */
	public static final String WWWROOT_RELAESE= "wwwroot_release";
	/**
	 * 文件根目录下（服务器启动创建）
	 */
	public static final String FILE_ROOT= "file_root";
	/**
	 * 数据库备份路径
	 */
	public static final String BACKUP_PATH = "/WEB-INF/buckup";
	/**
	 * 压缩文件的后缀
	 */
	public static final String FILE_SUFFIX_ZIP = ".zip";
	/**
	 * 数据库备份文本前缀
	 */
	public static String ONESQL_PREFIX="INFOX_BACKUP_";
	/**
	 * 安全目录的前缀URL地址
	 */
	public static final String SYSTEM = "/WEB-INF/security_views/sysmgr/";
	
	/**
	 * 获取当前登录用户实体类
	 * @return 登录用户实体类
	 */
	public static LoginInfoSession getSessionInfo(){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return (LoginInfoSession) request.getSession().getAttribute(SESSION_INFO_NAME) ;
	}

	public static void main(String[] args) {
	}

}
