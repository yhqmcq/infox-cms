package com.infox.sysmgr.job;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;

import com.infox.common.util.Constants;
import com.infox.common.util.DateUtil;
import com.infox.common.util.StringUtil;
import com.infox.common.web.springmvc.RealPathResolver;
import com.infox.sysmgr.service.BaseDataBackServiceI;

public class SchedulerBackupDB implements Job {
	
	private static Logger logger = Logger.getLogger(SchedulerBackupDB.class);
	
	private static String SUFFIX = "sql";
	private static String SPLIT = "`";
	private static String BR = "\r\n";
	private static String SLASH="/";
	private static String SPACE = " ";
	private static String BRANCH = ";";
	private static String INSERT_INTO = " INSERT INTO ";
	private static String VALUES = "VALUES";
	private static String LEFTBRACE = "(";
	private static String RIGHTBRACE = ")";
	private static String QUOTES = "'";
	private static String COMMA = ",";
	private static String DISABLEFOREIGN = "SET FOREIGN_KEY_CHECKS = 0;\r\n";
	private static String ABLEFOREIGN = "SET FOREIGN_KEY_CHECKS = 1;\r\n";
	
	private RealPathResolver realPathResolver ;
	
	private BaseDataBackServiceI dataBackupService ;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			SchedulerContext schCtx = context.getScheduler().getContext();  
			//获取Spring中的上下文    
			ApplicationContext appCtx = (ApplicationContext)schCtx.get("applicationContext");
			
			realPathResolver = (RealPathResolver) appCtx.getBean("servletContextRealPathResolver") ;
			dataBackupService = (BaseDataBackServiceI) appCtx.getBean("baseDataBackServiceImpl") ;
			
			
			JobKey jobKey = context.getJobDetail().getKey();
			System.out.println("执行任务"+"[定时备份数据库]: " + jobKey + " 运行时间：" + DateUtil.formatF(new Date()));	
			
			String[] tableNames = null ;
			List<Map<String, String>> tables = dataBackupService.listTables(dataBackupService.getDefaultCatalog()) ;
			if(null != tables && tables.size() > 0) {
				tableNames = new String[tables.size()] ;
				for(int i=0; i<tables.size(); i++) {
					Map<String, String> map = tables.get(i) ;
					tableNames[i] = map.get("table_name") ;
					logger.info("备份的数据库表:["+map.get("table_name")+"]  ["+i+"]") ;
				}
				
				String backpath = realPathResolver.get(Constants.BACKUP_PATH);
				File backDirectory = new File(backpath);
				if (!backDirectory.exists()) {
					backDirectory.mkdir();
				}
				String backFilePath = backpath + SLASH+ DateUtil.formatI(new Date()) + "." + SUFFIX;
				File file=new File(backFilePath);
				Thread thread =new DateBackupTableThread(file,tableNames);
				thread.start();
			}
			
		} catch (SchedulerException e1) {
			e1.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}  
	}

	private class DateBackupTableThread extends Thread{
		private File file;
		private String[] tablenames;
		public DateBackupTableThread(File file, String[] tablenames) {
			super();
			this.file = file;
			this.tablenames = tablenames;
		}
		public void run() {
			FileOutputStream out;
			OutputStreamWriter writer=null;
			try {
				out = new FileOutputStream(file);
				writer = new OutputStreamWriter(out, "utf8");
				writer.write(Constants.ONESQL_PREFIX + DISABLEFOREIGN);
				for (int i=0;i<tablenames.length;i++) {
					backupTable(writer,tablenames[i]);
				}
				writer.write(Constants.ONESQL_PREFIX + ABLEFOREIGN);
				writer.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		private   String backupTable(OutputStreamWriter writer,String tablename) throws IOException {
			writer.write(createOneTableSql(tablename));
			writer.flush();
			return tablename;
		}

		private String createOneTableSql(String tablename) {
			StringBuffer buffer = new StringBuffer();
			Object[] oneResult;
			buffer.append(Constants.ONESQL_PREFIX + "DROP TABLE IF EXISTS "
					+ tablename + BRANCH + BR);
			buffer.append(Constants.ONESQL_PREFIX
					+ dataBackupService.createTableDDL(tablename) + BRANCH + BR
					+ Constants.ONESQL_PREFIX);
			List<Object[]> results = dataBackupService.createTableData(tablename);
			for (int i = 0; i < results.size(); i++) {
				// one insert sql
				oneResult = results.get(i);
				buffer.append(createOneInsertSql(oneResult, tablename));
			}
			return buffer.toString();
		}

		private String createOneInsertSql(Object[] oneResult, String tablename) {
			StringBuffer buffer = new StringBuffer();
			buffer.append(Constants.ONESQL_PREFIX + INSERT_INTO + SPLIT + tablename
					+ SPLIT + SPACE + VALUES + LEFTBRACE);
			for (int j = 0; j < oneResult.length; j++) {
				if (oneResult[j] != null) {
					if (oneResult[j] instanceof Date) {
						buffer.append(QUOTES + oneResult[j] + QUOTES);
					} else if (oneResult[j] instanceof String) {
						buffer.append(QUOTES
								+ StringUtil.replaceKeyString((String) oneResult[j])
								+ QUOTES);
					} else if (oneResult[j] instanceof Boolean) {
						if ((Boolean) oneResult[j]) {
							buffer.append(1);
						} else {
							buffer.append(0);
						}
					} else {
						buffer.append(oneResult[j]);
					}
				} else {
					buffer.append(oneResult[j]);
				}
				buffer.append(COMMA);
			}
			buffer = buffer.deleteCharAt(buffer.lastIndexOf(COMMA));
			buffer.append(RIGHTBRACE + BRANCH + BR);
			return buffer.toString();
		}
	}

}
