package com.infox.sysmgr.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infox.common.dao.BaseDaoI;
import com.infox.common.web.page.DataGrid;
import com.infox.common.web.page.Json;
import com.infox.sysmgr.entity.CompanyEntity;
import com.infox.sysmgr.entity.RoleEntity;
import com.infox.sysmgr.entity.UserDetailEntity;
import com.infox.sysmgr.entity.UserEntity;
import com.infox.sysmgr.service.UserServiceI;
import com.infox.sysmgr.web.form.UserForm;

@Service
@Transactional
public class UserServiceImpl implements UserServiceI {

	@Autowired
	private BaseDaoI<UserEntity> basedaoUser;
	
	@Autowired
	private BaseDaoI<CompanyEntity> basedaoCompany;
	
	@Autowired
	private BaseDaoI<RoleEntity> basedaoRole;
	

	@Override
	public Json add(UserForm form) {
		Json j = new Json();
		try {
			UserEntity entity = new UserEntity();
			BeanUtils.copyProperties(form, entity, new String[] { "id" });

			if (form.getDept_id() != null && !"".equalsIgnoreCase(form.getDept_id())) {
				entity.setDept(this.basedaoCompany.get(CompanyEntity.class, form.getDept_id()));
			}
			
			
			UserDetailEntity ude = new UserDetailEntity() ;
			BeanUtils.copyProperties(form, ude, new String[] { "id" });
			entity.setUser_detail(ude) ;
			
			this.basedaoUser.save(entity);
			
			j.setMsg("创建成功！") ;
			j.setStatus(true) ;
		} catch (BeansException e) {
			j.setMsg("创建失败！") ;
		}
		return j;
	}

	@Override
	public Json delete(UserForm form) {
		Json j = new Json();
		try {
			if(null != form.getIds() && !"".equals(form.getIds())) {
				String[] ids = form.getIds().split(",") ;
				for (String id : ids) {
					UserEntity o = this.basedaoUser.get(UserEntity.class, id);
					this.basedaoUser.delete(o) ;
				}
				j.setMsg("删除成功！") ;
				j.setStatus(true) ;
			}
		} catch (BeansException e) {
			j.setMsg("删除失败！") ;
		}
		return j;
	}
	

	@Override
	public Json edit(UserForm form) {
		Json j = new Json();
		try {
			UserEntity entity = this.basedaoUser.get(UserEntity.class, form.getId());
			
			if(entity != null) {
				BeanUtils.copyProperties(form, entity ,new String[]{"created"});
				
				UserDetailEntity user_detail = new UserDetailEntity() ;
				BeanUtils.copyProperties(form, user_detail, new String[] { "id" });
				
				entity.setUser_detail(user_detail) ;
				
				if (form.getDept_id() != null && !form.getDept_id().equalsIgnoreCase("")) {// 说明前台选中了上级资源
					entity.setDept(this.basedaoCompany.get(CompanyEntity.class, form.getDept_id()));
				} else {
					entity.setDept(null);// 前台没有选中上级资源，所以就置空
				}
				this.basedaoUser.update(entity) ;
			}
			
			j.setMsg("编辑成功！") ;
			j.setStatus(true) ;
		} catch (BeansException e) {
			j.setMsg("编辑失败！") ;
		}
		return j;
	}
	

	@Override
	public UserForm get(String id) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		UserEntity entity = this.basedaoUser.get("select t from UserEntity t where t.id = :id", params);
		
		UserForm form = new UserForm();
		
		if(null != entity) {
			BeanUtils.copyProperties(entity, form);
			if(null != entity.getUser_detail()) {
				BeanUtils.copyProperties(entity.getUser_detail(), form, new String[] { "id" });
			}
			CompanyEntity dept = entity.getDept() ;
			if (entity.getDept() != null) {
				form.setDept_id(dept.getId()) ;
				form.setDept_name(dept.getName()) ;
			}
		}
		return form ;
	}
	
	@Override
	public UserForm get(UserForm form) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "select t from UserEntity t where 1=1";
		hql = addWhere(hql, form, params) + addOrdeby(form);
		
		UserEntity entity = this.basedaoUser.get(hql, params) ;
		if(null != entity) {
			UserForm pform = new UserForm();
			BeanUtils.copyProperties(entity, pform);
			
			UserDetailEntity user_detail = entity.getUser_detail() ;
			if(null != user_detail) {
				BeanUtils.copyProperties(user_detail, pform, new String[] { "id" });
			}
			
			CompanyEntity dept = entity.getDept() ;
			if (dept != null) {
				pform.setDept_id(dept.getId()) ;
				pform.setDept_name(dept.getName()) ;
			}
			
			return pform;
		} else {
			return null ;
		}
	}

	@Override
	public Json set_permit(UserForm form) {
		Json j = new Json();
		if (form.getIds() != null && !"".equalsIgnoreCase(form.getIds())) {
			List<RoleEntity> roles = new ArrayList<RoleEntity>();
			if (form.getRoleIds() != null && form.getRoleIds().length() > 0) {
				for (String roleId : form.getRoleIds().split(",")) {
					roles.add(this.basedaoRole.get(RoleEntity.class, roleId));
				}
			}
			for (String id : form.getIds().split(",")) {
				if (id != null && !id.equalsIgnoreCase("")) {
					UserEntity t = this.basedaoUser.get(UserEntity.class, id);
					t.setRoles(new HashSet<RoleEntity>(roles));
				}
			}
		}
		
		return j;
	}
	
	@Override
	public DataGrid datagrid(UserForm form) {
		
		List<UserForm> forms = new ArrayList<UserForm>();
		List<UserEntity> users = this.find(form) ;
		if (null != users && users.size() > 0) {
			for (UserEntity i : users) {
				UserForm uf = new UserForm();
				BeanUtils.copyProperties(i, uf);
				if(null != i.getUser_detail()) {
					BeanUtils.copyProperties(i.getUser_detail(), uf, new String[] { "id" });
				}
				forms.add(uf);
			}
		}
		
		
		DataGrid datagrid = new DataGrid();
		datagrid.setTotal(this.total(form));
		datagrid.setRows(forms);
		return datagrid;
	}


	private List<UserEntity> find(UserForm form) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "select t from UserEntity t where 1=1";
		hql = addWhere(hql, form, params) + addOrdeby(form);
		return this.basedaoUser.find(hql, params, form.getPage(), form.getRows());
	}

	private String addOrdeby(UserForm form) {
		String orderString = "";
		if (form.getSort() != null && form.getOrder() != null) {
			orderString = " order by " + form.getSort() + " " + form.getOrder();
		}
		return orderString;
	}

	public Long total(UserForm form) {
		Map<String, Object> params = new HashMap<String, Object>();

		String hql = "select count(*) from UserEntity t where 1=1";

		hql = addWhere(hql, form, params);

		return this.basedaoUser.count(hql, params);
	}

	private String addWhere(String hql, UserForm form, Map<String, Object> params) {
		if (null != form) {
			/*if (form.getTruename() != null && !"".equals(form.getTruename())) {
				hql += " and t.name like :name";
				params.put("name", "%%" + form.getTruename() + "%%");
			}
			if (form.getAccount() != null && !"".equals(form.getAccount())) {
				hql += " and t.account like :account";
				params.put("account", "%%" + form.getAccount() + "%%");
			}
			if (form.getSex() != null && !"".equals(form.getSex())) {
				hql += " and t.sex like :sex";
				params.put("sex", "%%" + form.getSex() + "%%");
			}
			if (form.getTel() != null && !"".equals(form.getTel())) {
				hql += " and t.tel like :tel";
				params.put("tel", "%%" + form.getTel() + "%%");
			}
			if (form.getEmail() != null && !"".equals(form.getEmail())) {
				hql += " and t.email like :email";
				params.put("email", "%%" + form.getEmail() + "%%");
			}
			if (form.getOrgname() != null && !"".equals(form.getOrgname())) {
				hql += " and t.orgname like :orgname";
				params.put("orgname", "%%" + form.getOrgname() + "%%");
			}
			if (form.getOnlineState() != null && !"".equals(form.getOrgname())) {
				hql += " and t.onlineState=:onlineState";
				params.put("onlineState", form.getOnlineState());
			}*/
		}
		return hql;
	}

	@Override
	public UserForm getPermission(UserForm form) {
		UserForm r = new UserForm();

		UserEntity t = this.basedaoUser.get("select distinct t from UserEntity t left join fetch t.roles role where t.id = '" + form.getId() + "'");

		if (t != null) {
			BeanUtils.copyProperties(t, r);
			Set<RoleEntity> s = t.getRoles();
			if (s != null && !s.isEmpty()) {
				boolean b = false;
				String ids = "";
				String names = "";
				for (RoleEntity tr : s) {
					if (b) {
						ids += ",";
						names += ",";
					} else {
						b = true;
					}
					ids += tr.getId();
					names += tr.getName();
				}

				r.setRoleIds(ids);
				r.setRoleNames(names);
			}
		}
		return r;
	}

}
