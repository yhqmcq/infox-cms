<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>用户登录历史</title>
<%@ include file="/common/base/meta.jsp"%>
<%@ include file="/common/base/script.jsp"%>

<script type="text/javascript">
	var dataGrid ;
	$(function() {
		dataGrid = $("#d1").datagrid({
			url: yhq.basePath+"/sysmgr/emponline/datagrid.do",
			title: '用户登录历史',
			method: "post",
			idField: 'id',
			pageSize : 100,
			pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500, 1000 ],
			fitColumns: true,
			fit: true,
			border: false,
			remoteSort: false,
			toolbar: '#buttonbar',
			striped:true,
			pagination: true,
			rownumbers: true,
			frozenColumns: [[
			    { field: 'ck', checkbox: true },
			]],
			columns: [[
			    { field: 'account', title: '账号(account)', width: 120, sortable: true },
			    { field: 'truename', title: '姓名(truename)', width: 150, sortable: true },
			    { field: 'opa', title: '操作', width: 50, sortable: true, formatter:function(value,row){
			    	if(undefined != row.type && "" != row.type && row.type == 1) {
			    		return "登录";
			    	} else {
			    		return "<font color='red'>注销</font>";
			    	}
			    }},
			    { field: 'ip', title: 'IP(ip)', width: 180, sortable: true },
			    { field: 'logindate', title: '日期(date)', width: 140, sortable: true }
			]],
			enableHeaderClickMenu: true,        //此属性开启表头列名称右侧那个箭头形状的鼠标左键点击菜单
	        enableHeaderContextMenu: true,      //此属性开启表头列名称右键点击菜单
	        selectOnRowContextMenu: false,      //此属性开启当右键点击行时自动选择该行的功能
	        pagingMenu: { submenu: false }     	//开启行右键菜单的翻页功能，此属性可丰富配置，详情见 API 文档
	    });
	});
	
	function del() {
		var rows = dataGrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			$.messager.confirm("您确定要进行该操作？", function (c) { 
				if(c) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					$.post(yhq.basePath+"/sysmgr/emponline/doNotNeedAuth_delete.do", {ids : ids.join(',')}, function(result) {
						if (result.status) {
							dataGrid.datagrid('clearSelections');dataGrid.datagrid('clearChecked');dataGrid.datagrid('reload') ;
							$.easyui.messager.show({ icon: "info", msg: "删除记录成功。" });
						} else {
							$.easyui.messager.show({ icon: "info", msg: "删除记录失败。" });
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
                    <a id="btn3" onClick="del();" class="easyui-linkbutton" data-options="plain: true, iconCls: 'ext_remove'">删除</a>
                    <a id="btn4" onclick="dataGrid.datagrid('reload');" class="easyui-linkbutton" data-options="plain: true, iconCls: 'ext_reload'">刷新</a>
                </div>
			</div>
			
		</div>
	</div>	
</body>
</html>