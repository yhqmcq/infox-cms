<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>角色管理</title>
<%@ include file="/common/base/meta.jsp"%>
<%@ include file="/common/base/script.jsp"%>

<script type="text/javascript">
	var treeGrid ;
	var treeGrid2 ;
	$(function() {
		treeGrid = $("#t1").treegrid({
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
			toolbar: '#buttonbar',
			striped:true,
			frozenColumns: [[
			    { field: 'ck', checkbox: true },
			    { field: 'id', title: 'ID(id)', width: 70, sortable: true }
			]],
			columns: [[
			    { field: 'name', title: '名称(name)', width: 180, sortable: true },
			    { field: 'showPermission', title: '关联', width: 40, align: 'center', formatter: function(value, row, index) {
				    return $.string.format('<span style="cursor: pointer;" onclick="getRolePermission(\'{0}\')"><img src="{1}" title="浏览"/></span>',row.id ,siteUtil.basePath+'/images/icons/view.png') ;
				}}
			]],
			enableHeaderClickMenu: false,
			enableHeaderContextMenu: true,
			enableRowContextMenu: true,
			selectOnRowContextMenu: true,       		//此属性开启当右键点击行时自动选择该行的功能
	    });
		treeGrid2 = $("#t2").treegrid({
			title: '导航菜单资源',
			width: 900,
			height: 400,
			method: "get",
			url: siteUtil.basePath+"/sysmgr/menu/treegrid.do",
			idField: 'id',
			treeField: 'name',
			fit: true,
			border: false,
			remoteSort: false,
			toolbar: '#buttonbar2',
			striped:true,
			singleSelect:false,
			onClickRow:function(row){   
             	treeGrid2.treegrid('cascadeCheck',{		//级联选择   
                    id:row.id, 							//节点ID   
                    deepCascade:true 					//深度级联   
               	});   
         	},
			frozenColumns: [[
			    { field: 'ck', checkbox: true },
			    { field: 'id', title: 'ID(id)', width: 80, sortable: true }
			]],
			columns: [[
			    { field: 'name', title: '名称(name)', width: 220, sortable: true },
			    { field: 'seq', title: '排序(seq)', width: 80, sortable: true },
			    { field: 'type', title: '类型(type)', width: 120, sortable: true, formatter:function(value,row){
			    	if(value == "R"){return "类别";}else if(value == "F"){return "<font color='green'>菜单</font>";}else{return "<font color='red'>操作</font>";}
			    }},
			    { field: 'isused', title: '是否禁用(isused)', width: 120, sortable: true, formatter:function(value,row){
			    	if(value == "Y"){return "<font color='green'>启用</font>";}else{return "<font color='red'>禁用</font>";}
			    }},
			    { field: 'href', title: '链接地址(href)', width: 250 },
			    { field: 'created', title: '日期(date)', width: 140, sortable: true }
			]]
	    });
	});
	
	function savePermission() {
		var selections=treeGrid2.treegrid('getSelections');
		var selectionRole=treeGrid.treegrid('getSelected');
		var checkedIds=[];
		$.each(selections,function(i,e){
			checkedIds.push(e.id);
		});
		if(selectionRole){
			$.ajax({
				url:siteUtil.basePath+"/sysmgr/role/set_grant.do",
				data: "id="+selectionRole.id+"&menuIds="+(checkedIds.length==0?"":checkedIds),
				success: function(result){
					result = $.parseJSON(result);
					if (result.status) {
						$.easyui.messager.show({ icon: "info", msg: "角色分配成功！" });
					} else {
						$.easyui.messager.show({ icon: "info", msg: "角色分配失败！" });
					}
				},
				error:function(){
					$.easyui.messager.show({ icon: "info", msg: "分配失败！" });
				}
			});
		}else{
			$.easyui.messager.show({ icon: "info", msg: "请选择角色！" });
		}
	}
	
	function getRolePermission(roleid) {
		$.post(siteUtil.basePath+"/sysmgr/role/getPermission.do", {
			id : roleid
		}, function(result) {
			treeGrid2.treegrid('unselectAll');
			if(result.menuIds) {
				var ids = result.menuIds.split(",") ;
				$.each(ids,function(i,e){
					treeGrid2.treegrid('select',e);
				});
			} else {
				$.easyui.messager.show({ icon: "info", msg: "该角色没有分配权限" });
			}
			
		}, 'JSON').error(function() {
		});
	}
	
	function form_edit(form) {
		var form_url = siteUtil.basePath+"/sysmgr/role/role_form.do" ;
		if("E" == form) {
			var node = treeGrid.treegrid('getSelected');
			if (node) {
				form_url = siteUtil.basePath+"/sysmgr/role/role_form.do?id="+node.id ;
			} else {
				$.easyui.messager.show({ icon: "info", msg: "请选择一条记录！" });
				return ;
			}
		}
		var dialog = $.easyui.showDialog({
            title: "表单",
            href: form_url,
            iniframe: false,
            width: 550, height: 230,
            topMost: true,
            autoVCenter: true,
            autoHCenter: true,
            enableApplyButton: false,
            saveButtonIconCls: "ext_save",
            onSave: function() {
            	return $.easyui.parent.submitForm(dialog, treeGrid);
            }
        });
	}
	
	function del() {
		var node = treeGrid.treegrid('getSelected');
		if(node){
			$.messager.confirm("您确定要进行该操作？<br/>该删除操作会将子菜单一并删除。", function (c) { 
				if(c) {
					$.post(siteUtil.basePath+"/sysmgr/role/delete.do", {id:node.id}, function(result) {
						if (result.status) {
							treeGrid.treegrid('reload') ;
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
	
		<div data-options="region: 'west', border: false, split:true,minWidth: 345, maxWidth: 345" style="width:345px;overflow: hidden;">
			<div id="t1">
				<div id="buttonbar">
					<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="ext_set1" plain="true" onclick="savePermission();">保存设置</a>
                    <a id="btn1" onClick="form_edit('A');" class="easyui-linkbutton" data-options="plain: true, iconCls: 'ext_add'">添加</a>
                    <a id="btn2" onClick="form_edit('E');" class="easyui-linkbutton" data-options="plain: true, iconCls: 'ext_edit'">编辑</a>
                    <a id="btn3" onClick="del();" class="easyui-linkbutton" data-options="plain: true, iconCls: 'ext_remove'">删除</a>
                    <a id="btn4" onclick="treeGrid.treegrid('reload');" class="easyui-linkbutton" data-options="plain: true, iconCls: 'ext_reload'">刷新</a>
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