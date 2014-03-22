<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>用户管理</title>
<%@ include file="/common/base/meta.jsp"%>
<%@ include file="/common/base/script.jsp"%>

<script type="text/javascript">
	var $dg ;
	$(function() {
		$dg = $("#d1").datagrid({
			url: siteUtil.basePath+"/sysmgr/userAction/datagrid.do",
			title: '用户管理', method: "post", idField: 'id', fit: true, border: false,
			remoteSort: false, toolbar: '#buttonbar', striped:true, pagination: true, singleSelect: true,
			frozenColumns: [[
			    { field: 'ck', checkbox: true },
			    { field: 'id', title: 'ID', width: 80, sortable: true }
			]],
			columns: [[
			    { field: 'account', title: '账号', width: 100, sortable: true },
			    { field: 'truename', title: '姓名', width: 100, sortable: true },
			    { field: 'status', title: '状态', width: 60, sortable: true, formatter:function(value,row){
			    	if(value == "0"){return "<font color='green'>激活</font>";}else{return "<font color='red'>禁用</font>";}
			    }},
			    { field: 'sex', title: '性别', width:60, sortable: true, formatter:function(value,row){
			    	if(value == "male"){return "男";}else{return "女";}
			    }},
			    { field: 'email', title: '邮箱地址', width: 180, sortable: true },
			    { field: 'created', title: '日期', width: 140, sortable: true }
			]],
			enableHeaderClickMenu: true, enableHeaderContextMenu: true, selectOnRowContextMenu: false, pagingMenu: { submenu: false }
	    });
	});
	
	function form_edit(form) {
		var form_url = siteUtil.basePath+"/sysmgr/userAction/user_form.do" ;
		if("E" == form) {
			var node = $dg.datagrid('getSelected');
			if (node) {
				form_url = siteUtil.basePath+"/sysmgr/userAction/user_form.do?id="+node.id ;
			} else {
				$.easyui.messager.show({ icon: "info", msg: "请选择一条记录！" });
				return ;
			}
		}
		var $d = $.easyui.showDialog({
			href: form_url,
            title: "表单", iniframe: false, width: 650, height: 350, topMost: true,
            enableApplyButton: false, enableCloseButton: false,  enableSaveButton: false,
            buttons : [ 
              { text : '保存', iconCls : 'ext_save', handler : function() { $.easyui.parent.submitForm($d, $dg) ; } },
              { text : '关闭', iconCls : 'ext_cancel', handler : function() { $d.dialog('destroy'); } } 
           	]
        });
	}
	
	function del() {
		var rows = $dg.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			$.messager.confirm("您确定要进行该操作？", function (c) { 
				if(c) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					$.post(siteUtil.basePath+"/sysmgr/userAction/delete.do", {ids : ids.join(',')}, function(result) {
						if (result.status) {
							$dg.datagrid('clearSelections');$dg.datagrid('clearChecked');$dg.datagrid('reload') ;
							$.easyui.messager.show({ icon: "info", msg: result.msg });
						} else {
							$.easyui.messager.show({ icon: "info", msg: result.msg });
						}
					}, 'json');
				}
			});
		} else {
			$.easyui.messager.show({ icon: "info", msg: "请选择一条记录！" });
		}
	}
	
</script>

</head>

<body style="padding: 0px; margin: 0px;">
	<div class="easyui-layout" data-options="fit: true">
		<div data-options="region: 'center', border: false" style="overflow: hidden;">
			<div id="d1">
				<div id="buttonbar">
                    <a id="btn1" onClick="form_edit('A');" class="easyui-linkbutton" data-options="plain: true, iconCls: 'ext_add'">添加</a>
                    <a id="btn2" onClick="form_edit('E');" class="easyui-linkbutton" data-options="plain: true, iconCls: 'ext_edit'">编辑</a>
                    <a id="btn3" onClick="del();" class="easyui-linkbutton" data-options="plain: true, iconCls: 'ext_remove'">删除</a>
                    <a id="btn4" onclick="$dg.datagrid('reload');" class="easyui-linkbutton" data-options="plain: true, iconCls: 'ext_reload'">刷新</a>
                </div>
			</div>
		</div>
	</div>	
</body>
</html>