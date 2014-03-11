<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
	var form_url = siteUtil.basePath+"/sysmgr/menu/add.do" ;
	
	$(function() {
		var s1 = $("#select1").combotree({
			url : siteUtil.basePath+"/sysmgr/menu/treegrid.do",
			width:157, idFiled:'pid', textFiled:'name', editable: false,
			disabled: true, required:false, lines:true, autoShowPanel: false
	    });
		$("#select3").combobox({
			width: 157, valueField: 'label', textField: 'value', value: 'R',
			data: [{ label: 'R', value: '类别' },{ label: 'F', value: '菜单' },{ label: 'O', value: '操作' }],
			panelHeight:'auto', editable:false, autoShowPanel: true,
			onSelect: function(node){
				if("R" == node.label) {	//是菜单类别，无法设置父菜单
					s1.combotree({ value: '', disabled: true }) ;
				} else if("F" == node.label || "O" == node.label) {
					s1.combotree({ disabled: false, required:true, autoShowPanel: true }) ;
				}
			}
	    });
		$("#select4").combobox({
			width: 157, valueField: 'label', textField: 'value', value: 'Y',
			data: [{ label: 'Y', value: '启用' },{ label: 'N', value: '禁用' }],
			panelHeight:'auto', editable:false, autoShowPanel: true
	    });
		
		
		//编辑，加载表单数据
		if($('input[name=id]').val().length > 0) {
			form_url = siteUtil.basePath+"/sysmgr/menu/edit.do" ;
			$.post(siteUtil.basePath+"/sysmgr/menu/get.do", {id:$('input[name=id]').val()}, function(result) {
				if (result.id != undefined) {
					$('form').form('load', {
						'id' : result.id,
						'name' : result.name,
						'href' : result.href,
						'disused' : result.disused,
						'type' : result.type,
						'iconCls' : result.iconCls,
						'pid' : result.pid
					});
					if("R" == result.type) {	//是菜单类别，无法设置父菜单
						s1.combotree({ disabled: true, required:false }) ;
					} else {
						s1.combotree({ disabled: false, required:true, value: result.pid }) ;
					}
				}
			}, 'json');
		} else {
			s1.combotree({ disabled: true, required:false, autoShowPanel: true }) ;
		}
		$("#form input:visible")[0].focus();
	});
	
	//提交表单数据
	var submitNow = function($dialog, $tree) {
		
		$.post(form_url, $("#form").form("getData"), function(result) {
			if (result.status) {
				$tree.treegrid('reload') ;
				window.mainpage.refreshNavTab();
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
	<table id="tab_box">
		<tr>
			<th>菜单名称：</th>
			<td><input name="name" class="easyui-validatebox" type="text" data-options="required: true, prompt: '菜单名称'" /></td>
			<th>菜单类型：</th>
			<td><input id="select3" name="type" /></td>
			
		</tr>
		<tr>
			<th>菜单地址：</th>
			<td colspan="3"><input name="href" style="width:385px;" class="easyui-validatebox" type="text" data-options="required: false, prompt: '菜单地址'" /></td>
		</tr>
		<tr>
			<th>排序：</th>
			<td><input name="seq" class="easyui-numberspinner" type="text" data-options="min:1,value:1" style="width:157px" /></td>
			<th>菜单图标：</th>
			<td><input name="iconCls" class="easyui-comboicons" data-options="width: 157, autoShowPanel: false, multiple: false, size: '16'" /></td>
		</tr>
		<tr>
			<th>父菜单：</th>
			<td><input id="select1" name="pid" /></td>
			<th>是否启用：</th>
			<td><input id="select4" name="disused" /></td>
		</tr>
	</table>
	
</form>
