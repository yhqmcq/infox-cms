<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>数据备份管理</title>
<%@ include file="/common/base/meta.jsp"%>
<%@ include file="/common/base/script.jsp"%>

<script type="text/javascript">
	var dataGrid ;
	$(function() {
		dataGrid = $("#d1").datagrid({
			title: '数据备份管理',
			method: "post",
			url: siteUtil.basePath+"/sysmgr/data/datagrid.do",
			idField: 'table_name',
			fit: true,
			border: false,
			remoteSort: false,
			toolbar: '#buttonbar',
			rownumbers: true,
			striped:true,
			pagination: true,
			frozenColumns: [[
			    { field: 'ck', checkbox: true },
			]],
			onDblClickRow: function(rowIndex, rowData) {
				table_fields(rowData.table_name);
			},
			columns: [[
			    { field: 'table_name', title: '表名称(table_name)', width: 180, sortable: true },
			    { field: 'engine', title: '引擎(engine)', width: 150, sortable: true },
			    { field: 'table_rows', title: '行数据(table_rows)', width: 150, sortable: true },
			    { field: 'table_collation', title: '字符集(table_collation)', width: 180, sortable: true },
			    { field: 'create_time', title: '日期(date)', width: 140, sortable: true }
			]],
			enableHeaderClickMenu: true,        //此属性开启表头列名称右侧那个箭头形状的鼠标左键点击菜单
	        enableHeaderContextMenu: true,      //此属性开启表头列名称右键点击菜单
	        selectOnRowContextMenu: false,      //此属性开启当右键点击行时自动选择该行的功能
	        pagingMenu: { submenu: false }     	//开启行右键菜单的翻页功能，此属性可丰富配置，详情见 API 文档
	    });
	});
	
	
	function revert() {
		$.easyui.showDialog({
            title: "恢复数据",
            href: siteUtil.basePath+"/sysmgr/data/data_revert.do",
            iniframe: false,width: 700, height: 450,
            topMost: true,
            autoVCenter: true,
            autoHCenter: true,
            enableApplyButton: false,
            enableSaveButton: false
        });
	}
	
	function table_fields(tableName) {
		$.easyui.showDialog({
            title: "表字段",
            href: siteUtil.basePath+"/sysmgr/data/data_table_fields.do?tableName="+tableName,
            iniframe: false,width: 800, height: 550,
            topMost: true,
            autoVCenter: true,
            autoHCenter: true,
            enableApplyButton: false,
            enableSaveButton: false
        });
	}
	
	function backup() {
		var rows = dataGrid.datagrid('getChecked');
		var tableNames = [];
		if (rows.length > 0) {
			for ( var i = 0; i < rows.length; i++) {
				tableNames.push(rows[i].table_name);
			}
			$.easyui.showDialog({
	            title: "备份",
	            href: siteUtil.basePath+"/sysmgr/data/backup.do?tableNames="+tableNames.join(','),
	            iniframe: false,width: 300, height: 150,
	            closable : false,
	            topMost: true,
	            autoVCenter: true,
	            autoHCenter: true,
	            enableApplyButton: false,
	            enableSaveButton: false
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
                    <a id="btn2" onClick="backup();" class="easyui-linkbutton" data-options="plain: true, iconCls: 'ext_backup'">备份</a>
                    <a id="btn1" onClick="revert();" class="easyui-linkbutton" data-options="plain: true, iconCls: 'ext_revert'">恢复</a>
                    <a id="btn4" onclick="dataGrid.datagrid('reload');" class="easyui-linkbutton" data-options="plain: true, iconCls: 'ext_reload'">刷新</a>
                </div>
			</div>
			
		</div>
	</div>	
</body>
</html>