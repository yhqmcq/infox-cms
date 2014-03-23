package com.infox.sysmgr.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infox.common.dao.BaseDaoI;
import com.infox.common.util.FileUtil;
import com.infox.common.web.page.Json;
import com.infox.sysmgr.entity.ModuleEntity;
import com.infox.sysmgr.service.ModuleServiceI;
import com.infox.sysmgr.web.form.ModuleForm;

@Service
@Transactional
public class ModuleServiceImpl implements ModuleServiceI {

	@Autowired
	private BaseDaoI<ModuleEntity> basedaoModule;
	
	@Override
	public Json save(ModuleForm form) {
		Json j = new Json();
		try {
			ModuleEntity entity = new ModuleEntity();
			BeanUtils.copyProperties(form, entity, new String[]{"id"});

			if (null != form.getPid() && !"".equals(form.getPid())) {
				entity.setModule(this.basedaoModule.get(ModuleEntity.class, form.getPid()));
			}
			this.basedaoModule.save(entity);
			j.setMsg("创建成功！");
			j.setStatus(true);
		} catch (BeansException e) {
			j.setMsg("创建失败！");
		}
		return j;
	}

	@Override
	public Json delete(String id) {
		Json j = new Json();
		try {
			ModuleEntity t = this.basedaoModule.get(ModuleEntity.class, id);
			del(t);
			j.setMsg("创建成功！");
			j.setStatus(true);
		} catch (BeansException e) {
			j.setMsg("创建失败！");
		}
		return j;
	}

	private void del(ModuleEntity entity) {
		if (entity.getModules() != null && entity.getModules().size() > 0) {
			for (ModuleEntity e : entity.getModules()) {
				del(e);
			}
		}
		this.basedaoModule.delete(entity);
	}

	@Override
	public Json edit(ModuleForm form) {
		Json j = new Json();
		try {
			ModuleEntity entity = this.basedaoModule.get(ModuleEntity.class, form.getId());
			BeanUtils.copyProperties(form, entity);
			if (null != form.getPid() && !"".equals(form.getPid())) {
				entity.setModule(this.basedaoModule.get(ModuleEntity.class, form.getPid()));
			}
			this.basedaoModule.update(entity);
			j.setMsg("创建成功！");
			j.setStatus(true);
		} catch (BeansException e) {
			j.setMsg("创建失败！");
		}
		return j;
	}

	@Override
	public Json ondrop(ModuleForm form) {
		Json j = new Json();
		try {

			j.setMsg("创建成功！");
			j.setStatus(true);
		} catch (BeansException e) {
			j.setMsg("创建失败！");
		}
		return j;
	}

	@Override
	public ModuleForm get(String id) {
		ModuleEntity entity = this.basedaoModule.get(ModuleEntity.class, id);
		ModuleForm form = new ModuleForm();
		BeanUtils.copyProperties(entity, form);
		if (null != entity.getModule()) {
			form.setPid(entity.getModule().getId());
		}
		return form;
	}

	@Override
	public List<ModuleForm> findMenusAll(ModuleForm form) {
		List<ModuleForm> forms = new ArrayList<ModuleForm>();

		String hql = "select t from ModuleEntity t where t.module is null and t.type='R' order by seq desc";
		List<ModuleEntity> menus = this.basedaoModule.find(hql);
		for (ModuleEntity entity : menus) {
			forms.add(recursiveNaviNode(entity));
		}
		return forms;
	}

	public ModuleForm recursiveNaviNode(ModuleEntity me) {
		ModuleForm mf = new ModuleForm();
		BeanUtils.copyProperties(me, mf);
		mf.setText(me.getModuleName());

		Map<String, String> attributes = new HashMap<String, String>();
		attributes.put("href", me.getLinkUrl());
		mf.setAttributes(attributes);

		if (null != me.getModules() && me.getModules().size() > 0) {

			Set<ModuleEntity> rs = me.getModules();
			List<ModuleForm> children = new ArrayList<ModuleForm>();
			for (ModuleEntity entity : rs) {
					ModuleForm tn = recursiveNaviNode(entity);
					BeanUtils.copyProperties(entity, tn, new String[] { "state" });
					tn.setText(entity.getModuleName());
					children.add(tn);
			}

			mf.setChildren(children);
		}
		return mf;
	}

	@Override
	public void exportMenusAll(ServletContext sc) {
		List<ModuleForm> menu = new ArrayList<ModuleForm>();

		String hql = "select t from ModuleEntity t where t.module is null and t.type='R' order by seq desc";
		List<ModuleEntity> menus = this.basedaoModule.find(hql);
		for (ModuleEntity entity : menus) {
				ModuleForm mf = new ModuleForm();
				BeanUtils.copyProperties(entity, mf);
				mf.setText(entity.getModuleName());
				menu.add(mf);
				exportTree(entity.getModules(), entity, sc);
		}
		String path = sc.getRealPath("/common/view-index-resource/") + "/nav-menu-data.json";
		FileUtil.outJson(path, "", menu);
	}

	public void exportTree(Set<ModuleEntity> menus, ModuleEntity m, ServletContext sc) {
		List<ModuleForm> forms = new ArrayList<ModuleForm>();
		if (null != menus && menus.size() > 0) {
			for (ModuleEntity entity : menus) {
				if(!"O".equals(entity.getType())) {
					forms.add(recursiveNaviNodeExport(entity));
				}
			}
		}
		String path = sc.getRealPath("/common/view-index-resource/") + "/nav-" + m.getId() + "-menu-data.json";
		FileUtil.outJson(path, "", forms);
	}

	
	public ModuleForm recursiveNaviNodeExport(ModuleEntity me) {
		ModuleForm mf = new ModuleForm();
		BeanUtils.copyProperties(me, mf);
		mf.setText(me.getModuleName());

		Map<String, String> attributes = new HashMap<String, String>();
		attributes.put("href", me.getLinkUrl());
		mf.setAttributes(attributes);

		if (null != me.getModules() && me.getModules().size() > 0) {

			Set<ModuleEntity> rs = me.getModules();
			List<ModuleForm> children = new ArrayList<ModuleForm>();
			for (ModuleEntity entity : rs) {
				if(!"O".equals(entity.getType())) {
					ModuleForm tn = recursiveNaviNode(entity);
					BeanUtils.copyProperties(entity, tn, new String[] { "state" });
					tn.setText(entity.getModuleName());
					children.add(tn);
				}
			}

			mf.setChildren(children);
		}
		return mf;
	}
	
}
