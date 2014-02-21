package com.infox.sysmgr.web.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.infox.common.util.Constants;
import com.infox.common.util.DateUtil;
import com.infox.common.util.FileUtil;
import com.infox.common.util.StringUtil;
import com.infox.common.util.ZipUtils;
import com.infox.common.web.BaseController;
import com.infox.common.web.page.DataGrid;
import com.infox.common.web.page.Json;
import com.infox.common.web.springmvc.RealPathResolver;
import com.infox.sysmgr.entity.base.BaseField;
import com.infox.sysmgr.service.BaseDataBackServiceI;
import com.infox.sysmgr.web.form.DataForm;

@Controller
@RequestMapping("/sysmgr/data")
public class DataBackup extends BaseController {
	
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
	private static String backup_table;
	
	@Autowired
	private RealPathResolver realPathResolver ;
	
	@Autowired
	private BaseDataBackServiceI dataBackupService ;
	
	@RequestMapping("/data_main.do")
	public String data_main() throws Exception {
		return Constants.SYSTEM + "data_main" ;
	}
	
	/**
	 * 表字段页面
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/data_table_fields.do")
	public String data_table_fields(DataForm form, HttpServletRequest request) throws Exception {
		request.setAttribute("tableName", form.getTableName()) ;
		return Constants.SYSTEM + "data_table_fields" ;
	}
	
	/**
	 * 恢复页面
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/data_revert.do")
	public String table_revert(DataForm form, HttpServletRequest request) throws Exception {
		request.setAttribute("defaultCatalog", dataBackupService.getDefaultCatalog());
		return Constants.SYSTEM + "data_table_revert" ;
	}
	
	/**
	 * 获取所有数据库
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/databases.do")
	@ResponseBody
	public DataGrid databases() throws Exception {
		List<Map<String, String>> databases = dataBackupService.listDataBases();
		DataGrid dg = new DataGrid() ;
		dg.setRows(databases) ;
		dg.setTotal(((Long)(long)databases.size()).longValue());
		return dg ;
	}
	
	/**
	 * 获取所有备份文件
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/backupFiles.do")
	@ResponseBody
	public DataGrid backupFiles() throws Exception {
		List<Map<String, String>> backFiles = new ArrayList<Map<String, String>>();
		String backpath = this.realPathResolver.get(Constants.BACKUP_PATH);
		File backDirectory = new File(backpath) ;
		if(backDirectory.exists()) {
			File[] listFiles = backDirectory.listFiles() ;
			for (File file : listFiles) {
				Map<String, String> fileMap = new HashMap<String, String>() ;
				FileInputStream fis = new FileInputStream(file) ;
				fileMap.put("fileName", file.getName()) ;
				fileMap.put("filePath", file.getAbsolutePath()) ;
				fileMap.put("fileSize", (fis.available()/1024)+" Kb") ;
				fis.close() ;
				
				backFiles.add(fileMap) ;
			}
		}
		
		DataGrid dg = new DataGrid() ;
		dg.setRows(backFiles) ;
		dg.setTotal(((Long)(long)backFiles.size()).longValue());
		
		return dg ;
	}

	/**
	 * 查询所有表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/datagrid.do")
	@ResponseBody
	public DataGrid table_datagrid() throws Exception {
		List<Map<String, String>> tables = dataBackupService.listTables(dataBackupService.getDefaultCatalog()) ;
		DataGrid dg = new DataGrid() ;
		dg.setRows(tables) ;
		dg.setTotal(((Long)(long)tables.size()).longValue());
		return dg ;
	}
	
	/**
	 * 获取表字段
	 * @param form
	 * @return
	 */
	@RequestMapping("/listfields.do")
	@ResponseBody
	public DataGrid listfields(DataForm form) {
		List<BaseField> listFields = dataBackupService.listFields(form.getTableName()) ;
		
		DataGrid dg = new DataGrid() ;
		dg.setRows(listFields) ;
		dg.setTotal(((Long)(long)listFields.size()).longValue());
		return dg ;
	}
	
	/**
	 * 备份
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/backup.do")
	public String backup(DataForm form) throws Exception {
		if(null != form.getTableNames() && !form.getTableNames().equalsIgnoreCase("")) {
			String[] tableNames = form.getTableNames().split(",") ;
			
			String backpath = this.realPathResolver.get(Constants.BACKUP_PATH);
			File backDirectory = new File(backpath);
			if (!backDirectory.exists()) {
				backDirectory.mkdir();
			}
			String backFilePath = backpath + SLASH+ DateUtil.formatI(new Date()) + "." + SUFFIX;
			File file=new File(backFilePath);
			Thread thread =new DateBackupTableThread(file,tableNames);
			thread.start();
		}
		return Constants.SYSTEM + "data_backup_progress" ;
	}
	
	/**
	 * 恢复
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/revert.do")
	@ResponseBody
	public Json revert(DataForm form) throws Exception {
		Json json = new Json() ;
		
		try {
			String backpath = this.realPathResolver.get(Constants.BACKUP_PATH);
			String backFilePath = backpath + SLASH + form.getBackupFileName();
			String sql=readFile(backFilePath);
			//还原暂时没做备份提示。
			this.dataBackupService.executeSQL("use "+SPLIT+form.getDatabase()+SPLIT+BR);
			this.dataBackupService.executeSQL(sql);
			
			json.setStatus(true) ;
		} catch (Exception e) {
			throw e ;
		}
		return json ;
	}
	
	/**
	 * 删除备份文件
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/backup_del.do")
	@ResponseBody
	public Json backup_del(DataForm form) throws Exception {
		Json json = new Json() ;
		if(null != form.getBackupFileName() && !"".equals(form.getBackupFileName())) {
			String[] filenames = form.getBackupFileName().split(",") ;
			String backpath = this.realPathResolver.get(Constants.BACKUP_PATH);
			for (String filename : filenames) {
				try {
					File file = new File(backpath + SLASH + filename) ;
					if(file.exists()) {
						file.delete() ;
					}
					json.setStatus(true) ;
				} catch (Exception e) {
					throw e ;
				}
			}
		}
		return json ;
	}
	
	/**
	 * 下载数据备份文件
	 * @param fileName
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/downBackUpFile.do")
	public void downBackUpFile(DataForm form ,HttpServletResponse response) throws Exception {
		try {
			File[] srcfiles = zipFiles(form) ;
			if(null != srcfiles && srcfiles.length > 0) {
				String zipName = DateUtil.formatI(new Date()) + Constants.FILE_SUFFIX_ZIP ;
				String zipPath = System.getProperty("java.io.tmpdir") + SLASH +zipName ;
				//ZIP压缩
				boolean flag = ZipUtils.zipFiles(srcfiles, new File(zipPath)) ;
				
				if(flag) {
					FileUtil.downFile(zipName, zipPath, response);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/backup_progress.do")
	@ResponseBody
	public JSONObject getBackupProgress(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		JSONObject json=new JSONObject();
		json.put("tablename", backup_table);
		return json ;
	}
	
	private File[] zipFiles(DataForm form) {
		File[] files = null ;
		if(null != form.getBackupFileName() && !"".equals(form.getBackupFileName())) {
			String[] fileNames = form.getBackupFileName().split(",") ;
			files = new File[fileNames.length] ;
			
			String backpath = this.realPathResolver.get(Constants.BACKUP_PATH);
			String backFilePath = backpath + SLASH + form.getBackupFileName();
			for (int i=0; i<fileNames.length; i++) {
				File file = new File(backFilePath) ;
				files[i] = file ;
			}
		}
		return files ;
	}
	
	@SuppressWarnings("resource")
	private  String readFile(String filename) throws IOException {
	    File file =new File(filename);
	    if(filename==null || filename.equals(""))
	    {
	      throw new NullPointerException("<@s.m 'db.fileerror'/>");
	    }
	    long len = file.length();
	    byte[] bytes = new byte[(int)len];
	    BufferedInputStream bufferedInputStream=new BufferedInputStream(new FileInputStream(file));
	    int r = bufferedInputStream.read( bytes );
	    if (r != len)
	      throw new IOException("文件读取错误!");
	    bufferedInputStream.close();
	    return new String(bytes,"utf-8");
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
					backup_table=tablenames[i];
					backupTable(writer,tablenames[i]);
				}
				writer.write(Constants.ONESQL_PREFIX + ABLEFOREIGN);
				backup_table="";
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


