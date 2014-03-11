<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>EasyUI Administrator</title>
<%@ include file="/common/base/meta.jsp"%>
<%@ include file="/common/base/script.jsp"%>
<script type="text/javascript">
	//var action_url = siteUtil.basePath+"/sysmgr/filemanager/tree_fileroot.do?path=/file_root" ;
	$(function() {
		$("#t1").datagrid({
	        title: 'test datagrid',
	        width: 1200,
	        height: 600,
	        method: "get",
	        url: "datagrid-data.json", 
	        //url: action_url,
	        idField: 'ID',
	        remoteSort: false,
	        frozenColumns: [[
	            { field: 'ck', checkbox: true },
	            { field: 'ID', title: 'ID', width: 80, sortable: true }
	        ]],
	        columns: [[
	            { field: 'Code', title: '编号(Code)', width: 120, sortable: true },
	            { field: 'fileSize', title: '大小', width: 80, sortable: true },
	            { field: 'Name', title: '名称(Name)', width: 140, sortable: true },
	            { field: 'Age', title: '年龄(Age)', width: 120, sortable: true },
	            { field: 'Height', title: '身高(Height)', width: 140, sortable: true },
	            { field: 'Weight', title: '体重(Weight)', width: 140, sortable: true },
	            { field: 'CreateDate', title: '创建日期(CreateDate)', width: 180, sortable: true },
	            { field: 'undefined', title: '测试(不存在的字段)', width: 150 }
	        ]],
	        enableHeaderClickMenu: true,        //此属性开启表头列名称右侧那个箭头形状的鼠标左键点击菜单
	        enableHeaderContextMenu: true,      //此属性开启表头列名称右键点击菜单
	        enableRowContextMenu: false
		});
	}) ;
</script>
</head>

<body style="padding: 0px; margin: 0px;">
    
    <div id="t1"></div>
	
</body>
</html>