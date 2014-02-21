<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>文件管理</title>
<%@ include file="/common/base/meta.jsp"%>
<%@ include file="/common/base/script.jsp"%>
<script type="text/javascript">
	var action_url = yhq.basePath+"/sysmgr/filemanager/file_treelist.do" ;
	var t1 ;
	var d1 ;
	$(function() {
		treeFunc() ;
		
		d1 = $("#d1").datagrid({
			title: '路径[${root}]',
			method: "post",
			idField: 'id',
			fit: true,
			border: false,
			remoteSort: false,
			toolbar: '#buttonbar',
			striped:true,
			rownumbers: true,
	        frozenColumns: [[
	            { field: 'ck', checkbox: true },
	            { field: 'id', title: 'ID', width: 40, sortable: true }
	        ]],
			columns: [[
			    { field: 'fileName', title: '名称', width: 300, sortable: true, tooltip: true, formatter:function(value,row){
			    	var str = "" ;
			    	if(row.dir) {
				    	str += $.string.format("<a class='imgIcon {2}'></a>&nbsp;<a href='javascript:;' onclick='enterFolder(\"{1}\")'>[目录]{0}</a>", value, row.path, row.iconCls);
			    	} else {
				    	str += $.string.format("<a class='imgIcon {2}'></a>&nbsp;<a href='javascript:;' onclick='loadFile(\"{1}\",\"{3}\",\"{4}\")'>[文件]{0}</a>", value, row.path, row.iconCls, row.editable, row.fileName);
			    	}
			    	return str ;
			    }},
			    { field: 'fileSize', title: '大小', width: 70, sortable: true },
			    { field: 'lastModified', title: '最后修改日期', width: 140, sortable: true }
			]],
			onLoadSuccess: function(data) {
                $.fn.datagrid.extensions.onLoadSuccess.apply(this, arguments);  //这句一定要加上。
                d1.datagrid('clearSelections');d1.datagrid('clearChecked');
            }
		});
	});
	
	function treeFunc() {
		var root = {"id": "0", "text": "文件管理", "dir": true, "iconCls": "folder", "attributes": {"filePath":"${root}"}};
		t1 = $("#t1").tree({
			lines: true,animate: true,toggleOnClick: true,
			onBeforeExpand:function(node,param){  
				t1.tree('options').url = action_url+"?path="+node.attributes.filePath;
            },
            onClick: function(node) {
            	if(node.dir) {
            		enterFolder(node.attributes.filePath) ;
            	} else {
            		var t = t1.tree("getParent", node.target) ;
            		loadFile(node.path, node.editable, node.fileName, t.attributes.filePath);
            	}
            } 
		});
		
		$.post(action_url, null, function(datas) {
			if(datas.length > 0) {
				root.children = datas ;
				t1.tree("loadData", [root]);
				
				d1.datagrid("loadData",datas);
			} else {
				root.children = [] ;
				t1.tree("loadData", [root]);
			}
		}, 'json');
	}
	
	function enterFolder(path) {
		if(path.split("/").length-1 == 0) {
			$.easyui.messager.show({ icon: "info", msg: "已是根目录！" });
		} else {
			d1.datagrid({url: action_url+"?path="+path});
			d1.datagrid("getPanel").panel({title:"路径["+path+"]"}) ;
		}
		
	}
	
	function createDir() {
		$(":input[name=dirName]").val("");
		var cd = $("#createDirDialog").show().dialog({
			title: "&nbsp;创建目录", width: 260, height: 120, modal: true, 
			closable : false, iconCls: "icon-standard-folder-add",
			buttons : [ {
				text : '&nbsp;&nbsp;创建',
				iconCls : "icon-standard-folder-add",
				handler : function() {
					var dirName = $(":input[name=dirName]").val() ;
					if(dirName != "") {
						$.post(yhq.basePath+"/sysmgr/filemanager/file_createDir.do", {path:currentPath(), dirName:dirName}, function(result) {
							if(result.status) {
								treeFunc();enterFolder(currentPath()) ;cd.dialog("close");
							} else {
								$.easyui.messager.show({ icon: "info", msg: result.msg });
							}
						}, 'json');
					}
				}
			} ]
		});
		$(":input[name=dirName]")[0].focus() ;
	}
	
	function deleteff() {
		var rows = d1.datagrid('getChecked');
		var paths = [];
		if (rows.length > 0) {
			$.messager.confirm("您确定要进行该操作？", function (c) { 
				if(c) {
					for ( var i = 0; i < rows.length; i++) {
						paths.push(currentPath()+"/"+rows[i].fileName);
					}
					$.post(yhq.basePath+"/sysmgr/filemanager/file_deleteff.do", {path : paths.join(',')}, function(result) {
						if (result.status) {
							treeFunc();enterFolder(currentPath()) ;
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
	
	function loadFile(path, editable, filename, downpath) {
		if(editable) {	//可编辑或下载
			$.messager.solicit_ext("请选择是否编辑或下载？", function (c) {
				if (c != undefined) {
					if(c) {
						alert("编辑") ;
					} else {
						downloadFile(downpath, filename) ;
					}
				}
			});
		} else {	//只能下载
			 $.messager.confirm("是否下载该文件？", function (c) {
				 if(c) {
					 downloadFile(downpath, filename) ;
				 }
			 });
		}
	}
	
	function backFolder() {
		enterFolder(currentPath().substring(0,currentPath().lastIndexOf("/"))) ;
	}
	function currentPath() {
		var url = d1.datagrid("getPanel").panel("options").title ;
		var but = url.substring(url.indexOf("[")+1) ;
		return but.substring(0, but.indexOf("]")) ;
	}
	function reloadCurrDir() {
		enterFolder(currentPath()) ;
	}
	
	function downloadFile(curpath, filename) {
		curpath = (undefined==curpath?currentPath():curpath) ;
		var rows = d1.datagrid('getChecked');
		var fileNames = [];
		if(rows.length > 0 || undefined != filename) {
			for ( var i = 0; i < rows.length; i++) {
				if(!rows[i].dir) {
					fileNames.push(rows[i].fileName);
				}
			}
			if(fileNames.length > 0 || undefined != filename){
				window.location.target="_ablank" ; 
				window.location.href=yhq.basePath+"/sysmgr/filemanager/download.do?path="+curpath+"&fileName="+(undefined==filename?fileNames.join(','):filename);
			} else {
				$.easyui.messager.show({ icon: "info", msg: "目录无法下载，请选择文件!" });
			}
		} else {
			$.easyui.messager.show({ icon: "info", msg: "请选择要下载的文件!" });
		}
	}
	
	function uploadFile() {
		$.easyui.showDialog({
            title: "上传文件",
            width: 595, height: 395,
            topMost: false,
            href: yhq.basePath+"/sysmgr/filemanager/upload.do?path="+currentPath(),
            iniframe: false,
            enableApplyButton: false,
            enableSaveButton: false,
            enableCloseButton: false,
        });
	}
</script>
</head>
<body style="padding: 0px; margin: 0px;">
	<div class="easyui-layout" data-options="fit: true">
		<div style="width:250px;" data-options="title:'文件树', region: 'west', border: false, split:true,minWidth: 250, maxWidth: 250, iconCls:'icon-standard-chart-organisation', tools:[{
			iconCls:'ext_reload',
			handler:function(){treeFunc();}
		}]">
			<div id="t1"></div>
		</div>
		<div data-options="region: 'center', border: false" style="overflow: hidden;">
			<div id="d1">
				<div id="buttonbar">
                    <a id="btn1" onClick="backFolder();" class="easyui-linkbutton" data-options="plain: true, iconCls: 'icon-standard-arrow-turn-left'">后退</a>
                    <a id="btn2" onClick="createDir();" class="easyui-linkbutton" data-options="plain: true, iconCls: 'icon-standard-folder-add'">创建</a>
                    <a id="btn3" onClick="deleteff();" class="easyui-linkbutton" data-options="plain: true, iconCls: 'ext_remove'">删除</a>
                    <a id="btn3" onClick="uploadFile();" class="easyui-linkbutton" data-options="plain: true, iconCls: 'icon-hamburg-up'">上传</a>
                    <a id="btn3" onClick="downloadFile();" class="easyui-linkbutton" data-options="plain: true, iconCls: 'icon-hamburg-down'">下载</a>
                    <a id="btn4" onclick="reloadCurrDir();" class="easyui-linkbutton" data-options="plain: true, iconCls: 'ext_reload'">刷新</a>
                </div>
			</div>
		</div>
		<div id="createDirDialog" style="display:none;padding:10px;">
			目录名称：<input name="dirName" class="easyui-validatebox" type="text" data-options="" />
		</div>
	</div>	
</body>
</html>