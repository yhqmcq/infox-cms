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
@Table(name = "INFOX_SYSMGR_USER_DETAIL")
@DynamicUpdate(true)
@DynamicInsert(true)
public class UserDetailEntity {

	private String id ;
	
	private String truename ;

	private String sex ;
	
	private String email ;
	
	private String tel ;
	
	private UserEntity user ;
	
	@OneToOne(mappedBy="user_detail")
	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
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

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	
}
