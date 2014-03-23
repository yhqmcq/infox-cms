<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>角色管理</title>
<%@ include file="/common/base/meta.jsp"%>
<%@ include file="/common/base/script.jsp"%>

<script type="text/javascript">
	var $dg ;
	var $tg ;
	$(function() {
		$dg = $("#t1").datagrid({
			url: siteUtil.basePath+"/sysmgr/roleAction/datagrid.do",
			title: '角色管理', method: "post", idField: 'id', fit: true, border: false,
			remoteSort: false, toolbar: '#buttonbar', striped:true, pagination: false, singleSelect: true,
			frozenColumns: [[
			    { field: 'ck', checkbox: true },
			    { field: 'id', title: 'ID', width: 70, sortable: true }
			]],
			columns: [[
			    { field: 'name', title: '名称', width: 180, sortable: true },
			    { field: 'showPermission', title: '关联', width: 40, align: 'center', formatter: function(value, row, index) {
				    return $.string.format('<span style="cursor: pointer;" onclick="getRolePermission(\'{0}\')"><img src="{1}" title="浏览"/></span>',row.id ,siteUtil.basePath+'/images/icons/view.png') ;
				}}
			]],
			enableHeaderClickMenu: false, enableHeaderContextMenu: true, enableRowContextMenu: true, selectOnRowContextMenu: true,       		
	    });
		
		$tg = $("#t2").treegrid({
			title: '菜单模块管理', width: 900, height: 400, method: "get",
			url: siteUtil.basePath+"/sysmgr/moduleAction/treegrid.do",
			idField: 'id', treeField: 'moduleName', fit: true, border: false,
			remoteSort: false, toolbar: '#buttonbar2', striped:true,singleSelect:false,
			frozenColumns: [[
			    { field: 'ck', checkbox: true },
			    { field: 'id', title: 'ID(id)', width: 80, sortable: true }
			]],
			columns: [[
			    { field: 'moduleName', title: '名称', width: 220, sortable: true },
			    { field: 'linkUrl', title: '链接地址', width: 250 }
			]],
			onClickRow:function(row){   
             	$tg.treegrid('cascadeCheck',{			//级联选择   
                    id:row.id, 							//节点ID   
                    deepCascade:true 					//深度级联   
               	});   
         	},
	    });
	});
	
	function savePermission() {
		var selections=$tg.treegrid('getSelections');
		var selectionRole=$dg.datagrid('getSelected');
		var checkedIds=[];
		$.each(selections,function(i,e){
			checkedIds.push(e.id);
		});
		if(selectionRole){
			$.ajax({
				url:siteUtil.basePath+"/sysmgr/roleAction/set_grant.do",
				data: "id="+selectionRole.id+"&moduleIds="+(checkedIds.length==0?"":checkedIds),
				success: function(result){
					result = $.parseJSON(result);
					if (result.status) {
						$.easyui.messager.show({ icon: "info", msg: result.msg });
					} else {
						$.easyui.messager.show({ icon: "info", msg: result.msg });
					}
				},
				error:function(){
					$.easyui.messager.show({ icon: "info", msg: result.msg });
				}
			});
		}else{
			$.easyui.messager.show({ icon: "info", msg: "请选择角色！" });
		}
	}
	
	function getRolePermission(roleid) {
		$.post(siteUtil.basePath+"/sysmgr/roleAction/getPermission.do", {
			id : roleid
		}, function(result) {
			$tg.datagrid('unselectAll');
			if(result.moduleIds) {
				var ids = result.moduleIds.split(",") ;
				$.each(ids,function(i,e){
					$tg.treegrid('select',e);
				});
			} else {
				$.easyui.messager.show({ icon: "info", msg: "该角色没有分配权限" });
			}
		}, 'JSON').error(function() {
		});
	}
	
	function form_edit(form) {
		var form_url = siteUtil.basePath+"/sysmgr/roleAction/role_form.do" ;
		if("E" == form) {
			var node = treeGrid.datagrid('getSelected');
			if (node) {
				form_url = siteUtil.basePath+"/sysmgr/roleAction/role_form.do?id="+node.id ;
			} else {
				$.easyui.messager.show({ icon: "info", msg: "请选择一条记录！" });
				return ;
			}
		}
		var $d = $.easyui.showDialog({
            title: "表单", href: form_url,
            iniframe: false, width: 350, height: 200, topMost: true,
            enableApplyButton: false, enableCloseButton: false,  enableSaveButton: false,
            buttons : [ 
              { text : '保存', iconCls : 'ext_save', handler : function() { $.easyui.parent.submitForm($d, $dg) ; } },
              { text : '关闭', iconCls : 'ext_cancel', handler : function() { $d.dialog('destroy'); } } 
           	]
        });
	}
	
	function del() {
		var node = $dg.datagrid('getSelected');
		if(node){
			$.messager.confirm("您确定要进行该操作？<br/>该删除操作会将子菜单一并删除。", function (c) { 
				if(c) {
					$.post(siteUtil.basePath+"/sysmgr/roleAction/delete.do", {id:node.id}, function(result) {
						if (result.status) {
							$dg.datagrid('reload') ;
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
	
		<div data-options="region: 'west', border: false, split:true,minWidth: 345, maxWidth: 345" style="width:345px;overflow: hidden;">
			<div id="t1">
				<div id="buttonbar">
					<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="ext_set1" plain="true" onclick="savePermission();">保存设置</a>
                    <a id="btn1" onClick="form_edit('A');" class="easyui-linkbutton" data-options="plain: true, iconCls: 'ext_add'">添加</a>
                    <a id="btn2" onClick="form_edit('E');" class="easyui-linkbutton" data-options="plain: true, iconCls: 'ext_edit'">编辑</a>
                    <a id="btn3" onClick="del();" class="easyui-linkbutton" data-options="plain: true, iconCls: 'ext_remove'">删除</a>
                    <a id="btn4" onclick="$dg.datagrid('reload');" class="easyui-linkbutton" data-options="plain: true, iconCls: 'ext_reload'">刷新</a>
                </div>
			</div>
		</div>
		
		<div data-options="region: 'center', border: false" style="overflow: hidden;">
			<div id="t2">
				<div id="buttonbar2">
                    <a id="btn4" onclick="$tg.treegrid('reload');" class="easyui-linkbutton" data-options="plain: true, iconCls: 'ext_reload'">刷新</a>
                </div>
			</div>
		</div>
		
	</div>	
</body>
</html>