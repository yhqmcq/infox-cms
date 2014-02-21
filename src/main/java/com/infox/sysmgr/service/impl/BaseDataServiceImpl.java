package com.infox.sysmgr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.infox.common.dao.JdbcDaoSupportUtil;
import com.infox.sysmgr.entity.base.BaseConstraints;
import com.infox.sysmgr.entity.base.BaseField;
import com.infox.sysmgr.entity.base.BaseTable;
import com.infox.sysmgr.service.BaseDataService;

@Repository
public class BaseDataServiceImpl implements BaseDataService {
	
	@Autowired
	private JdbcDaoSupportUtil jdsu ;
	public JdbcTemplate getJdbcTemplate() {
		return jdsu.getJdbcTemplate();
	}
	
	private String db;

	public List<BaseTable> listTables() {
		String sql = "select table_name,table_comment,engine,table_rows,auto_increment from tables where table_schema='"
				+ db + "'";
		List<BaseTable> tables = new ArrayList<BaseTable>();
		SqlRowSet set = getJdbcTemplate().queryForRowSet(sql);
		while (set.next()) {
			BaseTable table = new BaseTable();
			table.setName(set.getString(1));
			table.setComment(set.getString(2).split(";")[0]);
			table.setEngine(set.getString(3));
			table.setRows(set.getInt(4));
			table.setAuto_increment(set.getInt(5));
			tables.add(table);
		}
		return tables;
	}

	public BaseTable findTable(String tablename) {
		String sql = "select table_name,table_comment,engine,table_rows,auto_increment from tables where table_schema='"
				+ db + "' and table_name='" + tablename + "'";
		List<BaseTable> tables = new ArrayList<BaseTable>();
		SqlRowSet set = getJdbcTemplate().queryForRowSet(sql);
		while (set.next()) {
			BaseTable table = new BaseTable();
			table.setName(set.getString(1));
			table.setComment(set.getString(2).split(";")[0]);
			table.setEngine(set.getString(3));
			table.setRows(set.getInt(4));
			table.setAuto_increment(set.getInt(5));
			tables.add(table);
		}
		if (tables.size() > 0) {
			return tables.get(0);
		}else{
			return null;
		}
	}
	

	public List<BaseField> listFields(String tablename) {
		String sql = "select column_name,column_type,column_default,column_key,column_comment,is_nullable,extra from columns where table_schema='"
				+ db + "' and table_name='" + tablename + "'";
		List<BaseField> fields = new ArrayList<BaseField>();
		SqlRowSet set = getJdbcTemplate().queryForRowSet(sql);
		while (set.next()) {
			BaseField field = new BaseField();
			field.setName(set.getString(1));
			field.setFieldType(set.getString(2));
			field.setFieldDefault(set.getString(3));
			field.setFieldProperty(set.getString(4));
			field.setComment(set.getString(5));
			field.setNullable(set.getString(6));
			field.setExtra(set.getString(7));
			fields.add(field);
		}
		return fields;
	}

	public List<BaseConstraints> listConstraints(String tablename) {
		String sql = "select constraint_name,table_name,column_name,referenced_table_name,referenced_column_name from information_schema.KEY_COLUMN_USAGE where constraint_schema='"
				+ db + "' and table_name='" + tablename + "'";
		List<BaseConstraints> constraints = new ArrayList<BaseConstraints>();
		SqlRowSet set = getJdbcTemplate().queryForRowSet(sql);
		while (set.next()) {
			BaseConstraints constraint = new BaseConstraints();
			constraint.setName(set.getString(1));
			constraint.setTableName(set.getString(2));
			constraint.setColumnName(set.getString(3));
			constraint.setReferencedTableName(set.getString(4));
			constraint.setReferencedColumnName(set.getString(5));
			constraints.add(constraint);
		}
		return constraints;
	}

	public String getDb() {
		return db;
	}

	public void setDb(String db) {
		this.db = db;
	}

}