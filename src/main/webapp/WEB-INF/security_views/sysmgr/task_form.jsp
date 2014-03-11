<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
.line{height: 25px;line-height: 25px;margin: 3px;}
.imp{padding-left: 25px;}
.col{width:95px;}
</style>
<script type="text/javascript">
	var form_url = siteUtil.basePath+"/sysmgr/task/add.do" ;
	
	$(function() {
		var p = $("#p").panel({
	        title: "Cron表达式",iconCls: 'icon-standard-map',fit:true,
	        href: siteUtil.basePath+"/common/page/task_cron.jsp",
	        iniframe: false
	    });
		var t = $("#task_type").combobox({
			url: siteUtil.basePath+"/common/view-index-resource/task-job-data.json",
			width: 157, valueField:'id',textField:'task_type',
			panelHeight:'auto', editable:false, autoShowPanel: true,
			onSelect: function(rec){
				var task = $.array.first(t.combobox('getData'), function (val) { return val.id == rec.id; });
				$("input[name=task_job_class]").val(task.task_job_class);
				$("input[name=task_type_name]").val(rec.task_type);
			}
	    });
		
		$.util.exec(function () {
			//编辑，加载表单数据
			if($('input[name=id]').val().length > 0) {
				form_url = siteUtil.basePath+"/sysmgr/task/edit.do" ;
				$.post(siteUtil.basePath+"/sysmgr/task/get.do", {id:$('input[name=id]').val()}, function(result) {
					if (result.id != undefined) {
						$('form').form('load', {
							'id' : result.id,
							'task_name' : result.task_name,
							'task_enable' : result.task_enable,
							'task_remark' : result.task_remark,
							'task_code' : result.task_code,
							'task_type_name' : result.task_type_name,
							'task_job_class' : result.task_job_class
						});
						t.combobox("disable",true);
						t.combobox('setValue',result.task_type);
						$("input[name=cron_expression]").val(result.cron_expression) ;
					}
				}, 'json');
			}
			$("#form input:visible")[0].focus();
		},100);
	});
	
	//提交表单数据
	var submitNow = function($dialog, $datagrid) {
		var o = {};o=$("#form").form("getData");
		o["cron_expression"]=$("input[name=cron_expression]").val();
		$.post(form_url, o, function(result) {
			if (result.status) {
				$datagrid.datagrid('reload') ;
				$.easyui.messager.show({ icon: "info", msg: "保存记录成功。" });
			} else {
				$.easyui.messager.show({ icon: "info", msg: "保存记录失败。" });
				return false ;
			}
		}, 'json');
	};
	
	//验证表单
	var submitForm = function($dialog, $datagrid) {
		if($('#form').form('validate')) {
			return submitNow($dialog, $datagrid) ;
		} else{
			return false ;
		}
	};
	
	
</script>

<div class="easyui-layout" data-options="fit: true">
	<div data-options="region: 'west', title: '任务属性', iconCls: 'icon-standard-map', split: true, minWidth: 250, maxWidth: 500" style="width: 250px; padding: 1px;">
		<form id="form" class="easyui-form">
			<input type="hidden" name="id" value="${id}" />
			<input type="hidden" name="creater_id" value="${sessionInfo.id}" />
			<input type="hidden" name="creater_name" value="${sessionInfo.truename}" />
			<input type="hidden" name="task_code" />
			<input type="hidden" name="task_type_name" />
			<input type="hidden" name="task_job_class" />
			<table id="tab_box">
				<tr>
					<th>任务类型：</th>
					<td><input id="task_type" name="task_type" /></td>
				</tr>
				<tr>
					<th>任务名称：</th>
					<td><input name="task_name" class="easyui-validatebox" type="text" data-options="required:true, prompt: '任务名称'" /></td>
				</tr>
				<tr>
					<th>任务状态：</th>
					<td>
						<input class="easyui-combobox" style="width:157px;" name="task_enable" data-options="
							valueField: 'label', textField: 'value', editable: false, value : 'Y',
							data: [{ label: 'Y', value: '启动' },{ label: 'N', value: '停止' }],
							panelHeight:'auto', editable:false" />
					</td>
				</tr>
				<td colspan="4">
					<textarea class="easyui-validatebox" name="task_remark" style="height:100px;width:220px;" data-options="prompt: '任务备注'" ></textarea>
				</td>
			</table>
			
		</form>
	</div>
	<div data-options="region: 'center', split: true, minWidth: 250, maxWidth: 500" style="width: 250px; padding: 1px;">
		<div id="p"></div>
	</div>
</div>
