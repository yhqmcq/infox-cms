package com.infox.common.web.page;

/**
 * @程序编写者：杨浩泉
 * @日期：2013-1-15
 * @类说明：分页数据模型
 */
public class PageHelper {

	private String ids ;//ID集合（删除）
	
	private int page;// 当前页
	
	private int rows;// 每页显示记录数
	
	private String sort;// 排序字段名
	
	private String order;// 按什么排序(asc,desc)

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	
	
	
}
