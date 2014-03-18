package com.infox.sysmgr.web.form;

import java.util.Date;

import com.infox.common.web.page.EasyuiTree;

/**
 * @author Administrator
 *
 */
public class MenuForm extends EasyuiTree<MenuForm> {
	
	private String id ;
	
	/** 菜单名称 */
	private String name ;
	
	/** 排序 */
	private Integer seq ;
	
	/** 类型（类别、菜单、操作） */
	private String type ;
	
	/** 是否启用 */
	private String disused ;
	
	/** 菜单地址 */
	private String href ;
	
	/** 权限编码 */
	private String code ;
	
	/** 摘要 */
	private String summary ;
	
	/** 创建时间 */
	private Date created = new Date() ;
	
	/** 修改时间 */
	private Date lastmod = new Date() ;
	
	/** 创建者 */
	private String creater ;
	
	/** 修改者 */
	private String modifyer ;
	
	/** 菜单图标 */
	private String iconCls ;
	
	/** tree{open,closed} */
	private String state ;

	private String point ;
	
	/** 父节点 */
	private String pid ;

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public String getDisused() {
		return disused;
	}

	public void setDisused(String disused) {
		this.disused = disused;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getLastmod() {
		return lastmod;
	}

	public void setLastmod(Date lastmod) {
		this.lastmod = lastmod;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getModifyer() {
		return modifyer;
	}

	public void setModifyer(String modifyer) {
		this.modifyer = modifyer;
	}
}
