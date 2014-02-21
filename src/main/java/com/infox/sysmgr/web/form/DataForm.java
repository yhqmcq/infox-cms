package com.infox.sysmgr.web.form;

import com.infox.common.web.page.PageHelper;

public class DataForm extends PageHelper {
	
	private String database ;
	
	private String tableName ;

	private String tableNames ;

	private String backupFileName ;
	

	public String getBackupFileName() {
		return backupFileName;
	}

	public void setBackupFileName(String backupFileName) {
		this.backupFileName = backupFileName;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableNames() {
		return tableNames;
	}

	public void setTableNames(String tableNames) {
		this.tableNames = tableNames;
	}

	
}
