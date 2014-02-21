package com.infox.sysmgr.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "INFOX_SYSMGR_MENU")
@DynamicUpdate(true)
@DynamicInsert(true)
public class MenuEntity implements Serializable {

	private static final long serialVersionUID = 1L;

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
	
	/** 菜单图标 */
	private String iconCls ;
	
	/** tree{open,closed} */
	private String state ;
	
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
	
	private MenuEntity menu ;
	
	private Set<MenuEntity> menus = new HashSet<MenuEntity>() ;

	@ManyToOne
	@JoinColumn(name = "MENU_PID")
	public MenuEntity getMenu() {
		return menu;
	}

	public void setMenu(MenuEntity menu) {
		this.menu = menu;
	}

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "MENU_PID")
	@OrderBy("seq desc")
	public Set<MenuEntity> getMenus() {
		return menus;
	}

	public void setMenus(Set<MenuEntity> menus) {
		this.menus = menus;
	}

	@Id
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@Temporal(TemporalType.TIMESTAMP)
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

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}
	
}