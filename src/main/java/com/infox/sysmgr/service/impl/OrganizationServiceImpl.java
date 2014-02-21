package com.infox.sysmgr.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infox.common.dao.BaseDaoI;
import com.infox.common.util.RandomUtils;
import com.infox.sysmgr.entity.OrganizationEntity;
import com.infox.sysmgr.service.OrganizationServiceI;
import com.infox.sysmgr.web.form.OrganizationForm;

@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationServiceI {

	@Autowired
	private BaseDaoI<OrganizationEntity> basedaoOrg;

	@Override
	public void add(OrganizationForm form) throws Exception {
		
		OrganizationEntity entity = new OrganizationEntity();
		BeanUtils.copyProperties(form, entity, new String[] { "created", "lastmod" });
		entity.setId(RandomUtils.generateNumber(6)) ;
		
		if(form.getPid() != null && !"".equalsIgnoreCase(form.getPid())) {
			entity.setOrg(this.basedaoOrg.get(OrganizationEntity.class, form.getPid())) ;
		}

		this.basedaoOrg.save(entity);
	}

	@Override
	public void delete(String id) throws Exception {
		OrganizationEntity t = this.basedaoOrg.get(OrganizationEntity.class, id);
		del(t);
	}

	private void del(OrganizationEntity entity) {

		if (entity.getOrgs() != null && entity.getOrgs().size() > 0) {
			for (OrganizationEntity r : entity.getOrgs()) {
				del(r);
			}
		}
		this.basedaoOrg.delete(entity);
	}

	@Override
	public void edit(OrganizationForm form) throws Exception {
		
		OrganizationEntity entity = this.basedaoOrg.get(OrganizationEntity.class, form.getId());
		
		if(entity != null) {
			BeanUtils.copyProperties(form, entity ,new String[]{"creater"});
			
			if (form.getPid() != null && !form.getPid().equalsIgnoreCase("")) {
				entity.setOrg(basedaoOrg.get(OrganizationEntity.class, form.getPid()));
			}
			if (form.getPid() != null && !form.getPid().equalsIgnoreCase("")) {// 说明前台选中了上级资源
				OrganizationEntity pt = basedaoOrg.get(OrganizationEntity.class, form.getPid());
				isChildren(entity, pt);// 说明要将当前资源修改到当前资源的子/孙子资源下
				entity.setOrg(pt);
			} else {
				entity.setOrg(null);// 前台没有选中上级资源，所以就置空
			}
		}

	}
	
	/**
	 * 判断是否是将当前节点修改到当前节点的子节点
	 * @param entity 当前节点
	 * @param pt 要修改到的节点
	 * @return
	 */
	private boolean isChildren(OrganizationEntity entity, OrganizationEntity pt) {
		if (pt != null && pt.getOrg() != null) {
			if (pt.getOrg().getId().equalsIgnoreCase(entity.getId())) {
				pt.setOrg(null);
				return true;
			} else {
				return isChildren(entity, pt.getOrg());
			}
		}
		return false;
	}


	@Override
	public OrganizationForm get(String id) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		OrganizationEntity entity = this.basedaoOrg.get("from OrganizationEntity t where t.id = :id", params);
		
		OrganizationForm r = new OrganizationForm();
		
		if(null != entity) {
			BeanUtils.copyProperties(entity, r);
			if (entity.getOrg() != null) {
				r.setPid(entity.getOrg().getId());
			}
		}
		return r ;
	}

	@Override
	public List<OrganizationForm> org_treegrid(OrganizationForm form ,String mode) throws Exception {
		String hql = "select t from OrganizationEntity t where t.org is null order by created desc" ;
		
		List<OrganizationEntity> entitys = this.basedaoOrg.find(hql) ;
		
		List<OrganizationForm> orgsform = new ArrayList<OrganizationForm>() ;
		
		for (OrganizationEntity menuEntity : entitys) {
			orgsform.add(recursiveNode(menuEntity ,mode)) ;
		}
		
		return orgsform ;
	}
	
	public OrganizationForm recursiveNode(OrganizationEntity me ,String mode) {
		OrganizationForm mf = new OrganizationForm() ;
		BeanUtils.copyProperties(me, mf) ;
		
		//combotree方式显示
		mf.setText(me.getFullname()) ;
		
		if(null != me.getOrgs() && me.getOrgs().size() > 0) {
			mf.setState("closed") ;
			
			Set<OrganizationEntity> rs = me.getOrgs() ;
			List<OrganizationForm> children = new ArrayList<OrganizationForm>();
			for (OrganizationEntity menuEntity : rs) {
				
				//combotree方式显示
				if("combotree".equalsIgnoreCase(mode)) {
						OrganizationForm tn = recursiveNode(menuEntity ,mode) ;
						BeanUtils.copyProperties(menuEntity ,tn) ;
						children.add(tn);
				} else {
					OrganizationForm tn = recursiveNode(menuEntity ,mode) ;
					BeanUtils.copyProperties(menuEntity ,tn) ;
					children.add(tn);
				}
			}
			
			mf.setChildren(children) ;
			mf.setState("open");
		}
		return mf ;
	}

}
