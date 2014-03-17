package com.infox.sysmgr.entity;

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

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.infox.common.util.RandomUtils;

/**
 * 部门表（有上下级之分）
 * 创建者： 杨浩泉
 * 创建时间： 2014-3-17 下午9:21:15
 * 版本号： v1.0
 */
@Entity
@Table(name = "INFOX_SYSMGR_DEPT")
@DynamicUpdate(true)
@DynamicInsert(true)
public class DeptEntity {

	private String id ;
	
	private String name ;
	
	private Date created = new Date() ;

	private Set<DeptEntity> depts = new HashSet<DeptEntity>(0) ;
	
	private DeptEntity dept ;
	
	@OneToMany(mappedBy = "dept", fetch = FetchType.LAZY)
	@OrderBy("created desc")
	public Set<DeptEntity> getDepts() {
		return depts;
	}

	public void setDepts(Set<DeptEntity> depts) {
		this.depts = depts;
	}

	@ManyToOne
	@JoinColumn(name = "DEPT_PID")
	public DeptEntity getDept() {
		return dept;
	}

	public void setDept(DeptEntity dept) {
		this.dept = dept;
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
	
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
}
