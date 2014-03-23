package com.infox.sysmgr.job;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import com.infox.common.util.DateUtil;
import com.infox.common.web.springmvc.RealPathResolver;

public class SchedulerEmail implements Job {
	
	private static Logger logger = Logger.getLogger(SchedulerEmail.class);
	
	private RealPathResolver realPathResolver ;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			SchedulerContext schCtx = context.getScheduler().getContext();  
			//获取Spring中的上下文    
			ApplicationContext appCtx = (ApplicationContext)schCtx.get("applicationContext");
			
			realPathResolver = (RealPathResolver) appCtx.getBean("servletContextRealPathResolver") ;
			
			logger.info(realPathResolver.get("")) ;
			
			JobKey jobKey = context.getJobDetail().getKey();
			System.out.println("执行任务"+"[定时邮件]: " + jobKey + " 运行时间：" + DateUtil.formatF(new Date()));
		} catch (BeansException e) {
			e.printStackTrace();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}		
	}

}
