<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
	var form_url = siteUtil.basePath+"/sysmgr/moduleAction/add.do" ;
	
	$(function() {
		var s1 = $("#select1").combotree({
			url : siteUtil.basePath+"/sysmgr/moduleAction/treegrid.do",
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
			form_url = siteUtil.basePath+"/sysmgr/moduleAction/edit.do" ;
			$.post(siteUtil.basePath+"/sysmgr/moduleAction/get.do", {id:$('input[name=id]').val()}, function(result) {
				if (result.id != undefined) {
					$('form').form('load', {
						'id' : result.id,
						'moduleName' : result.moduleName,
						'linkUrl' : result.linkUrl,
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
	var submitNow = function($d, $tg) {
		$.post(form_url, $("#form").form("getData"), function(result) {
			if (result.status) {
				$tg.treegrid('reload') ;
				window.mainpage.refreshNavTab();
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
			submitNow($d, $tg) ; ;
		}
	};
	
	
</script>

<form id="form" class="easyui-form">
	<input type="hidden" name="id" value="${id}" />
	<div class="form_base">
		<table>
			<tr>
				<th>名称：</th>
				<td><input name="moduleName" class="easyui-validatebox" type="text" data-options="required: true, prompt: '菜单名称'" /></td>
				<th>类型：</th>
				<td><input id="select3" name="type" /></td>
				
			</tr>
			<tr>
				<th>URL地址：</th>
				<td colspan="3"><input name="linkUrl" style="width:475px;" class="easyui-validatebox" type="text" data-options="required: false, prompt: '菜单地址'" /></td>
			</tr>
			<tr>
				<th>排序：</th>
				<td><input name="seq" class="easyui-numberspinner" type="text" data-options="min:1,value:1" style="width:157px" /></td>
				<th>图标：</th>
				<td><input name="iconCls" class="easyui-comboicons" data-options="width: 157, autoShowPanel: false, multiple: false, size: '16'" /></td>
			</tr>
			<tr>
				<th>父模块：</th>
				<td><input id="select1" name="pid" /></td>
				<th>是否启用：</th>
				<td><input id="select4" name="disused" /></td>
			</tr>
		</table>
	</div>
</form>
