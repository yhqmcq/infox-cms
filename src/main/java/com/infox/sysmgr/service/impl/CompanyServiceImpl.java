package com.infox.sysmgr.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infox.common.dao.BaseDaoI;
import com.infox.common.web.page.Json;
import com.infox.sysmgr.entity.CompanyEntity;
import com.infox.sysmgr.service.CompanyServiceI;
import com.infox.sysmgr.web.form.CompanyForm;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyServiceI {

	@Autowired
	private BaseDaoI<CompanyEntity> basedaoCompany;

	@Override
	public Json add(CompanyForm form) {
		Json j = new Json();
		try {
			CompanyEntity entity = new CompanyEntity();
			BeanUtils.copyProperties(form, entity, new String[] { "created" });

			if (form.getPid() != null && !"".equalsIgnoreCase(form.getPid())) {
				entity.setCompany(this.basedaoCompany.get(CompanyEntity.class, form.getPid()));
			}
			this.basedaoCompany.save(entity);
			
			j.setMsg("创建成功！") ;
			j.setStatus(true) ;
		} catch (BeansException e) {
			j.setMsg("创建失败！") ;
		}
		return j;
	}

	@Override
	public Json delete(String id) {
		Json j = new Json();
		try {
			CompanyEntity t = this.basedaoCompany.get(CompanyEntity.class, id);
			del(t);
			
			j.setMsg("删除成功！") ;
			j.setStatus(true) ;
		} catch (BeansException e) {
			j.setMsg("删除失败！") ;
		}
		return j;
	}
	
	private void del(CompanyEntity entity) {
		if (entity.getCompanys() != null && entity.getCompanys().size() > 0) {
			for (CompanyEntity r : entity.getCompanys()) {
				del(r);
			}
		}
		this.basedaoCompany.delete(entity);
	}

	@Override
	public Json edit(CompanyForm form) {
		Json j = new Json();
		try {
			CompanyEntity entity = this.basedaoCompany.get(CompanyEntity.class, form.getId());
			
			if(entity != null) {
				BeanUtils.copyProperties(form, entity ,new String[]{"creater"});
				
				if (form.getPid() != null && !form.getPid().equalsIgnoreCase("")) {
					entity.setCompany(basedaoCompany.get(CompanyEntity.class, form.getPid()));
				}
				if (form.getPid() != null && !form.getPid().equalsIgnoreCase("")) {// 说明前台选中了上级资源
					CompanyEntity pt = basedaoCompany.get(CompanyEntity.class, form.getPid());
					isChildren(entity, pt);// 说明要将当前资源修改到当前资源的子/孙子资源下
					entity.setCompany(pt);
				} else {
					entity.setCompany(null);// 前台没有选中上级资源，所以就置空
				}
			}
			
			j.setMsg("删除成功！") ;
			j.setStatus(true) ;
		} catch (BeansException e) {
			j.setMsg("删除失败！") ;
		}
		return j;
	}
	
	/**
	 * 判断是否是将当前节点修改到当前节点的子节点
	 * @param entity 当前节点
	 * @param pt 要修改到的节点
	 * @return
	 */
	private boolean isChildren(CompanyEntity entity, CompanyEntity pt) {
		if (pt != null && pt.getCompany() != null) {
			if (pt.getCompany().getId().equalsIgnoreCase(entity.getId())) {
				pt.setCompany(null);
				return true;
			} else {
				return isChildren(entity, pt.getCompany());
			}
		}
		return false;
	}
	

	@Override
	public CompanyForm get(String id) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		CompanyEntity entity = this.basedaoCompany.get("select t from CompanyEntity t where t.id = :id", params);
		
		CompanyForm form = new CompanyForm();
		
		if(null != entity) {
			BeanUtils.copyProperties(entity, form);
			if (entity.getCompany() != null) {
				form.setPid(entity.getCompany().getId());
			}
		}
		return form ;
	}

	@Override
	public List<CompanyForm> treegrid(CompanyForm form, String mode) {
		String hql = "select t from CompanyEntity t where t.org is null order by created desc" ;
		
		List<CompanyEntity> entitys = this.basedaoCompany.find(hql) ;
		
		List<CompanyForm> companysform = new ArrayList<CompanyForm>() ;
		
		for (CompanyEntity menuEntity : entitys) {
			companysform.add(recursiveNode(menuEntity ,mode)) ;
		}
		return companysform ;
	}
	
	public CompanyForm recursiveNode(CompanyEntity me ,String mode) {
		CompanyForm mf = new CompanyForm() ;
		BeanUtils.copyProperties(me, mf) ;
		
		//combotree方式显示
		mf.setText(me.getName()) ;
		
		if(null != me.getCompanys() && me.getCompanys().size() > 0) {
			mf.setState("closed") ;
			
			Set<CompanyEntity> rs = me.getCompanys() ;
			List<CompanyForm> children = new ArrayList<CompanyForm>();
			for (CompanyEntity menuEntity : rs) {
				
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
