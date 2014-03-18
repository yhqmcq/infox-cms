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
import com.infox.sysmgr.entity.OrgEntity;
import com.infox.sysmgr.service.CompanyOrServiceI;
import com.infox.sysmgr.web.form.Company1Form;

@Service
@Transactional
public class CompanyOrServiceImpl implements CompanyOrServiceI {

	@Autowired
	private BaseDaoI<OrgEntity> basedaoOrg;

	@Override
	public void add(Company1Form form) throws Exception {
		
		OrgEntity entity = new OrgEntity();
		BeanUtils.copyProperties(form, entity, new String[] { "created", "lastmod" });
		
		if(form.getPid() != null && !"".equalsIgnoreCase(form.getPid())) {
			entity.setOrg(this.basedaoOrg.get(OrgEntity.class, form.getPid())) ;
		}

		this.basedaoOrg.save(entity);
	}

	@Override
	public void delete(String id) throws Exception {
		OrgEntity t = this.basedaoOrg.get(OrgEntity.class, id);
		del(t);
	}

	private void del(OrgEntity entity) {

		if (entity.getOrgs() != null && entity.getOrgs().size() > 0) {
			for (OrgEntity r : entity.getOrgs()) {
				del(r);
			}
		}
		this.basedaoOrg.delete(entity);
	}

	@Override
	public void edit(Company1Form form) throws Exception {
		
		OrgEntity entity = this.basedaoOrg.get(OrgEntity.class, form.getId());
		
		if(entity != null) {
			BeanUtils.copyProperties(form, entity ,new String[]{"creater"});
			
			if (form.getPid() != null && !form.getPid().equalsIgnoreCase("")) {
				entity.setOrg(basedaoOrg.get(OrgEntity.class, form.getPid()));
			}
			if (form.getPid() != null && !form.getPid().equalsIgnoreCase("")) {// 说明前台选中了上级资源
				OrgEntity pt = basedaoOrg.get(OrgEntity.class, form.getPid());
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
	private boolean isChildren(OrgEntity entity, OrgEntity pt) {
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
	public Company1Form get(String id) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		OrgEntity entity = this.basedaoOrg.get("from CompanyEntity t where t.id = :id", params);
		
		Company1Form r = new Company1Form();
		
		if(null != entity) {
			BeanUtils.copyProperties(entity, r);
			if (entity.getOrg() != null) {
				r.setPid(entity.getOrg().getId());
			}
		}
		return r ;
	}

	@Override
	public List<Company1Form> treegrid(Company1Form form ,String mode) throws Exception {
		String hql = "select t from CompanyEntity t where t.org is null order by created desc" ;
		
		List<OrgEntity> entitys = this.basedaoOrg.find(hql) ;
		
		List<Company1Form> orgsform = new ArrayList<Company1Form>() ;
		
		for (OrgEntity menuEntity : entitys) {
			orgsform.add(recursiveNode(menuEntity ,mode)) ;
		}
		
		return orgsform ;
	}
	
	public Company1Form recursiveNode(OrgEntity me ,String mode) {
		Company1Form mf = new Company1Form() ;
		BeanUtils.copyProperties(me, mf) ;
		
		//combotree方式显示
		mf.setText(me.getFullname()) ;
		
		if(null != me.getOrgs() && me.getOrgs().size() > 0) {
			mf.setState("closed") ;
			
			Set<OrgEntity> rs = me.getOrgs() ;
			List<Company1Form> children = new ArrayList<Company1Form>();
			for (OrgEntity menuEntity : rs) {
				
				//combotree方式显示
				if("combotree".equalsIgnoreCase(mode)) {
						Company1Form tn = recursiveNode(menuEntity ,mode) ;
						BeanUtils.copyProperties(menuEntity ,tn) ;
						children.add(tn);
				} else {
					Company1Form tn = recursiveNode(menuEntity ,mode) ;
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
