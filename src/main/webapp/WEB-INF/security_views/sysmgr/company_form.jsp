<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
	var form_url = siteUtil.basePath+"/sysmgr/companyAction/add.do" ;
	var s1 ;
	$(function() {
		s1 = $("#select1").combotree({
			url : siteUtil.basePath+"/sysmgr/companyAction/treegrid.do",
			width:157, idFiled:'pid', textFiled:'name', editable: false,
			lines:true, autoShowPanel: true
	    });
		
		$("#select3").combobox({ 
			width: 157, valueField: 'label', textField: 'value', value: 'C', required: false,
			data: [{ label: 'C', value: '公司' },{ label: 'D', value: '部门' }],
			panelHeight:'auto', editable:false, autoShowPanel: true,
			onSelect: function(node){
				if("D" == node.label) {	//是部门，则必填
					s1.combotree({ required:true, autoShowPanel: true, value: s1.combotree("getValue") }) ;
				} else {
					s1.combotree({ required:false }) ;
				}
			}
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
				<th>类型：</th>
				<td><input id="select3" name="type" /></td>
				<th>上级：</th>
				<td><input id="select1" name="pid" /><a onClick="s1.combotree('setValue','');" class="easyui-linkbutton" data-options="plain: true, iconCls: 'ext_remove'"></a></td>
			</tr>
			<tr>
				<th>名称：</th>
				<td><input name="name" class="easyui-validatebox" type="text" data-options="required: true,prompt: '名称'" /></td>
				<th>简称：</th>
				<td><input name="sname" class="easyui-validatebox" type="text" data-options="prompt: '公司或部门简称'" /></td>
			</tr>
			<tr>
				<th>电话：</th>
				<td><input name="tel" class="easyui-validatebox" type="text" data-options="" /></td>
				<th>传真：</th>
				<td><input name="fax" class="easyui-validatebox" type="text" data-options="" /></td>
			</tr>
		</table>
	</div>
</form>
