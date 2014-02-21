package com.infox.sysmgr.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.infox.common.util.Constants;
import com.infox.common.web.BaseController;
import com.infox.common.web.page.DataGrid;
import com.infox.common.web.page.Json;
import com.infox.sysmgr.service.TaskSchedulerServiceI;
import com.infox.sysmgr.web.form.TaskForm;

@Controller
@RequestMapping("/sysmgr/task")
public class TaskScheduler extends BaseController {
	
	@Autowired
	private TaskSchedulerServiceI taskservice ;
	
	@RequestMapping("/task_main.do")
	public String task_main() throws Exception {
		return  Constants.SYSTEM + "task_main" ;
	}
	
	@RequestMapping("/doNotNeedAuth_task_cron.do")
	public String task_cron(TaskForm form, HttpServletRequest request) throws Exception {
		return Constants.SYSTEM + "task_cron" ;
	}
	
	@RequestMapping("/task_form.do")
	public String task_form(TaskForm form, HttpServletRequest request) throws Exception {
		if(null != form.getId() && !"".equals(form.getId())) {
			request.setAttribute("id", form.getId()) ;
		}
		return Constants.SYSTEM + "task_form" ;
	}
	
	@RequestMapping("/get.do")
	@ResponseBody
	public TaskForm get(TaskForm form, HttpServletRequest request) throws Exception {
		return this.taskservice.get(form.getId()) ;
	}
	
	@RequestMapping("/add.do")
	@ResponseBody
	public Json add(TaskForm form) throws Exception {
		Json j = new Json() ;
		try {
			this.taskservice.add(form) ;
		} catch (Exception e) {
			e.printStackTrace() ;
			throw new Exception(e.getMessage());
		}
		j.setStatus(true) ;
		return j ;
	}
	
	@RequestMapping("/edit.do")
	@ResponseBody
	public Json edit(TaskForm form) throws Exception {
		Json j = new Json() ;
		try {
			this.taskservice.edit(form) ;
			j.setStatus(true) ;
		} catch (Exception e) {
			throw e;
		}
		return j ;
	}
	
	@RequestMapping("/delete.do")
	@ResponseBody
	public Json delete(String ids) throws Exception {
		Json j = new Json();
		try {
			if(null != ids && !ids.equalsIgnoreCase("")) {
				String[] id = ids.split(",") ;
				for(int i=0;i<id.length;i++) {
					this.taskservice.delete(id[i]) ;
				}
				j.setStatus(true);
			}
		} catch (Exception e) {
			throw e;
		}
		return j ;
	}
	
	@RequestMapping("/datagrid.do")
	@ResponseBody
	public DataGrid datagrid(TaskForm form) throws Exception {
		return this.taskservice.datagrid(form) ;
	}
}
