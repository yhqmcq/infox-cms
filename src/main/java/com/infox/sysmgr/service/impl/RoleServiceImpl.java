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
import com.infox.common.util.RandomUtils;
import com.infox.common.web.page.DataGrid;
import com.infox.sysmgr.entity.MenuEntity;
import com.infox.sysmgr.entity.RoleEntity;
import com.infox.sysmgr.service.RoleServiceI;
import com.infox.sysmgr.web.form.RoleForm;

@Service
@Transactional
public class RoleServiceImpl implements RoleServiceI {

	@Autowired
	private BaseDaoI<RoleEntity> basedaoRole;
	@Autowired
	private BaseDaoI<MenuEntity> basedaoMenu;

	@Override
	public void add(RoleForm form) throws Exception {
		RoleEntity entity = new RoleEntity();

		BeanUtils.copyProperties(form, entity);
		entity.setId(RandomUtils.generateNumber(6)) ;
		
		if (form.getPid() != null && !form.getPid().equalsIgnoreCase("")) {
			entity.setRole(this.basedaoRole.get(RoleEntity.class, form.getPid()));
		}
		this.basedaoRole.save(entity);
	}

	@Override
	public void delete(String id) throws Exception {
		RoleEntity t = basedaoRole.get(RoleEntity.class, id);
		del(t);
	}

	private void del(RoleEntity entity) {
		if (entity.getRoles() != null && entity.getRoles().size() > 0) {
			for (RoleEntity r : entity.getRoles()) {
				del(r);
			}
		}
		basedaoRole.delete(entity);
	}

	@Override
	public void edit(RoleForm form) throws Exception {

		RoleEntity entity = basedaoRole.get(RoleEntity.class, form.getId());
		if (entity != null) {
			BeanUtils.copyProperties(form, entity ,new String[]{"creater"});

			if (form.getPid() != null && form.getId().equalsIgnoreCase(form.getPid())) {
				throw new Exception("角色不可自关联！");
			}

			if (form.getPid() != null && !form.getPid().equalsIgnoreCase("")) {
				entity.setRole(basedaoRole.get(RoleEntity.class, form.getPid()));
			}
			if (form.getPid() != null && !form.getPid().equalsIgnoreCase("")) {// 说明前台选中了上级资源
				RoleEntity pt = basedaoRole.get(RoleEntity.class, form.getPid());
				isChildren(entity, pt);// 说明要将当前资源修改到当前资源的子/孙子资源下
				entity.setRole(pt);
			} else {
				entity.setRole(null);// 前台没有选中上级资源，所以就置空
			}
		}
	}

	private boolean isChildren(RoleEntity t, RoleEntity pt) {
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
	public RoleForm get(String id) throws Exception {
		RoleForm r = new RoleForm();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		RoleEntity t = basedaoRole.get("select distinct t from RoleEntity t left join fetch t.menus menu where t.id = :id", params);
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
	public DataGrid role_datagrid(RoleForm form) throws Exception {
		DataGrid datagrid = new DataGrid();
		datagrid.setRows(this.changeModel(this.find(form)));
		datagrid.setTotal(this.total(form));
		return datagrid;
	}

	private List<RoleForm> changeModel(List<RoleEntity> entity) {
		List<RoleForm> roleforms = new ArrayList<RoleForm>();

		if (null != entity && entity.size() > 0) {
			for (RoleEntity i : entity) {
				RoleForm uf = new RoleForm();
				BeanUtils.copyProperties(i, uf);
				roleforms.add(uf);
			}
		}
		return roleforms;
	}

	private List<RoleEntity> find(RoleForm form) {
		Map<String, Object> params = new HashMap<String, Object>();

		String hql = "select t from RoleEntity t where 1=1";
		hql = addWhere(hql, form, params) + addOrdeby(form);
		return this.basedaoRole.find(hql, params, form.getPage(), form.getRows());
	}

	private String addOrdeby(RoleForm form) {
		String orderString = "";
		if (form.getSort() != null && form.getOrder() != null) {
			orderString = " order by " + form.getSort() + " " + form.getOrder();
		}
		return orderString;
	}

	public Long total(RoleForm form) {
		Map<String, Object> params = new HashMap<String, Object>();

		String hql = "select count(*) from RoleEntity t where 1=1";

		hql = addWhere(hql, form, params);

		return this.basedaoRole.count(hql, params);
	}

	private String addWhere(String hql, RoleForm form, Map<String, Object> params) {
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
		List<RoleEntity> tl = null;

		tl = basedaoRole.find("select distinct t from RoleEntity t left join fetch t.menus menu order by t.seq");

		if (tl != null && tl.size() > 0) {
			for (RoleEntity t : tl) {
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
	public List<RoleForm> treegrid(RoleForm form ,String mode) throws Exception {
		String hql = "select t from RoleEntity t where t.role is null" ;
		List<RoleEntity> orgs = this.basedaoRole.find(hql) ;
		List<RoleForm> rolesform = new ArrayList<RoleForm>() ;
		for (RoleEntity menuEntity : orgs) {
			rolesform.add(recursiveNode(menuEntity ,mode)) ;
		}
		return rolesform ;
	}
	
	public RoleForm recursiveNode(RoleEntity entity ,String mode) {
		RoleForm mf = new RoleForm() ;
		BeanUtils.copyProperties(entity, mf) ;
		
		mf.setText(entity.getName()) ;
		
		if(null != entity.getRoles() && entity.getRoles().size() > 0) {
			mf.setState("closed") ;
			
			Set<RoleEntity> rs = entity.getRoles() ;
			List<RoleForm> children = new ArrayList<RoleForm>();
			for (RoleEntity menuEntity : rs) {
				
				//combotree方式显示
				if("combotree".equalsIgnoreCase(mode)) {
						RoleForm tn = recursiveNode(menuEntity ,mode) ;
						BeanUtils.copyProperties(menuEntity ,tn) ;
						children.add(tn);
				} else {
					RoleForm tn = recursiveNode(menuEntity ,mode) ;
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
	public void set_grant(RoleForm form) throws Exception {
		
		RoleEntity roleEntity = this.basedaoRole.get(RoleEntity.class, form.getId()) ;
		
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
			roleEntity.setMenus(new HashSet<MenuEntity>(this.basedaoMenu.find("select distinct t from MenuEntity t where t.id in (" + ids + ")"))) ;
		} else {
			roleEntity.setMenus(null) ;
		}
		
	}

	@Override
	public RoleForm getPermission(RoleForm form) throws Exception {
		
		RoleForm r = new RoleForm() ;
		
		RoleEntity t = this.basedaoRole.get("select distinct t from RoleEntity t left join fetch t.menus menu where t.id = '" + form.getId()+"'") ;
		
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
