package com.infox.sysmgr.service;

import java.util.List;

import com.infox.sysmgr.entity.base.BaseConstraints;
import com.infox.sysmgr.entity.base.BaseField;
import com.infox.sysmgr.entity.base.BaseTable;

public interface BaseDataService {
	public List<BaseTable> listTables();

	public List<BaseField> listFields(String tablename);

	public List<BaseConstraints> listConstraints(String tablename);

	public BaseTable findTable(String tablename);

}