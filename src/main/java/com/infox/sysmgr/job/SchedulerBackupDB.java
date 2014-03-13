package com.infox.sysmgr.job;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.context.ApplicationContext;

import com.infox.common.web.springmvc.RealPathResolver;

public class SchedulerBackupDB implements Job {
	
	private static Logger logger = Logger.getLogger(SchedulerBackupDB.class);
	
	private RealPathResolver realPathResolver ;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			SchedulerContext schCtx = context.getScheduler().getContext();  
			//获取Spring中的上下文    
			ApplicationContext appCtx = (ApplicationContext)schCtx.get("applicationContext");
			
			realPathResolver = (RealPathResolver) appCtx.getBean("servletContextRealPathResolver") ;
			
			logger.info(realPathResolver.get("")) ;
			
		} catch (Exception e) {
			e.printStackTrace();
		}  
	}
}
