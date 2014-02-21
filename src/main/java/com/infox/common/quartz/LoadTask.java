package com.infox.common.quartz;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.infox.sysmgr.entity.TaskEntity;
import com.infox.sysmgr.service.TaskSchedulerServiceI;
import com.infox.sysmgr.web.form.TaskForm;

public class LoadTask {

	@Autowired
	private TaskSchedulerServiceI taskSchedulerService ;
	
	@Autowired
	private SchedulerUtil scheduler ;
	
	/**
	 * 服务器启动加载所有定时任务(task_enable=Y)
	 */
	public void loadTask() {
		try {
			TaskForm form = new TaskForm() ;
			form.setTask_enable("Y") ;
			List<TaskEntity> entitys = this.taskSchedulerService.find(form) ;
			for (TaskEntity entity : entitys) {
				TaskForm task = new TaskForm() ;
				BeanUtils.copyProperties(entity, task) ;
				this.scheduler.scheduler(task) ;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
