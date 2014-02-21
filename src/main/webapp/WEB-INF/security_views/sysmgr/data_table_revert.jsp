<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
	var dataGrid1 ;
	$(function() {
		dataGrid1 = $("#d1").datagrid({
			method: "post",
			url: yhq.basePath+"/sysmgr/data/databases.do?",
			idField: 'database',
			fit: true,
			border: false,
			remoteSort: false,
			fitColumn: true,
			singleSelect: true,
			striped:true,
			frozenColumns: [[
			    { field: 'ck', checkbox: true }
			]],
			columns: [[
			    { field: 'database', title: '数据库名称(database)', width: 180, sortable: true }
			]],
			onLoadSuccess: function() {
				$("#d1").datagrid('selectRecord','${defaultCatalog}');
			},
			enableHeaderClickMenu: true,        //此属性开启表头列名称右侧那个箭头形状的鼠标左键点击菜单
	        enableHeaderContextMenu: true,      //此属性开启表头列名称右键点击菜单
	        selectOnRowContextMenu: false,      //此属性开启当右键点击行时自动选择该行的功能
	        pagingMenu: { submenu: false }     	//开启行右键菜单的翻页功能，此属性可丰富配置，详情见 API 文档
	    });
		
		dataGrid2 = $("#d2").datagrid({
			method: "post",
			url: yhq.basePath+"/sysmgr/data/backupFiles.do?",
			idField: 'fileName',
			fit: true,
			border: false,
			remoteSort: false,
			fitColumn: true,
			singleSelect: false,
			striped:true,
			toolbar: '#revertBar',
			frozenColumns: [[
			    { field: 'ck', checkbox: true }
			]],
			columns: [[
			    { field: 'fileName', title: '文件名称(fileName)', width: 160, sortable: true },
			    { field: 'fileSize', title: '文件大小(fileSize)', width: 150, sortable: true }
			]],
			enableHeaderClickMenu: true,        //此属性开启表头列名称右侧那个箭头形状的鼠标左键点击菜单
	        enableHeaderContextMenu: true,      //此属性开启表头列名称右键点击菜单
	        selectOnRowContextMenu: false,      //此属性开启当右键点击行时自动选择该行的功能
	        pagingMenu: { submenu: false }     	//开启行右键菜单的翻页功能，此属性可丰富配置，详情见 API 文档
	    });
	});
	
	function revertDB() {
		var rows1 = dataGrid1.datagrid('getChecked');
		var rows2 = dataGrid2.datagrid('getChecked');
		if(rows1.length > 0 && rows2.length > 0) {
			$.messager.confirm("您确定要进行该操作？", function (c) { 
				if(c) {
					$.post(yhq.basePath+"/sysmgr/data/revert.do", {database: rows1[0].database, backupFileName: rows2[0].fileName}, function(result) {
						if (result.status) {
							$.easyui.messager.show({ icon: "info", msg: "数据库恢复成功。" });
						} else {
							$.easyui.messager.show({ icon: "info", msg: "数据库恢复失败。" });
						}
					}, 'json');
				}
			});
		} else {
			$.easyui.messager.show({ icon: "info", msg: "请选择要恢复的数据库和备份文件！" });
		}
	}
	
	function delbackup() {
		var rows = dataGrid2.datagrid('getChecked');
		var fileNames = [];
		if(rows.length > 0) {
			$.messager.confirm("您确定要进行该操作？", function (c) { 
				if(c) {
					for ( var i = 0; i < rows.length; i++) {
						fileNames.push(rows[i].fileName);
					}
					$.post(yhq.basePath+"/sysmgr/data/backup_del.do", {backupFileName: fileNames.join(',')}, function(result) {
						if (result.status) {
							dataGrid2.datagrid('reload');
							$.easyui.messager.show({ icon: "info", msg: "删除备份文件成功。" });
						} else {
							$.easyui.messager.show({ icon: "info", msg: "删除备份文件失败。" });
						}
					}, 'json');
				}
			});
		} else {
			$.easyui.messager.show({ icon: "info", msg: "请选择要恢复的数据库和备份文件！" });
		}
	}
	
	function downloadFile() {
		var rows2 = dataGrid2.datagrid('getChecked');
		if(rows2.length > 0) {
			window.location.href=yhq.basePath+"/sysmgr/data/downBackUpFile.do?backupFileName="+rows2[0].fileName;
		} else {
			$.easyui.messager.show({ icon: "info", msg: "请选择要下载的备份文件!" });
		}
	}
	
</script>

<div class="easyui-layout" data-options="fit: true">
	<div data-options="title:'数据库 [${defaultCatalog}]', region: 'center', border: false, iconCls: 'ext_db'" style="overflow: hidden;">
		<div id="d1"></div>
	</div>
	
	<div data-options="title:'SQL备份文件', region: 'east', border: false, split:true, iconCls: 'ext_sql'" style="width:460px;overflow: hidden;">
		<div id="d2">
			<div id="revertBar">
				<a id="btn3" onClick="revertDB();" class="easyui-linkbutton" data-options="plain: true, iconCls: 'ext_revert'">恢复</a>
				<a id="btn3" onClick="downloadFile();" class="easyui-linkbutton" data-options="plain: true, iconCls: 'ext_dw'">下载备份文件</a>
				<a id="btn3" onClick="delbackup();" class="easyui-linkbutton" data-options="plain: true, iconCls: 'ext_remove'">删除备份文件</a>
			</div>
		</div>
	</div>
</div>
