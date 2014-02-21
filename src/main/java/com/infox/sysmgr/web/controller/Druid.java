package com.infox.sysmgr.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.infox.common.web.BaseController;

/**
 * @程序编写者：杨浩泉
 * @日期：2013-6-30
 * @类说明：数据源控制器
 */
@Controller
@RequestMapping("/druid")
public class Druid extends BaseController {
	/**
	 * 转向到数据源监控页面
	 * 
	 * @return
	 */
	@RequestMapping("/druid.do")
	public String druid() {
		return "redirect:/druid/index.html";
	}

}

