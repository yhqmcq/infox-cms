package com.infox.sysmgr.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infox.common.dao.BaseDaoI;
import com.infox.common.web.page.DataGrid;
import com.infox.sysmgr.entity.EmpOnlineEntity;
import com.infox.sysmgr.entity.EmployeeEntity;
import com.infox.sysmgr.service.EmployeeOnlineServiceI;
import com.infox.sysmgr.service.EmployeeServiceI;
import com.infox.sysmgr.web.form.EmpOnlineForm;
import com.infox.sysmgr.web.form.EmployeeForm;

@Service
@Transactional
public class EmployeeOnlineServiceImpl implements EmployeeOnlineServiceI {
	
	@Autowired
	private BaseDaoI<EmpOnlineEntity> basedaoEmpOnline;
	
	@Autowired
	private EmployeeServiceI empService ;

	@Override
	public void add(EmpOnlineForm form) throws Exception {
		EmpOnlineEntity entity = new EmpOnlineEntity();
		BeanUtils.copyProperties(form, entity);
		EmployeeEntity empEntity = new EmployeeEntity() ;
		empEntity.setId(form.getEmpid()) ;
		this.basedaoEmpOnline.save(entity);
		
		EmployeeForm employeeForm = this.empService.get(form.getEmpid()) ;
		employeeForm.setOnlineState(form.getType()) ;
		this.empService.edit(employeeForm) ;
	}

	@Override
	public void delete(String id) throws Exception {
		EmpOnlineEntity entity = this.basedaoEmpOnline.get(EmpOnlineEntity.class, id);
		if("0".equals(entity.getType())){
			this.basedaoEmpOnline.delete(entity);
		}
	}

	@Override
	public void edit(EmpOnlineForm form) throws Exception {
		EmpOnlineEntity entity = this.basedaoEmpOnline.get(EmpOnlineEntity.class, form.getId());
		BeanUtils.copyProperties(form, entity ,new String[]{});
		this.basedaoEmpOnline.update(entity);
	}

	@Override
	public EmpOnlineForm get(String id) throws Exception {
		EmpOnlineForm form = new EmpOnlineForm();
		EmpOnlineEntity entity = this.basedaoEmpOnline.get(EmpOnlineEntity.class, id);

		BeanUtils.copyProperties(entity, form);
		return form;
	}

	@Override
	public DataGrid datagrid(EmpOnlineForm form) throws Exception {
		DataGrid datagrid = new DataGrid();
		datagrid.setTotal(this.total(form));
		datagrid.setRows(this.changeModel(this.find(form)));
		return datagrid;
	}

	private List<EmpOnlineForm> changeModel(List<EmpOnlineEntity> EmpOnlineEntity) {
		List<EmpOnlineForm> forms = new ArrayList<EmpOnlineForm>();

		if (null != EmpOnlineEntity && EmpOnlineEntity.size() > 0) {
			for (EmpOnlineEntity i : EmpOnlineEntity) {
				EmpOnlineForm uf = new EmpOnlineForm();
				BeanUtils.copyProperties(i, uf);
				forms.add(uf);
			}
		}
		return forms;
	}

	private List<EmpOnlineEntity> find(EmpOnlineForm form) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "select t from EmpOnlineEntity t where 1=1";
		hql = addWhere(hql, form, params) + addOrdeby(form);
		return this.basedaoEmpOnline.find(hql, params, form.getPage(), form.getRows());
	}

	private String addOrdeby(EmpOnlineForm form) {
		String orderString = "";
		if (form.getSort() != null && form.getOrder() != null) {
			orderString = " order by " + form.getSort() + " " + form.getOrder();
		}
		return orderString;
	}

	private Long total(EmpOnlineForm form) {
		Map<String, Object> params = new HashMap<String, Object>();

		String hql = "select count(*) from EmpOnlineEntity t where 1=1";

		hql = addWhere(hql, form, params);

		return this.basedaoEmpOnline.count(hql, params);
	}

	private String addWhere(String hql, EmpOnlineForm form, Map<String, Object> params) {
		if (null != form) {
			if (form.getType() != null && !"".equals(form.getType())) {
				hql += " and t.type=:type";
				params.put("type", form.getType());
			}
		}
		return hql;
	}

}
