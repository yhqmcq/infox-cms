<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
	var dataGrid ;
	$(function() {
		dataGrid = $("#d1").datagrid({
			method: "post",
			url: yhq.basePath+"/sysmgr/data/listfields.do?tableName=${tableName}",
			idField: 'id',
			fit: true,
			border: false,
			remoteSort: false,
			striped:true,
			frozenColumns: [[
			    { field: 'ck', checkbox: true },
			    { field: 'name', title: '字段名(name)', width: 150, sortable: true }
			]],
			columns: [[
			    { field: 'fieldType', title: '字段类型(FieldType)', width: 150, sortable: true },
			    { field: 'fieldProperty', title: '字段属性(FieldProperty)', width: 150, sortable: true },
			    { field: 'fieldDefault', title: '默认值(FieldDefault)', width: 150, sortable: true },
			    { field: 'nullable', title: '允许为空(Nullable)', width: 150, sortable: true },
			    { field: 'extra', title: '附件属性(Extra)', width: 150, sortable: true }
			]],
			enableHeaderClickMenu: true,        //此属性开启表头列名称右侧那个箭头形状的鼠标左键点击菜单
	        enableHeaderContextMenu: true,      //此属性开启表头列名称右键点击菜单
	        selectOnRowContextMenu: false,      //此属性开启当右键点击行时自动选择该行的功能
	        pagingMenu: { submenu: false }     	//开启行右键菜单的翻页功能，此属性可丰富配置，详情见 API 文档
	    });
	});
</script>

<div id="d1"></div>
