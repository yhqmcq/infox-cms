package com.infox.sysmgr.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infox.common.dao.BaseDaoI;
import com.infox.common.web.page.DataGrid;
import com.infox.sysmgr.entity.MenuEntity;
import com.infox.sysmgr.entity.Role1Entity;
import com.infox.sysmgr.service.Role1ServiceI;
import com.infox.sysmgr.web.form.Role1Form;

@Service
@Transactional
public class RoleServiceImpl implements Role1ServiceI {

	@Autowired
	private BaseDaoI<Role1Entity> basedaoRole;
	@Autowired
	private BaseDaoI<MenuEntity> basedaoMenu;

	@Override
	public void add(Role1Form form) throws Exception {
		Role1Entity entity = new Role1Entity();

		BeanUtils.copyProperties(form, entity);
		
		if (form.getPid() != null && !form.getPid().equalsIgnoreCase("")) {
			entity.setRole(this.basedaoRole.get(Role1Entity.class, form.getPid()));
		}
		this.basedaoRole.save(entity);
	}

	@Override
	public void delete(String id) throws Exception {
		Role1Entity t = basedaoRole.get(Role1Entity.class, id);
		del(t);
	}

	private void del(Role1Entity entity) {
		if (entity.getRoles() != null && entity.getRoles().size() > 0) {
			for (Role1Entity r : entity.getRoles()) {
				del(r);
			}
		}
		basedaoRole.delete(entity);
	}

	@Override
	public void edit(Role1Form form) throws Exception {

		Role1Entity entity = basedaoRole.get(Role1Entity.class, form.getId());
		if (entity != null) {
			BeanUtils.copyProperties(form, entity ,new String[]{"creater"});

			if (form.getPid() != null && form.getId().equalsIgnoreCase(form.getPid())) {
				throw new Exception("角色不可自关联！");
			}

			if (form.getPid() != null && !form.getPid().equalsIgnoreCase("")) {
				entity.setRole(basedaoRole.get(Role1Entity.class, form.getPid()));
			}
			if (form.getPid() != null && !form.getPid().equalsIgnoreCase("")) {// 说明前台选中了上级资源
				Role1Entity pt = basedaoRole.get(Role1Entity.class, form.getPid());
				isChildren(entity, pt);// 说明要将当前资源修改到当前资源的子/孙子资源下
				entity.setRole(pt);
			} else {
				entity.setRole(null);// 前台没有选中上级资源，所以就置空
			}
		}
	}

	private boolean isChildren(Role1Entity t, Role1Entity pt) {
		if (pt != null && pt.getRole() != null) {
			if (pt.getRole().getId().equalsIgnoreCase(t.getId())) {
				pt.setRole(null);
				return true;
			} else {
				return isChildren(t, pt.getRole());
			}
		}
		return false;
	}

	@Override
	public Role1Form get(String id) throws Exception {
		Role1Form r = new Role1Form();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		Role1Entity t = basedaoRole.get("select distinct t from Role1Entity t left join fetch t.menus menu where t.id = :id", params);
		if (t != null) {
			BeanUtils.copyProperties(t, r);
			if (t.getRole() != null) {
				r.setPid(t.getRole().getId());
				r.setPname(t.getRole().getName());
			}
			Set<MenuEntity> s = t.getMenus();
			if (s != null && !s.isEmpty()) {
				boolean b = false;
				String ids = "";
				String names = "";
				for (MenuEntity tr : s) {
					if (b) {
						ids += ",";
						names += ",";
					} else {
						b = true;
					}
					ids += tr.getId();
					names += tr.getName();
				}
				r.setMenuIds(ids);
				r.setMenuNames(names);
			}
		}
		return r;
	}

	@Override
	public DataGrid role_datagrid(Role1Form form) throws Exception {
		DataGrid datagrid = new DataGrid();
		datagrid.setRows(this.changeModel(this.find(form)));
		datagrid.setTotal(this.total(form));
		return datagrid;
	}

	private List<Role1Form> changeModel(List<Role1Entity> entity) {
		List<Role1Form> roleforms = new ArrayList<Role1Form>();

		if (null != entity && entity.size() > 0) {
			for (Role1Entity i : entity) {
				Role1Form uf = new Role1Form();
				BeanUtils.copyProperties(i, uf);
				roleforms.add(uf);
			}
		}
		return roleforms;
	}

	private List<Role1Entity> find(Role1Form form) {
		Map<String, Object> params = new HashMap<String, Object>();

		String hql = "select t from Role1Entity t where 1=1";
		hql = addWhere(hql, form, params) + addOrdeby(form);
		return this.basedaoRole.find(hql, params, form.getPage(), form.getRows());
	}

	private String addOrdeby(Role1Form form) {
		String orderString = "";
		if (form.getSort() != null && form.getOrder() != null) {
			orderString = " order by " + form.getSort() + " " + form.getOrder();
		}
		return orderString;
	}

	public Long total(Role1Form form) {
		Map<String, Object> params = new HashMap<String, Object>();

		String hql = "select count(*) from Role1Entity t where 1=1";

		hql = addWhere(hql, form, params);

		return this.basedaoRole.count(hql, params);
	}

	private String addWhere(String hql, Role1Form form, Map<String, Object> params) {
		if (null != form) {
			if (form.getName() != null && !"".equals(form.getName())) {
				hql += " and t.name like :name";
				params.put("name", "%%" + form.getName() + "%%");
			}
		}
		return hql;
	}
	
	/*
	@Override
	public List<RoleForm> role_treegrid(RoleForm roleform) throws Exception {
		List<RoleForm> rl = new ArrayList<RoleForm>();
		List<Role1Entity> tl = null;

		tl = basedaoRole.find("select distinct t from Role1Entity t left join fetch t.menus menu order by t.seq");

		if (tl != null && tl.size() > 0) {
			for (Role1Entity t : tl) {
				RoleForm r = new RoleForm();
				BeanUtils.copyProperties(t, r);
				r.setIconCls("ext_group");
				if (t.getRole() != null) {
					r.setPid(t.getRole().getId());
					r.setPname(t.getRole().getName());
				}
				Set<MenuEntity> s = t.getMenus();
				if (s != null && !s.isEmpty()) {
					boolean b = false;
					String ids = "";
					String names = "";
					for (MenuEntity tr : s) {
						if (b) {
							ids += ",";
							names += ",";
						} else {
							b = true;
						}
						ids += tr.getId();
						names += tr.getName();
					}
					r.setMenuIds(ids);
					r.setMenuNames(names);
				}
				rl.add(r);
			}
		}
		return rl;
	}
	*/
	
	@Override
	public List<Role1Form> treegrid(Role1Form form ,String mode) throws Exception {
		String hql = "select t from Role1Entity t where t.role is null" ;
		List<Role1Entity> orgs = this.basedaoRole.find(hql) ;
		List<Role1Form> rolesform = new ArrayList<Role1Form>() ;
		for (Role1Entity menuEntity : orgs) {
			rolesform.add(recursiveNode(menuEntity ,mode)) ;
		}
		return rolesform ;
	}
	
	public Role1Form recursiveNode(Role1Entity entity ,String mode) {
		Role1Form mf = new Role1Form() ;
		BeanUtils.copyProperties(entity, mf) ;
		
		mf.setText(entity.getName()) ;
		
		if(null != entity.getRoles() && entity.getRoles().size() > 0) {
			mf.setState("closed") ;
			
			Set<Role1Entity> rs = entity.getRoles() ;
			List<Role1Form> children = new ArrayList<Role1Form>();
			for (Role1Entity menuEntity : rs) {
				
				//combotree方式显示
				if("combotree".equalsIgnoreCase(mode)) {
						Role1Form tn = recursiveNode(menuEntity ,mode) ;
						BeanUtils.copyProperties(menuEntity ,tn) ;
						children.add(tn);
				} else {
					Role1Form tn = recursiveNode(menuEntity ,mode) ;
					BeanUtils.copyProperties(menuEntity ,tn) ;
					children.add(tn);
				}
			}
			
			mf.setChildren(children) ;
			mf.setState("open");
		}
		return mf ;
	}

	@Override
	public void set_grant(Role1Form form) throws Exception {
		
		Role1Entity Role1Entity = this.basedaoRole.get(Role1Entity.class, form.getId()) ;
		
		if(form.getMenuIds() != null && !"".equalsIgnoreCase(form.getIds())) {
			String ids = "";
			boolean b = false;
			for (String id : form.getMenuIds().split(",")) {
				if (b) {
					ids += ",";
				} else {
					b = true;
				}
				ids += "'" + id + "'";
			}
			Role1Entity.setMenus(new HashSet<MenuEntity>(this.basedaoMenu.find("select distinct t from MenuEntity t where t.id in (" + ids + ")"))) ;
		} else {
			Role1Entity.setMenus(null) ;
		}
		
	}

	@Override
	public Role1Form getPermission(Role1Form form) throws Exception {
		
		Role1Form r = new Role1Form() ;
		
		Role1Entity t = this.basedaoRole.get("select distinct t from Role1Entity t left join fetch t.menus menu where t.id = '" + form.getId()+"'") ;
		
		if (t != null) {
			BeanUtils.copyProperties(t, r);
			Set<MenuEntity> s = t.getMenus();
			if (s != null && !s.isEmpty()) {
				boolean b = false;
				String ids = "";
				String names = "";
				for (MenuEntity tr : s) {
					if (b) {
						ids += ",";
						names += ",";
					} else {
						b = true;
					}
					ids += tr.getId();
					names += tr.getName();
				}
				
				r.setMenuIds(ids) ;
				r.setMenuNames(names);
			}
		}
		return r ;
	}

}
