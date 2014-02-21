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
import com.infox.common.quartz.SchedulerUtil;
import com.infox.common.util.RandomUtils;
import com.infox.common.util.UUIDHexGenerator;
import com.infox.common.web.page.DataGrid;
import com.infox.sysmgr.entity.TaskEntity;
import com.infox.sysmgr.service.TaskSchedulerServiceI;
import com.infox.sysmgr.web.form.TaskForm;

@Service
@Transactional
public class TaskSchedulerServiceImpl implements TaskSchedulerServiceI {

	@Autowired
	private BaseDaoI<TaskEntity> basedaoTask ;
	
	@Autowired
	private SchedulerUtil schedulerUtil ;

	@Override
	public void add(TaskForm form) throws Exception {
		try {
			form.setTask_code(UUIDHexGenerator.generator()) ;
			this.schedulerUtil.scheduler(form) ;
			
			TaskEntity entity = new TaskEntity() ;
			BeanUtils.copyProperties(form, entity) ;
			entity.setId(RandomUtils.generateNumber(6)) ;
			this.basedaoTask.save(entity) ;
		} catch (Exception e) {
			throw new Exception(e.getMessage()) ;
		}
	}

	@Override
	public void delete(String id) throws Exception {
		try {
			TaskEntity entity = this.basedaoTask.get(TaskEntity.class, id) ;
			if(null != entity) {
				TaskForm task = new TaskForm() ;
				BeanUtils.copyProperties(entity, task) ;
				this.schedulerUtil.deleteJob(task) ;
			}
			this.basedaoTask.delete(entity);
		} catch (Exception e) {
			throw new Exception(e.getMessage()) ;
		}
	}

	@Override
	public void edit(TaskForm form) throws Exception {
		try {
			String currentEnable = null, cron_expression = null ;
			
			TaskEntity entity = this.basedaoTask.get(TaskEntity.class, form.getId());
			currentEnable = entity.getTask_enable() ; cron_expression = entity.getCron_expression() ;
			BeanUtils.copyProperties(form, entity);
			this.basedaoTask.update(entity);
			
			//判断任务修改前的状态he修改后的状态是否不相同，不相同则修改任务的状态
			if(!form.getTask_enable().equalsIgnoreCase(currentEnable)) {
				TaskForm task = new TaskForm() ;
				BeanUtils.copyProperties(entity, task) ;
				if("Y".equalsIgnoreCase(form.getTask_enable())) {
					this.schedulerUtil.resumeJob(task) ;
				} else {
					this.schedulerUtil.pauseJob(task) ;
				}
			}
			//判断任务修改前和修改后的触发时间是否一致，不一致则重新设定触发时间
			if(!form.getCron_expression().equalsIgnoreCase(cron_expression)) {
				//不管任务的状态是否启动，都设为启动
				entity.setTask_enable("Y") ;
				TaskForm task = new TaskForm() ;
				BeanUtils.copyProperties(entity, task) ;
				this.schedulerUtil.rescheduleJob(task) ;
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage()) ;
		}
	}

	@Override
	public TaskForm get(String id) throws Exception {
		TaskForm form = new TaskForm();
		TaskEntity entity = this.basedaoTask.get(TaskEntity.class, id);
		BeanUtils.copyProperties(entity, form);
		return form;
	}

	@Override
	public DataGrid datagrid(TaskForm form) throws Exception {
		DataGrid datagrid = new DataGrid();
		datagrid.setTotal(this.total(form));
		datagrid.setRows(this.changeModel(this.find(form)));
		return datagrid;
	}
	
	
	private List<TaskForm> changeModel(List<TaskEntity> TaskEntity) {
		List<TaskForm> forms = new ArrayList<TaskForm>();

		if (null != TaskEntity && TaskEntity.size() > 0) {
			for (TaskEntity i : TaskEntity) {
				TaskForm uf = new TaskForm();
				BeanUtils.copyProperties(i, uf);
				forms.add(uf);
			}
		}
		return forms;
	}

	public List<TaskEntity> find(TaskForm form) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "select t from TaskEntity t where 1=1";
		hql = addWhere(hql, form, params) + addOrdeby(form);
		return this.basedaoTask.find(hql, params, form.getPage(), form.getRows());
	}

	private String addOrdeby(TaskForm form) {
		String orderString = "";
		if (form.getSort() != null && form.getOrder() != null) {
			orderString = " order by " + form.getSort() + " " + form.getOrder();
		}
		return orderString;
	}

	public Long total(TaskForm form) {
		Map<String, Object> params = new HashMap<String, Object>();

		String hql = "select count(*) from TaskEntity t where 1=1";

		hql = addWhere(hql, form, params);

		return this.basedaoTask.count(hql, params);
	}

	private String addWhere(String hql, TaskForm form, Map<String, Object> params) {
		if (null != form) {
			if(form.getTask_enable() != null && !form.getTask_enable().equals("")) {
				hql += " and t.task_enable=:task_enable";
				params.put("task_enable", form.getTask_enable());
			}
		}
		return hql;
	}
	
}
