<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>定时作业管理</title>
<%@ include file="/common/base/meta.jsp"%>
<%@ include file="/common/base/script.jsp"%>

<script type="text/javascript">
	var $dg ;
	$(function() {
		$dg = $("#d1").datagrid({
			url: siteUtil.basePath+"/sysmgr/task/datagrid.do",
			title: '定时作业管理', method: "post", idField: 'id', fit: true, border: false,
			remoteSort: false, toolbar: '#buttonbar', striped:true, pagination: true,
			frozenColumns: [[
			    { field: 'ck', checkbox: true },
			    { field: 'id', title: 'ID', width: 80, sortable: true }
			]],
			columns: [[
			    { field: 'task_type_name', title: '任务类型', width: 200 },
			    { field: 'task_name', title: '任务名称', width: 200 },
			    { field: 'task_enable', title: '状态',align:'center', width: 120, sortable: true, formatter:function(value,row){
			    	if(value == "Y"){return "<font color='green'>运行中</font>";}else{return "<font color='red'>已停止</font>";}
			    }},
			    { field: 'operate', title: '操作', width: 150, formatter:function(value,row,index){
			    	var opa = $.string.format("<a href='javascript:;' onclick='engine(\"{0}\")'>{1}</a>", index, (row.task_enable=="N"?"启动":"停止"));
			    	return opa ;
			    }},
			    { field: 'cron_expression', title: '表达式', width: 250 },
			    { field: 'create_name', title: '创建者', width: 150 },
			    { field: 'created', title: '日期', width: 140, sortable: true }
			]],
			enableHeaderClickMenu: true, enableHeaderContextMenu: true, selectOnRowContextMenu: false, pagingMenu: { submenu: false }
	    });
	});
	
	function engine(index) {
		var o = {} ; o=$dg.datagrid("getRowData", index); o["task_enable"]=(o.task_enable=="Y"?"N":"Y") ;
		$.post(siteUtil.basePath+"/sysmgr/task/edit.do?d"+new Date().getTime(), o, function(result) {
			if (result.status) {
				$dg.datagrid('clearSelections');$dg.datagrid('clearChecked');$dg.datagrid('reload') ;
				if(o.task_enable == "Y") {
					$.easyui.messager.show({ icon: "info", msg: "任务已重新启动。" });
				} else {
					$.easyui.messager.show({ icon: "info", msg: "任务已停止。" });
				}
			} else {
				$.easyui.messager.show({ icon: "info", msg: "编辑失败。" });
			}
		}, 'json');
	}
	
	function form_edit(form) {
		var form_url = siteUtil.basePath+"/sysmgr/task/task_form.do" ;
		if("E" == form) {
			var node = $dg.datagrid('getSelected');
			if (node) {
				form_url = siteUtil.basePath+"/sysmgr/task/task_form.do?id="+node.id ;
			} else {
				$.easyui.messager.show({ icon: "info", msg: "请选择一条记录！" });
				return ;
			}
		}
		var $d = $.easyui.showDialog({
            title: "表单",
            href: form_url, iniframe: false,  width: 875, height: 530,topMost: true,
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
					$.post(siteUtil.basePath+"/sysmgr/task/delete.do?d"+new Date().getTime(), {ids : ids.join(',')}, function(result) {
						if (result.status) {
							$dg.datagrid('clearSelections');$dg.datagrid('clearChecked');$dg.datagrid('reload') ;
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