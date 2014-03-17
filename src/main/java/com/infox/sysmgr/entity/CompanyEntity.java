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
 * 公司表（有上下级之分）
 * 创建者： 杨浩泉
 * 创建时间： 2014-3-17 下午9:21:03
 * 版本号： v1.0
 */
@Entity
@Table(name = "INFOX_SYSMGR_COMPANY")
@DynamicUpdate(true)
@DynamicInsert(true)
public class CompanyEntity {

	private String id ;
	
	private String name ;
	
	private Date created = new Date() ;

	private Set<CompanyEntity> companys = new HashSet<CompanyEntity>(0) ;
	
	private CompanyEntity company ;
	
	@OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
	@OrderBy("created desc")
	public Set<CompanyEntity> getCompanys() {
		return companys;
	}

	public void setCompanys(Set<CompanyEntity> companys) {
		this.companys = companys;
	}

	@ManyToOne
	@JoinColumn(name = "COMPANY_PID")
	public CompanyEntity getCompany() {
		return company;
	}

	public void setCompany(CompanyEntity company) {
		this.company = company;
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
