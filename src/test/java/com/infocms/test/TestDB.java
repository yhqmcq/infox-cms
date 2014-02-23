package com.infocms.test;

import java.sql.SQLException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.druid.pool.DruidDataSource;
import com.infocms.junit.BaseJunitCase;
import com.infox.sysmgr.service.BaseDataBackServiceI;

public class TestDB extends BaseJunitCase {
	
	@Autowired
	private DruidDataSource ds;
	

	@Autowired
	private BaseDataBackServiceI j ; 
	
	@Test
	public void testDB() throws SQLException {
		
		//List<Map<String, String>> listTables = j.listTables("infox");
		
		//this.c.createTableData("") ;
	}
	
	
	
}
