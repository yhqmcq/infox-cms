package com.infox.sysmgr.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.infox.common.util.RandomUtils;

/**
 * 员工信息详细表
 * 创建者： 杨浩泉
 * 创建时间： 2014-3-17 下午9:22:03
 * 版本号： v1.0
 */
@Entity
@Table(name = "INFOX_SYSMGR_EMP_DETAIL")
@DynamicUpdate(true)
@DynamicInsert(true)
public class EmpDetailEntity {

	private String id ;
	
	private String name ;

	private EmpEntity emp ;
	
	@OneToOne(mappedBy="emp_detail")
	public EmpEntity getEmp() {
		return emp;
	}

	public void setEmp(EmpEntity emp) {
		this.emp = emp;
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
