<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
	var form_url = siteUtil.basePath+"/sysmgr/roleAction/add.do" ;
	
	$(function() {
		//编辑，加载表单数据
		if($('input[name=id]').val().length > 0) {
			form_url = siteUtil.basePath+"/sysmgr/roleAction/edit.do" ;
			$.post(siteUtil.basePath+"/sysmgr/roleAction/get.do", {id:$('input[name=id]').val()}, function(result) {
				if (result.id != undefined) {
					$('form').form('load', {
						'id' : result.id,
						'name' : result.name,
						'description' : result.description,
						'pid' : result.pid
					});
				}
			}, 'json');
		}
		$("#form input:visible")[0].focus();
	});
	
	//提交表单数据
	var submitNow = function($d, $dg) {
		$.post(form_url, $("#form").form("getData"), function(result) {
			if (result.status) {
				$dg.datagrid("reload") ;
				$.easyui.messager.show({ icon: "info", msg: result.msg });
				$.easyui.loaded(); $d.dialog("close") ;
			} else {
				$.easyui.messager.show({ icon: "info", msg: result.msg });
				return false ;
			}
		}, 'json');
	};
	
	//验证表单
	var submitForm = function($d, $dg) {
		if($('#form').form("validate")) {
			$.easyui.loading({ msg: "数据提交中，请稍等..." });
			submitNow($d, $dg) ;
		}
	};
	
	
</script>

<form id="form" class="easyui-form">
	<input type="hidden" name="id" value="${id}" />
	<div class="form_base">
		<table>
			<tr>
				<th>角色名称：</th>
				<td><input name="name" class="easyui-validatebox" type="text" data-options="required: true, prompt: '角色名称'" /></td>
			</tr>
			<tr>
				<th>描述：</th>
				<td><input name="description" class="easyui-validatebox" type="text" data-options="prompt: '角色备注'" /></td>
			</tr>
		</table>
	</div>
</form>
