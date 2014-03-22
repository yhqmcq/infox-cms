<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>用户授权</title>
<%@ include file="/common/base/meta.jsp"%>
<%@ include file="/common/base/script.jsp"%>

<script type="text/javascript">
	var $dg ;
	var $dg2 ;
	$(function() {
		$dg = $("#d1").datagrid({
			url: siteUtil.basePath+"/sysmgr/userAction/datagrid.do",
			title: '用户授权', method: "post", idField: 'id', fit: true, border: false,
			remoteSort: false, toolbar: '#buttonbar', striped:true, pagination: true, singleSelect: true,
			frozenColumns: [[
			    { field: 'ck', checkbox: true },
			    { field: 'id', title: 'ID', width: 80, sortable: true }
			]],
			columns: [[
			    { field: 'account', title: '账号', width: 100, sortable: true },
			    { field: 'truename', title: '姓名', width: 100, sortable: true },
			    { field: 'showPermission', title: '关联', width: 40, align: 'center', formatter: function(value, row, index) {
				    return $.string.format('<span style="cursor: pointer;" onclick="getRolePermission(\'{0}\')"><img src="{1}" title="浏览"/></span>',row.id ,siteUtil.basePath+'/images/icons/view.png') ;
				}}
			]],
			enableHeaderClickMenu: true, enableHeaderContextMenu: true, selectOnRowContextMenu: false, pagingMenu: { submenu: false }
	    });
		$dg2 = $("#t2").datagrid({
			title: '角色管理', method: "get",
			url: siteUtil.basePath+"/sysmgr/roleAction/datagrid.do",
			idField: 'id', treeField: 'name', fitColumns: false, fit: true,
			border: false, remoteSort: false, singleSelect:false, toolbar: '#buttonbar2', striped:true,
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
		var users=$dg.datagrid('getSelections');
		var roles=$dg2.datagrid('getSelections');
		
		
		var userIds=[];
		$.each(users,function(i,e){
			userIds.push(e.id);
		});
		
		var roleIds=[];
		$.each(roles,function(i,e){
			roleIds.push(e.id);
		});
		if(users.length != 0 && users != ""){
			$.ajax({
				url:siteUtil.basePath+"/sysmgr/userAction/set_permit.do?d="+new Date().getTime(),
				data: "ids="+userIds+"&roleIds="+(roleIds.length==0?"":roleIds),
				success: function(result){
					result = $.parseJSON(result);
					if (result.status) {
						$.easyui.messager.show({ icon: "info", msg: result.msg });
					} else {
						$.easyui.messager.show({ icon: "info", msg: result.msg });
					}
				},
				error:function(){}
			});
		}else{
			$.easyui.messager.show({ icon: "info", msg: "请选择用户！" });
		}
	}
	
	function getRolePermission(userid) {
		$.post(siteUtil.basePath+"/sysmgr/userAction/getPermission.do", {
			id : userid
		}, function(result) {
			$dg2.datagrid('unselectAll');
			if(result.roleIds) {
				var ids = result.roleIds.split(",") ;
				$.each(ids,function(i,e){
					$dg2.datagrid('selectRecord',e);
				});
			} else {
				$.easyui.messager.show({ icon: "info", msg: "该用户未分配角色!" });
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
                    <a id="btn4" onclick="$dg.datagrid('reload');" class="easyui-linkbutton" data-options="plain: true, iconCls: 'ext_reload'">刷新</a>
                </div>
			</div>
		</div>
		
		<div data-options="region: 'center', border: false" style="overflow: hidden;">
			<div id="t2">
				<div id="buttonbar2">
                    <a id="btn4" onclick="$dg2.treegrid('reload');" class="easyui-linkbutton" data-options="plain: true, iconCls: 'ext_reload'">刷新</a>
                </div>
			</div>
		</div>
		
	</div>	
</body>
</html>