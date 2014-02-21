package com.infox.common.web;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.infox.common.web.springmvc.DateEditor;

/**
 * @程序编写者：杨浩泉
 * @日期：2013-6-30
 * @类说明：基础控制器
 * 其他控制器继承此控制器获得日期字段类型转换和防止XSS攻击的功能
 */
@Controller
@RequestMapping("/baseController")
public class BaseController {

	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		/**
		 * 自动转换日期类型的字段格式
		 */
		binder.registerCustomEditor(Date.class, new DateEditor());

		/**
		 * 防止XSS攻击
		 */
		//binder.registerCustomEditor(String.class, new StringEscapeEditor(true, false));
	}

	/**
	 * 用户跳转JSP页面
	 * 
	 * 此方法不考虑权限控制
	 * 
	 * @param folder
	 *            路径
	 * @param jspName
	 *            JSP名称(不加后缀)
	 * @return 指定JSP页面
	 */
	@RequestMapping("/{folder}/{jspName}")
	public String redirectJsp(@PathVariable String folder, @PathVariable String jspName) {
		return "/" + folder + "/" + jspName;
	}

}
