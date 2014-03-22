package com.infox.sysmgr.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infox.common.dao.BaseDaoI;
import com.infox.common.util.BeanUtils;
import com.infox.common.web.page.DataGrid;
import com.infox.common.web.page.Json;
import com.infox.sysmgr.entity.ModuleEntity;
import com.infox.sysmgr.entity.RoleEntity;
import com.infox.sysmgr.service.RoleServiceI;
import com.infox.sysmgr.web.form.RoleForm;

@Service
@Transactional
public class RoleServiceImpl implements RoleServiceI {

	@Autowired
	private BaseDaoI<RoleEntity> basedaoRole;

	@Autowired
	private BaseDaoI<ModuleEntity> basedaoModule;

	@Override
	public Json add(RoleForm form) throws Exception {
		Json j = new Json();
		try {
			RoleEntity entity = new RoleEntity();
			BeanUtils.copyProperties(form, entity, new String[] { "id" });
			this.basedaoRole.save(entity);
			j.setMsg("新建成功！");
			j.setStatus(true);
		} catch (BeansException e) {
			j.setMsg("新建失败！");
		}
		return j;
	}

	@Override
	public Json delete(RoleForm form) throws Exception {
		Json j = new Json();
		try {
			this.basedaoRole.delete(this.basedaoRole.get(RoleEntity.class, form.getId()));
			j.setMsg("删除成功！");
			j.setStatus(true);
		} catch (BeansException e) {
			j.setMsg("删除失败！");
		}
		return j;
	}

	@Override
	public Json edit(RoleForm form) throws Exception {
		Json j = new Json();
		try {
			RoleEntity entity = this.basedaoRole.get(RoleEntity.class, form.getId());
			BeanUtils.copyProperties(form, entity);
			this.basedaoRole.update(entity);
			j.setMsg("编辑成功！");
			j.setStatus(true);
		} catch (BeansException e) {
			j.setMsg("编辑失败！");
		}
		return j;
	}

	@Override
	public RoleForm get(RoleForm form) throws Exception {
		RoleForm r = new RoleForm();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", form.getId());
		RoleEntity t = basedaoRole.get("select distinct t from RoleEntity t left join fetch t.modules module where t.id = :id", params);
		if (t != null) {
			BeanUtils.copyProperties(t, r);
			Set<ModuleEntity> s = t.getModules();
			if (s != null && !s.isEmpty()) {
				boolean b = false;
				String ids = "";
				String names = "";
				for (ModuleEntity tr : s) {
					if (b) {
						ids += ",";
						names += ",";
					} else {
						b = true;
					}
					ids += tr.getId();
					names += tr.getModuleName() ;
				}
				r.setModuleIds(ids);
				r.setModuleNames(names);
			}
		}
		return r;
	}

	@Override
	public DataGrid datagrid(RoleForm form) throws Exception {
		List<RoleForm> roleforms = new ArrayList<RoleForm>();

		List<RoleEntity> entitys = this.find(form);
		if (null != entitys && entitys.size() > 0) {
			for (RoleEntity i : entitys) {
				RoleForm uf = new RoleForm();
				BeanUtils.copyProperties(i, uf);
				roleforms.add(uf);
			}
		}

		DataGrid datagrid = new DataGrid();
		datagrid.setRows(roleforms);
		datagrid.setTotal(this.total(form));
		return datagrid;
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

	@Override
	public List<RoleForm> treegrid(RoleForm form) throws Exception {

		return null;
	}

	@Override
	public Json set_grant(RoleForm form) throws Exception {
		Json j = new Json();
		try {
			RoleEntity role = this.basedaoRole.get(RoleEntity.class, form.getId()) ;
			
			if(form.getModuleIds() != null && !"".equalsIgnoreCase(form.getId())) {
				String ids = "";
				boolean b = false;
				for (String id : form.getModuleIds().split(",")) {
					if (b) {
						ids += ",";
					} else {
						b = true;
					}
					ids += "'" + id + "'";
				}
				role.setModules(new HashSet<ModuleEntity>(this.basedaoModule.find("select distinct t from ModuleEntity t where t.id in (" + ids + ")"))) ;
			} else {
				role.setModules(null) ;
			}
				j.setStatus(true);
				j.setMsg("角色授权成功!");

		} catch (BeansException e) {
			j.setMsg("角色授权失败!");
		}
		return j;
	}

	@Override
	public RoleForm getPermission(RoleForm form) throws Exception {
		RoleForm r = new RoleForm();
		RoleEntity t = this.basedaoRole.get("select distinct t from RoleEntity t left join fetch t.modules module where t.id = '" + form.getId() + "'");
		if (t != null) {
			BeanUtils.copyProperties(t, r);
			Set<ModuleEntity> s = t.getModules() ;
			if (s != null && !s.isEmpty()) {
				boolean b = false;
				String ids = "";
				String names = "";
				for (ModuleEntity tr : s) {
					if (b) {
						ids += ",";
						names += ",";
					} else {
						b = true;
					}
					ids += tr.getId();
					names += tr.getModuleName();
				}
				r.setModuleIds(ids);
				r.setModuleNames(names);
			}
		}
		return r;
	}

}
