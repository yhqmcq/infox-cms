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
import com.infox.sysmgr.entity.DeptEntity;
import com.infox.sysmgr.service.DeptServiceI;
import com.infox.sysmgr.web.form.DeptForm;

@Service
@Transactional
public class DeptServiceImpl implements DeptServiceI {

	@Autowired
	private BaseDaoI<DeptEntity> basedaoDept;
	
	@Autowired
	private BaseDaoI<CompanyEntity> basedaoCompany;

	@Override
	public Json add(DeptForm form) {
		Json j = new Json();
		try {
			if(null == form.getCompany_id() || "".equals(form.getCompany_id())) {
				j.setMsg("部门必须隶属于某个公司！") ; return j ;
			}
			DeptEntity entity = new DeptEntity();
			BeanUtils.copyProperties(form, entity, new String[] { "created" });

			if (form.getPid() != null && !"".equalsIgnoreCase(form.getPid())) {
				entity.setDept(this.basedaoDept.get(DeptEntity.class, form.getPid()));
			}
			if(form.getCompany_id() != null && !"".equals(form.getCompany_id())) {
				entity.setCompany(this.basedaoCompany.get(CompanyEntity.class, form.getCompany_id())) ;
			}
			this.basedaoDept.save(entity);
			
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
			DeptEntity t = this.basedaoDept.get(DeptEntity.class, id);
			del(t);
			
			j.setMsg("删除成功！") ;
			j.setStatus(true) ;
		} catch (BeansException e) {
			j.setMsg("删除失败！") ;
		}
		return j;
	}
	
	private void del(DeptEntity entity) {
		if (entity.getDepts() != null && entity.getDepts().size() > 0) {
			for (DeptEntity r : entity.getDepts()) {
				del(r);
			}
		}
		this.basedaoDept.delete(entity);
	}

	@Override
	public Json edit(DeptForm form) {
		Json j = new Json();
		try {
			if(null == form.getCompany_id() || "".equals(form.getCompany_id())) {
				j.setMsg("部门必须隶属于某个公司！") ; return j ;
			}
			DeptEntity entity = this.basedaoDept.get(DeptEntity.class, form.getId());
			
			if(entity != null) {
				BeanUtils.copyProperties(form, entity ,new String[]{"creater"});
				
				if (form.getPid() != null && !form.getPid().equalsIgnoreCase("")) {
					entity.setDept(basedaoDept.get(DeptEntity.class, form.getPid()));
				}
				if (form.getPid() != null && !form.getPid().equalsIgnoreCase("")) {// 说明前台选中了上级资源
					DeptEntity pt = basedaoDept.get(DeptEntity.class, form.getPid());
					isChildren(entity, pt);// 说明要将当前资源修改到当前资源的子/孙子资源下
					entity.setDept(pt);
				} else {
					entity.setDept(null);// 前台没有选中上级资源，所以就置空
				}
				if(form.getCompany_id() != null && !"".equals(form.getCompany_id())) {
					entity.setCompany(this.basedaoCompany.get(CompanyEntity.class, form.getCompany_id())) ;
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
	private boolean isChildren(DeptEntity entity, DeptEntity pt) {
		if (pt != null && pt.getDept() != null) {
			if (pt.getDept().getId().equalsIgnoreCase(entity.getId())) {
				pt.setDept(null);
				return true;
			} else {
				return isChildren(entity, pt.getDept());
			}
		}
		return false;
	}
	

	@Override
	public DeptForm get(String id) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		DeptEntity entity = this.basedaoDept.get("select t from DeptEntity t where t.id = :id", params);
		
		DeptForm form = new DeptForm();
		
		if(null != entity) {
			BeanUtils.copyProperties(entity, form);
			if (entity.getDept() != null) {
				form.setPid(entity.getDept().getId());
			}
		}
		return form ;
	}

	@Override
	public List<DeptForm> treegrid(DeptForm form, String mode) {
		String hql = "select t from DeptEntity t where t.org is null order by created desc" ;
		
		List<DeptEntity> entitys = this.basedaoDept.find(hql) ;
		
		List<DeptForm> companysform = new ArrayList<DeptForm>() ;
		
		for (DeptEntity menuEntity : entitys) {
			companysform.add(recursiveNode(menuEntity ,mode)) ;
		}
		return companysform ;
	}
	
	public DeptForm recursiveNode(DeptEntity me ,String mode) {
		DeptForm mf = new DeptForm() ;
		BeanUtils.copyProperties(me, mf) ;
		
		//combotree方式显示
		mf.setText(me.getName()) ;
		
		if(null != me.getDepts() && me.getDepts().size() > 0) {
			mf.setState("closed") ;
			
			Set<DeptEntity> rs = me.getDepts() ;
			List<DeptForm> children = new ArrayList<DeptForm>();
			for (DeptEntity menuEntity : rs) {
				
				//combotree方式显示
				if("combotree".equalsIgnoreCase(mode)) {
						DeptForm tn = recursiveNode(menuEntity ,mode) ;
						BeanUtils.copyProperties(menuEntity ,tn) ;
						children.add(tn);
				} else {
					DeptForm tn = recursiveNode(menuEntity ,mode) ;
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
