<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
	var form_url = siteUtil.basePath+"/sysmgr/org/add.do" ;
	
	$(function() {
		$("#select1").combotree({
			url : siteUtil.basePath+"/sysmgr/org/treegrid.do",
			width:157, idFiled:'pid', textFiled:'fullname', editable: false,
			lines:true, autoShowPanel: true,
			onSelect:function(node){$("#pname").val(node.text);}
	    });
		
		//编辑，加载表单数据
		if($('input[name=id]').val().length > 0) {
			form_url = siteUtil.basePath+"/sysmgr/org/edit.do" ;
			$.post(siteUtil.basePath+"/sysmgr/org/get.do", {id:$('input[name=id]').val()}, function(result) {
				if (result.id != undefined) {
					$('form').form('load', {
						'id' : result.id,
						'fullname' : result.fullname,
						'code' : result.code,
						'ename' : result.ename,
						'sname' : result.sname,
						'tel' : result.tel,
						'fax' : result.fax,
						'description' : result.description,
						'pid' : result.pid,
						'pname' : result.pname
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
				<th>机构名称：</th>
				<td><input name="fullname" class="easyui-validatebox" type="text" data-options="required: true,prompt: '机构名称'" /></td>
				<th>机构代码：</th>
				<td><input name="code" class="easyui-validatebox" type="text" data-options="required: true,prompt: '机构代码'" /></td>
			</tr>
			<tr>
				<th>英文名称：</th>
				<td><input name="ename" class="easyui-validatebox" type="text" data-options="" /></td>
				<th>机构简称：</th>
				<td><input name="sname" class="easyui-validatebox" type="text" data-options="" /></td>
			</tr>
			<tr>
				<th>电话：</th>
				<td><input name="tel" class="easyui-validatebox" type="text" data-options="" /></td>
				<th>传真：</th>
				<td><input name="fax" class="easyui-validatebox" type="text" data-options="" /></td>
			</tr>
			<tr>
				<th>上级机构：</th>
				<td colspan="3"><input id="select1" name="pid" /><input name="pname" id="pname"  type="hidden"/></td>
			</tr>
			<tr>
				<td colspan="4">
					<textarea class="easyui-validatebox" name="description" style="width:98.4%;height:100px;"></textarea>
				</td>
			</tr>
		</table>
	</div>
</form>
