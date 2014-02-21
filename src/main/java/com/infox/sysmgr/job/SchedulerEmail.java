package com.infox.sysmgr.job;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

import com.infox.common.util.DateUtil;

public class SchedulerEmail implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobKey jobKey = context.getJobDetail().getKey();
		System.out.println("执行任务"+"[定时邮件]: " + jobKey + " 运行时间：" + DateUtil.formatF(new Date()));		
	}

}
