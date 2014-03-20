<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
	var form_url = siteUtil.basePath+"/sysmgr/companyAction/add.do" ;
	
	$(function() {
		$("#select1").combotree({
			url : siteUtil.basePath+"/sysmgr/companyAction/treegrid.do",
			width:157, idFiled:'pid', textFiled:'name', editable: false,
			lines:true, autoShowPanel: true
	    });
		
		//编辑，加载表单数据
		if($('input[name=id]').val().length > 0) {
			form_url = siteUtil.basePath+"/sysmgr/companyAction/edit.do" ;
			$.post(siteUtil.basePath+"/sysmgr/companyAction/get.do", {id:$('input[name=id]').val()}, function(result) {
				if (result.id != undefined) {
					$('form').form('load', {
						'id' : result.id,
						'name' : result.name,
						'pid' : result.pid
					});
				}
			}, 'json');
		}
		$("#form input:visible")[0].focus();
	});
	
	//提交表单数据
	var submitNow = function($d, $tg) {
		$.post(form_url, $("#form").form("getData"), function(result) {
			if (result.status) {
				$tg.treegrid('reload') ;
				$.easyui.messager.show({ icon: "info", msg: result.msg });
				$d.dialog("close") ;
			} else {
				$.easyui.messager.show({ icon: "info", msg: result.msg });
			}
		}, 'json');
	};
	
	//验证表单
	var submitForm = function($d, $tg) {
		if($('#form').form('validate')) {
			submitNow($d, $tg) ;
		}
	};
	
	
</script>

<form id="form" class="easyui-form">
	<input type="hidden" name="id" value="${id}" />
	<div class="form_base">
		<table>
			<tr>
				<th>公司名称：</th>
				<td><input name="name" class="easyui-validatebox" type="text" data-options="required: true,prompt: '公司名称'" /></td>
				<th>公司代码：</th>
				<td><input name="code" class="easyui-validatebox" type="text" data-options="required: true,prompt: '公司代码'" /></td>
			</tr>
			<tr>
				<th>英文名称：</th>
				<td><input name="ename" class="easyui-validatebox" type="text" data-options="" /></td>
				<th>公司简称：</th>
				<td><input name="sname" class="easyui-validatebox" type="text" data-options="" /></td>
			</tr>
			<tr>
				<th>电话：</th>
				<td><input name="tel" class="easyui-validatebox" type="text" data-options="" /></td>
				<th>传真：</th>
				<td><input name="fax" class="easyui-validatebox" type="text" data-options="" /></td>
			</tr>
			<tr>
				<th>上级公司：</th>
				<td colspan="3"><input id="select1" name="pid" /></td>
			</tr>
			<tr>
				<td colspan="4">
					<textarea class="easyui-validatebox" name="description" style="width:98.4%;height:100px;"></textarea>
				</td>
			</tr>
		</table>
	</div>
</form>
