<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
	var form_url = siteUtil.basePath+"/sysmgr/role/add.do" ;
	
	$(function() {
		$("#select1").combotree({
			url : siteUtil.basePath+"/sysmgr/role/treegrid.do",
			width:157, idFiled:'pid', textFiled:'name', editable: false,
			lines:true, autoShowPanel: true
	    });
		
		//编辑，加载表单数据
		if($('input[name=id]').val().length > 0) {
			form_url = siteUtil.basePath+"/sysmgr/role/edit.do" ;
			$.post(siteUtil.basePath+"/sysmgr/role/get.do", {id:$('input[name=id]').val()}, function(result) {
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
	var submitNow = function($dialog, $tree) {
		$.post(form_url, $("#form").form("getData"), function(result) {
			if (result.status) {
				$tree.treegrid('reload') ;
				$.easyui.messager.show({ icon: "info", msg: "保存记录成功。" });
			} else {
				$.easyui.messager.show({ icon: "info", msg: "保存记录失败。" });
				return false ;
			}
		}, 'json');
	};
	
	//验证表单
	var submitForm = function($dialog, $tree) {
		if($('#form').form('validate')) {
			return submitNow($dialog, $tree) ; ;
		} else{
			return false ;
		}
	};
	
	
</script>

<form id="form" class="easyui-form">
	<input type="hidden" name="id" value="${id}" />
	<div class="form_base">
		<table>
			<tr>
				<th>角色名称：</th>
				<td><input name="name" class="easyui-validatebox" type="text" data-options="required: true, prompt: '菜单名称'" /></td>
				<th>上级角色：</th>
				<td><input id="select1" name="pid" /></td>
			</tr>
			<tr>
				<td colspan="4">
					<textarea class="easyui-validatebox" name="description" style="width:98.4%;height:100px;"></textarea>
				</td>
			</tr>
		</table>
	</div>
</form>
