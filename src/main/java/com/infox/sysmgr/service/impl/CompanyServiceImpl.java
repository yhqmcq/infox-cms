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
import com.infox.sysmgr.entity.CompanyOrgEntity;
import com.infox.sysmgr.service.CompanyServiceI;
import com.infox.sysmgr.web.form.CompanyForm;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyServiceI {

	@Autowired
	private BaseDaoI<CompanyOrgEntity> basedaoOrg;

	@Override
	public void add(CompanyForm form) throws Exception {
		
		CompanyOrgEntity entity = new CompanyOrgEntity();
		BeanUtils.copyProperties(form, entity, new String[] { "created", "lastmod" });
		
		if(form.getPid() != null && !"".equalsIgnoreCase(form.getPid())) {
			entity.setOrg(this.basedaoOrg.get(CompanyOrgEntity.class, form.getPid())) ;
		}

		this.basedaoOrg.save(entity);
	}

	@Override
	public void delete(String id) throws Exception {
		CompanyOrgEntity t = this.basedaoOrg.get(CompanyOrgEntity.class, id);
		del(t);
	}

	private void del(CompanyOrgEntity entity) {

		if (entity.getOrgs() != null && entity.getOrgs().size() > 0) {
			for (CompanyOrgEntity r : entity.getOrgs()) {
				del(r);
			}
		}
		this.basedaoOrg.delete(entity);
	}

	@Override
	public void edit(CompanyForm form) throws Exception {
		
		CompanyOrgEntity entity = this.basedaoOrg.get(CompanyOrgEntity.class, form.getId());
		
		if(entity != null) {
			BeanUtils.copyProperties(form, entity ,new String[]{"creater"});
			
			if (form.getPid() != null && !form.getPid().equalsIgnoreCase("")) {
				entity.setOrg(basedaoOrg.get(CompanyOrgEntity.class, form.getPid()));
			}
			if (form.getPid() != null && !form.getPid().equalsIgnoreCase("")) {// 说明前台选中了上级资源
				CompanyOrgEntity pt = basedaoOrg.get(CompanyOrgEntity.class, form.getPid());
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
	private boolean isChildren(CompanyOrgEntity entity, CompanyOrgEntity pt) {
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
	public CompanyForm get(String id) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		CompanyOrgEntity entity = this.basedaoOrg.get("from CompanyEntity t where t.id = :id", params);
		
		CompanyForm r = new CompanyForm();
		
		if(null != entity) {
			BeanUtils.copyProperties(entity, r);
			if (entity.getOrg() != null) {
				r.setPid(entity.getOrg().getId());
			}
		}
		return r ;
	}

	@Override
	public List<CompanyForm> treegrid(CompanyForm form ,String mode) throws Exception {
		String hql = "select t from CompanyEntity t where t.org is null order by created desc" ;
		
		List<CompanyOrgEntity> entitys = this.basedaoOrg.find(hql) ;
		
		List<CompanyForm> orgsform = new ArrayList<CompanyForm>() ;
		
		for (CompanyOrgEntity menuEntity : entitys) {
			orgsform.add(recursiveNode(menuEntity ,mode)) ;
		}
		
		return orgsform ;
	}
	
	public CompanyForm recursiveNode(CompanyOrgEntity me ,String mode) {
		CompanyForm mf = new CompanyForm() ;
		BeanUtils.copyProperties(me, mf) ;
		
		//combotree方式显示
		mf.setText(me.getFullname()) ;
		
		if(null != me.getOrgs() && me.getOrgs().size() > 0) {
			mf.setState("closed") ;
			
			Set<CompanyOrgEntity> rs = me.getOrgs() ;
			List<CompanyForm> children = new ArrayList<CompanyForm>();
			for (CompanyOrgEntity menuEntity : rs) {
				
				//combotree方式显示
				if("combotree".equalsIgnoreCase(mode)) {
						CompanyForm tn = recursiveNode(menuEntity ,mode) ;
						BeanUtils.copyProperties(menuEntity ,tn) ;
						children.add(tn);
				} else {
					CompanyForm tn = recursiveNode(menuEntity ,mode) ;
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
