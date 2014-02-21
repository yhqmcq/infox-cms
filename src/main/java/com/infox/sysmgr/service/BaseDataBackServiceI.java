package com.infox.sysmgr.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.infox.sysmgr.entity.base.BaseField;

public interface BaseDataBackServiceI {

	public List<Map<String, String>> listTables(String catalog);

	public List<BaseField> listFields(String tablename);

	public List<Map<String, String>> listDataBases();

	public String createTableDDL(String tablename);
	
	public String getDefaultCatalog()throws SQLException;
	
	public void setDefaultCatalog(String catalog) throws SQLException;

	public List<Object[]> createTableData(String tablename);
	
	public Boolean executeSQL(String sql);

}