<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>菜单管理</title>
<%@ include file="/common/base/meta.jsp"%>
<%@ include file="/common/base/script.jsp"%>

<script type="text/javascript">
	var $tg ;
	$(function() {
		$tg = $("#t1").treegrid({
			title: '公司管理', method: "get",
			url: siteUtil.basePath+"/sysmgr/companyAction/treegrid.do",
			idField: 'id', treeField: 'name', fit: true, border: false,
			remoteSort: false, toolbar: '#buttonbar', striped:true,
			frozenColumns: [[
			    { field: 'ck', checkbox: true },
			    { field: 'id', title: 'ID', width: 80, sortable: true }
			]],
			columns: [[
			    { field: 'name', title: '公司名称', width: 180, sortable: true },
			    { field: 'created', title: '日期', width: 140, sortable: true }
			]],
			enableHeaderClickMenu: false,
			enableHeaderContextMenu: true,
			enableRowContextMenu: true
	    });
	});
	
	function form_edit(form) {
		var form_url = siteUtil.basePath+"/sysmgr/companyAction/company_form.do" ;
		if("E" == form) {
			var node = $tg.treegrid('getSelected');
			if (node) {
				form_url = siteUtil.basePath+"/sysmgr/companyAction/company_form.do?id="+node.id ;
			} else {
				$.easyui.messager.show({ icon: "info", msg: "请选择一条记录！" });
				return ;
			}
		}
		var $d = $.easyui.showDialog({
			href: form_url,
            title: "表单", iniframe: false, width: 650, height: 350, topMost: true,
            enableApplyButton: false, enableCloseButton: false,  enableSaveButton: false,
            buttons : [ 
              { text : '保存', iconCls : 'ext_save', handler : function() { $.easyui.parent.submitForm($d, $tg) ; } },
              { text : '关闭', iconCls : 'ext_cancel', handler : function() { $d.dialog('destroy'); } } 
           	]
        });
	}
	
	function del() {
		var node = $tg.treegrid('getSelected');
		if(node){
			$.messager.confirm("您确定要进行该操作？<br/>该删除操作会将子菜单一并删除。", function (c) { 
				if(c) {
					$.post(siteUtil.basePath+"/sysmgr/companyAction/delete.do", {id:node.id}, function(result) {
						if (result.status) {
							$tg.treegrid('reload') ;
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
		<div data-options="region: 'center', border: false" style="overflow: hidden;">
			
			<div id="t1">
				<div id="buttonbar">
                    <a id="btn1" onClick="form_edit('A');" class="easyui-linkbutton" data-options="plain: true, iconCls: 'ext_add'">创建</a>
                    <a id="btn2" onClick="form_edit('E');" class="easyui-linkbutton" data-options="plain: true, iconCls: 'ext_edit'">编辑</a>
                    <a id="btn3" onClick="del();" class="easyui-linkbutton" data-options="plain: true, iconCls: 'ext_remove'">删除</a>
                    <a id="btn4" onclick="$tg.treegrid('reload');" class="easyui-linkbutton" data-options="plain: true, iconCls: 'ext_reload'">刷新</a>
                </div>
			</div>
			
		</div>
	</div>	
</body>
</html>