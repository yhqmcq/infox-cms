package com.infox.sysmgr.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infox.common.dao.BaseDaoI;
import com.infox.common.util.FileUtil;
import com.infox.sysmgr.entity.MenuEntity;
import com.infox.sysmgr.service.MenuServiceI;
import com.infox.sysmgr.web.form.MenuForm;

@Service
@Transactional
public class MenuServiceImpl implements MenuServiceI {
	
	@Autowired
	private BaseDaoI<MenuEntity> basedaoMenu ;

	@Override
	public void add(MenuForm form) throws Exception {
		MenuEntity entity = new MenuEntity() ;
		BeanUtils.copyProperties(form, entity) ;
		
		if(null != form.getPid() && !"".equals(form.getPid())){
			entity.setMenu(this.basedaoMenu.get(MenuEntity.class, form.getPid())) ;
		}
		this.basedaoMenu.save(entity) ;
	}

	@Override
	public void delete(String id) throws Exception {
		MenuEntity t = this.basedaoMenu.get(MenuEntity.class, id);
		del(t);
	}
	private void del(MenuEntity entity) {
		if (entity.getMenus() != null && entity.getMenus().size() > 0) {
			for (MenuEntity e : entity.getMenus()) {
				del(e);
			}
		}
		this.basedaoMenu.delete(entity) ;
	}

	@Override
	public void edit(MenuForm form) throws Exception {
		MenuEntity entity = this.basedaoMenu.get(MenuEntity.class, form.getId()) ;
		BeanUtils.copyProperties(form, entity) ;
		if(null != form.getPid() && !"".equals(form.getPid())){
			entity.setMenu(this.basedaoMenu.get(MenuEntity.class, form.getPid())) ;
		}
		this.basedaoMenu.update(entity) ;
	}

	@Override
	public MenuForm get(String id) throws Exception {
		MenuEntity entity = this.basedaoMenu.get(MenuEntity.class, id) ;
		MenuForm form = new MenuForm() ;
		BeanUtils.copyProperties(entity, form) ;
		if(null != entity.getMenu()) {
			form.setPid(entity.getMenu().getId()) ;
		}
		return form ;
	}

	@Override
	public List<MenuForm> findMenusAll(MenuForm form) throws Exception {
		List<MenuForm> forms = new ArrayList<MenuForm>() ;
		
		String hql = "select t from MenuEntity t where t.menu is null and t.type='R' order by seq desc" ;
		List<MenuEntity> menus = this.basedaoMenu.find(hql) ;
		for (MenuEntity entity : menus) {
			forms.add(recursiveNaviNode(entity)) ;
		}
		return forms ;
	}
	
	public MenuForm recursiveNaviNode(MenuEntity me) {
		MenuForm mf = new MenuForm() ;
		BeanUtils.copyProperties(me, mf) ;
		mf.setText(me.getName()) ;
		
		Map<String, String> attributes = new HashMap<String, String>();
		attributes.put("href", me.getHref());
		mf.setAttributes(attributes) ;
		
		if(null != me.getMenus() && me.getMenus().size() > 0) {
			
			Set<MenuEntity> rs = me.getMenus() ;
			List<MenuForm> children = new ArrayList<MenuForm>();
			for (MenuEntity menuEntity : rs) {
				
				MenuForm tn = recursiveNaviNode(menuEntity) ;
				BeanUtils.copyProperties(menuEntity ,tn ,new String[]{"state"}) ;
				tn.setText(menuEntity.getName()) ;
				children.add(tn);
			}
			
			mf.setChildren(children) ;
		}
		return mf ;
	}
	
	@Override
	public void ondrop(MenuForm form) throws Exception {
		MenuEntity entity = this.basedaoMenu.get(MenuEntity.class, form.getId()) ;
		
		MenuEntity targetMenu = this.basedaoMenu.get(MenuEntity.class, form.getPid()) ;
		
		if("bottom".equalsIgnoreCase(form.getPoint()) || "top".equalsIgnoreCase(form.getPoint())) {
			MenuEntity parentMenu = targetMenu.getMenu() ;
			entity.setMenu(parentMenu) ;
			if(null == parentMenu){entity.setType("R") ;} else {entity.setType("F") ;}
			if("bottom".equalsIgnoreCase(form.getPoint())) {
				entity.setSeq(targetMenu.getSeq()-1) ;
			} else {
				entity.setSeq(targetMenu.getSeq()+1) ;
			}
		} else {
			entity.setType("F") ;
			entity.setMenu(targetMenu) ;
		}
		this.basedaoMenu.update(entity) ;
	}

	
	@Override
	public void exportMenusAll(ServletContext sc) throws Exception {
		List<MenuForm> menu = new ArrayList<MenuForm>() ;
		
		String hql = "select t from MenuEntity t where t.menu is null and t.type='R' order by seq desc" ;
		List<MenuEntity> menus = this.basedaoMenu.find(hql) ;
		for (MenuEntity entity : menus) {
			MenuForm mf = new MenuForm() ;
			BeanUtils.copyProperties(entity, mf) ;
			mf.setText(entity.getName()) ;
			
			menu.add(mf) ;
			
			exportTree(entity.getMenus(), entity, sc);
		}
		String path = sc.getRealPath("/common/view-index-resource/") + "/nav-menu-data.json" ;
		FileUtil.outJson(path, "", menu) ;
	}
	
	public void exportTree(Set<MenuEntity> menus, MenuEntity m, ServletContext sc) {
		List<MenuForm> forms = new ArrayList<MenuForm>() ;
		if(null != menus && menus.size() > 0) {
			for (MenuEntity entity : menus) {
				forms.add(recursiveNaviNode(entity)) ;
			}
		}
		String path = sc.getRealPath("/common/view-index-resource/") + "/nav-"+m.getId()+"-menu-data.json" ;
		FileUtil.outJson(path, "", forms) ;
	}

}
