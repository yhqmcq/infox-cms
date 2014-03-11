<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>用户授权</title>
<%@ include file="/common/base/meta.jsp"%>
<%@ include file="/common/base/script.jsp"%>

<script type="text/javascript">
	var dataGrid ;
	var treeGrid2 ;
	$(function() {
		dataGrid = $("#d1").datagrid({
			title: '用户授权',
			method: "get",
			url: siteUtil.basePath+"/sysmgr/employee/datagrid.do",
			idField: 'id',
			fit: true,
			border: false,
			remoteSort: false,
			singleSelect:false,
			toolbar: '#buttonbar',
			striped:true,
			frozenColumns: [[
			    { field: 'ck', checkbox: true },
			    { field: 'id', title: 'ID(id)', width: 80, sortable: true }
			]],
			columns: [[
			    { field: 'account', title: '账号(account)', width: 130, sortable: true },
			    { field: 'status', title: '状态(status)', width: 110, sortable: true, formatter:function(value,row){
			    	if(value == "Y"){return "<font color='green'>激活</font>";}else{return "<font color='red'>禁用</font>";}
			    }},
			    { field: 'truename', title: '姓名(truename)', width: 130, sortable: true },
			    { field: 'showPermission', title: '关联', width: 40, align: 'center', formatter: function(value, row, index) {
				    return $.string.format('<span style="cursor: pointer;" onclick="getRolePermission(\'{0}\')"><img src="{1}" title="浏览"/></span>',row.id ,siteUtil.basePath+'/images/icons/view.png') ;
				}}
			]]
	    });
		treeGrid2 = $("#t2").treegrid({
			title: '角色管理',
			method: "get",
			url: siteUtil.basePath+"/sysmgr/role/treegrid.do",
			idField: 'id',
			treeField: 'name',
			fitColumns: false,
			fit: true,
			border: false,
			remoteSort: false,
			singleSelect:false,
			toolbar: '#buttonbar2',
			striped:true,
			frozenColumns: [[
			    { field: 'ck', checkbox: true },
			    { field: 'id', title: 'ID(id)', width: 60, sortable: true }
			]],
			columns: [[
			    { field: 'name', title: '名称(name)', width: 170, sortable: true }
			]]
	    });
	});
	
	function savePermission() {
		var selections=treeGrid2.treegrid('getSelections');
		var selectionUsers=dataGrid.datagrid('getSelections');
		var checkedIds=[];
		$.each(selections,function(i,e){
			checkedIds.push(e.id);
		});
		var checkedRoleIds=[];
		$.each(selectionUsers,function(i,e){
			checkedRoleIds.push(e.id);
		});
		if(selectionUsers.length != 0 && selectionUsers != ""){
			$.ajax({
				url:siteUtil.basePath+"/sysmgr/employee/set_grant.do?d="+new Date().getTime(),
				data: "ids="+(checkedRoleIds.length==0?"":checkedRoleIds)+"&roleIds="+(checkedIds.length==0?"":checkedIds),
				success: function(result){
					result = $.parseJSON(result);
					if (result.status) {
						$.easyui.messager.show({ icon: "info", msg: "角色分配成功！" });
					} else {
						$.easyui.messager.show({ icon: "info", msg: "角色分配失败！" });
					}
				},
				error:function(){}
			});
		}else{
			$.easyui.messager.show({ icon: "info", msg: "请选择用户！" });
		}
	}
	
	function getRolePermission(roleid) {
		dataGrid.datagrid('unselectAll');
		$.post(siteUtil.basePath+"/sysmgr/employee/getPermission.do", {
			id : roleid
		}, function(result) {
			treeGrid2.treegrid('unselectAll');
			if(result.roleIds) {
				var ids = result.roleIds.split(",") ;
				$.each(ids,function(i,e){
					treeGrid2.treegrid('select',e);
				});
			} else {
				$.easyui.messager.show({ icon: "info", msg: "该角色没有分配权限" });
			}
		}, 'JSON').error(function() {
		});
	}
	
</script>

</head>

<body style="padding: 0px; margin: 0px;">
	<div class="easyui-layout" data-options="fit: true">
	
		<div data-options="region: 'west', border: false, split:true" style="width:530px;overflow: hidden;">
			<div id="d1">
				<div id="buttonbar">
					<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="ext_set1" plain="true" onclick="savePermission();">保存设置</a>
                    <a id="btn4" onclick="dataGrid.datagrid('reload');" class="easyui-linkbutton" data-options="plain: true, iconCls: 'ext_reload'">刷新</a>
                </div>
			</div>
		</div>
		
		<div data-options="region: 'center', border: false" style="overflow: hidden;">
			<div id="t2">
				<div id="buttonbar2">
                    <a id="btn4" onclick="treeGrid2.treegrid('reload');" class="easyui-linkbutton" data-options="plain: true, iconCls: 'ext_reload'">刷新</a>
                </div>
			</div>
		</div>
		
	</div>	
</body>
</html>