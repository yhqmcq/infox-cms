<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>菜单管理</title>
<%@ include file="/common/base/meta.jsp"%>
<%@ include file="/common/base/script.jsp"%>

<script type="text/javascript">
	var treeGrid ;
	$(function() {
		treeGrid = $("#t1").treegrid({
			title: '菜单管理',
			width: 900,
			height: 400,
			method: "get",
			url: siteUtil.basePath+"/sysmgr/menu/treegrid.do",
			idField: 'id',
			treeField: 'name',
			fit: true,
			border: false,
			remoteSort: false,
			toolbar: '#buttonbar',
			striped:true,
			frozenColumns: [[
			    { field: 'ck', checkbox: true },
			    { field: 'id', title: 'ID(id)', width: 80, sortable: true }
			]],
			columns: [[
			    { field: 'name', title: '名称(name)', width: 220, sortable: true },
			    { field: 'seq', title: '排序(seq)', width: 80, sortable: true },
			    { field: 'type', title: '类型(type)', width: 80, sortable: true, formatter:function(value,row){
			    	if(value == "R"){return "类别";}else if(value == "F"){return "<font color='green'>菜单</font>";}else{return "<font color='red'>操作</font>";}
			    }},
			    { field: 'disused', title: '禁用(disused)', width: 100, sortable: true, formatter:function(value,row){
			    	if(value == "Y"){return "<font color='green'>启用</font>";}else{return "<font color='red'>禁用</font>";}
			    }},
			    { field: 'href', title: '链接地址(href)', width: 250 },
			    { field: 'created', title: '日期(date)', width: 140, sortable: true }
			]],
			enableHeaderClickMenu: false,
	        enableHeaderContextMenu: false,
	        dndRow: true,                       //此属性开启此表格的行拖动排序功能
	        moveMenu: { submenu: false, upLevel: true, up: true, down: true, downLevel: true },    //开始行右键菜单的移动列功能，此属性可丰富配置，详情见 API 文档
	        selectOnRowContextMenu: false,       //此属性开启当右键点击行时自动选择该行的功能
	        //通过拖动数据行以及右键菜单移动行数据时，都会触发 onDrop 事件。
	        onDrop: function (target, source, point) {
	        	ondrop(target, source, point) ;
	        },
	        autoBindDblClickRow: true

	    });
	});
	
	function ondrop(target, source, point) {
		$.post(siteUtil.basePath+"/sysmgr/menu/ondrop.do", {id:source.id, pid:target.id, point:point}, function(result) {
			if (result.status) {
				treeGrid.treegrid('reload') ;
				$.messager.show($.string.format("您刚才将节点 {0} 移向了节点 {1} 的 {2} 位置", source.name, target.name, point));
				
				$.easyui.loading({ msg: "正在加载...", locale: "#westCenterLayout", topMost: true });
				$.util.exec(function () { 
					$.easyui.parent.window.mainpage.refreshNavTab(); 
					$.easyui.loaded("#westCenterLayout", true);
				},3000);
				
			} else {
				$.messager.show($.string.format("移向节点失败"));
			}
		}, 'json');
	}
	
	function form_edit(form) {
		var form_url = siteUtil.basePath+"/sysmgr/menu/menu_form.do" ;
		if("E" == form) {
			var node = treeGrid.treegrid('getSelected');
			if (node) {
				form_url = siteUtil.basePath+"/sysmgr/menu/menu_form.do?id="+node.id ;
			} else {
				$.easyui.messager.show({ icon: "info", msg: "请选择一条记录！" });
				return ;
			}
		}
		var dialog = $.easyui.showDialog({
            title: "表单",
            href: form_url,
            iniframe: false,
            width: 550, height: 250,
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
					$.post(siteUtil.basePath+"/sysmgr/menu/delete.do", {id:node.id}, function(result) {
						if (result.status) {
							treeGrid.treegrid('reload') ;
							$.easyui.messager.show({ icon: "info", msg: "删除记录成功。" });
							
							$.easyui.loading({ msg: "正在加载...", locale: "#westCenterLayout", topMost: true });
							$.util.exec(function () { 
								$.easyui.parent.window.mainpage.refreshNavTab(); 
								$.easyui.loaded(true);
							},1000);
						} else {
							$.easyui.messager.show({ icon: "info", msg: "删除记录失败。" });
						}
					}, 'json');
				} else {$.easyui.loaded("#westCenterLayout", true);}
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
                    <a id="btn1" onClick="form_edit('A');" class="easyui-linkbutton" data-options="plain: true, iconCls: 'ext_add'">添加</a>
                    <a id="btn2" onClick="form_edit('E');" class="easyui-linkbutton" data-options="plain: true, iconCls: 'ext_edit'">编辑</a>
                    <a id="btn3" onClick="del();" class="easyui-linkbutton" data-options="plain: true, iconCls: 'ext_remove'">删除</a>
                    <a id="btn4" onclick="treeGrid.treegrid('reload');" class="easyui-linkbutton" data-options="plain: true, iconCls: 'ext_reload'">刷新</a>
                </div>
			</div>
			
		</div>
	</div>	
</body>
</html>