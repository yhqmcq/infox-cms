package com.infox.sysmgr.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.infox.common.util.RandomUtils;

/**
 * 员工信息基本表
 * 创建者： 杨浩泉
 * 创建时间： 2014-3-17 下午9:20:27
 * 版本号： v1.0
 */
@Entity
@Table(name = "INFOX_SYSMGR_EMP")
@DynamicUpdate(true)
@DynamicInsert(true)
public class EmpEntity {

	private String id ;
	
	private String name ;
	
	private Date created = new Date() ;
	
	private EmpDetailEntity emp_detail ;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="DETAIL_ID")
	public EmpDetailEntity getEmp_detail() {
		return emp_detail;
	}

	public void setEmp_detail(EmpDetailEntity emp_detail) {
		this.emp_detail = emp_detail;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@Id
	public String getId() {
		if (this.id != null) {
			return this.id;
		}
		return RandomUtils.generateNumber(6);
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
	
}
