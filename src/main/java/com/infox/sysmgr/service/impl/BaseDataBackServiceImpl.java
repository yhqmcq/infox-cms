package com.infox.sysmgr.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.infox.common.dao.JdbcDaoSupportUtil;
import com.infox.common.util.Constants;
import com.infox.sysmgr.entity.base.BaseField;
import com.infox.sysmgr.service.BaseDataBackServiceI;

@Repository
public class BaseDataBackServiceImpl implements
		BaseDataBackServiceI {
	
	@Autowired
	private JdbcDaoSupportUtil jdsu ;
	public JdbcTemplate getJdbcTemplate() {
		return jdsu.getJdbcTemplate();
	}

	public String createTableDDL(String tablename) {
		String sql = " show create table " + tablename;
		String ddl = getJdbcTemplate().queryForObject(sql,
				new RowMapper<String>() {
					public String mapRow(ResultSet set, int arg1)
							throws SQLException {
						return set.getString(2);
					}
				});
		return ddl;
	}

	public List<Object[]> createTableData(String tablename) {
		int filedNum = getTableFieldNums(tablename);
		List<Object[]> results = new ArrayList<Object[]>();
		String sql = " select * from   " + tablename;
		SqlRowSet set = getJdbcTemplate().queryForRowSet(sql);
		while (set.next()) {
			Object[] oneResult = new Object[filedNum];
			for (int i = 1; i <= filedNum; i++) {
				oneResult[i - 1] = set.getObject(i);
			}
			results.add(oneResult);
		}
		return results;
	}

	public List<BaseField> listFields(String tablename) {
		String sql = " desc  " + tablename;
		List<BaseField> fields = new ArrayList<BaseField>();
		SqlRowSet set = getJdbcTemplate().queryForRowSet(sql);
		while (set.next()) {
			BaseField field = new BaseField();
			field.setName(set.getString(1));
			field.setFieldType(set.getString(2));
			field.setNullable(set.getString(3));
			field.setFieldProperty(set.getString(4));
			field.setFieldDefault(set.getString(5));
			field.setExtra(set.getString(6));
			fields.add(field);
		}
		return fields;
	}

	public List<Map<String, String>> listTables(String catalog) {
		String sql = " SELECT TABLE_NAME,ENGINE,TABLE_ROWS,CREATE_TIME,TABLE_COLLATION FROM information_schema.TABLES WHERE TABLE_SCHEMA='" + catalog + "' "; 
		List<Map<String, String>> tables = new ArrayList<Map<String, String>>();
		SqlRowSet set = getJdbcTemplate().queryForRowSet(sql);
		while (set.next()) {
			Map<String, String> field = new HashMap<String, String>() ;
			field.put("table_name", set.getString(1)) ;
			field.put("engine", set.getString(2)) ;
			field.put("table_rows", set.getString(3)) ;
			field.put("create_time", set.getString(4)) ;
			field.put("table_collation", set.getString(5)) ;
			tables.add(field);
		}
		return tables;
	}
	
	public List<Map<String, String>> listDataBases() {
		String sql = " show  databases ";
		List<Map<String, String>> tables = new ArrayList<Map<String, String>>();
		SqlRowSet set = getJdbcTemplate().queryForRowSet(sql);
		while (set.next()) {
			Map<String, String> dbName = new HashMap<String, String>() ;
			dbName.put("database", set.getString(1)) ;
			tables.add(dbName);
		}
		return tables;
	}
	public String getDefaultCatalog() throws SQLException{
		  return getJdbcTemplate().getDataSource().getConnection().getCatalog();
	}
	
	public void setDefaultCatalog(String catalog) throws SQLException{
		   getJdbcTemplate().getDataSource().getConnection().setCatalog(catalog);
	}

	private int getTableFieldNums(String tablename) {
		String sql = " desc  " + tablename;
		SqlRowSet set = getJdbcTemplate().queryForRowSet(sql);
		int rownum = 0;
		while (set.next()) {
			rownum++;
		}
		return rownum;
	}

	public Boolean executeSQL(String sql) {
		try {
			String[]s=sql.split(Constants.ONESQL_PREFIX);
			for(String sqls:s){
				System.out.println(sqls);
				if(StringUtils.isNotBlank(sqls)){
					getJdbcTemplate().execute(sqls.trim());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}