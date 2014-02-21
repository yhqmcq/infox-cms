package com.infox.common.web.page;

import java.util.List;

/**
 * easyui的datagrid模型
 * @author sugar
 * 
 */
public class DataGrid implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;

	/** 总记录数 */
	private Long total;
	
	/** 每行记录 */
	private List<?> rows;
	
	private List<?> footer;
	
	public List<?> data ;

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

	public List<?> getFooter() {
		return footer;
	}

	public void setFooter(List<?> footer) {
		this.footer = footer;
	}
}
