<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
var uploader ;
$(function() {
	uploader = new plupload.Uploader({
		runtimes : 'html5,flash,html4',
		flash_swf_url : '${pageContext.request.contextPath}/js/plugins/plupload-2.1.1/js/Moxie.swf',	//Flash支持
		url : '${pageContext.request.contextPath}/plupload?fileFolder=${upload_path}&isParent=true&dateFolder=false',
		browse_button : 'pickfiles', 
		container: 'container', 
		/*
		filters : {
			max_file_size : '10mb',
			mime_types: [
				{title : "Image files", extensions : "jpg,gif,png"},
				{title : "Zip files", extensions : "zip"}
			]
		},*/
		init: {
			PostInit: function() {
				$("#filelist").empty();
				$("#uploadfiles").click(function() {
					if (uploader.files.length > 0) {
						$('#filescountlist').show() ;
						
						$('#filesTotal').html(uploader.files.length+'&nbsp;') ;
						
						uploader.start();
						return false;
			        } else {
			        	alert('列表中没有文件，请添加！');
			        }
				});
			},
			FilesAdded: function(up, files) {
				plupload.each(files, function(file, index) {
					var fl = '<tr id="' + file.id + '">'+
							 '<td style="width:40px;text-align:center;">'+(index+1)+'</td>'+
							 '<td style="padding-left:5px;">'+file.name+'</td>'+
							 '<td style="width:80px;text-align:center;">'+plupload.formatSize(file.size)+'</td>'+
							 '<td style="width:50px;text-align:center;"><b>0%</b></td>'+
							 '<td id="img_' + file.id + '" style="width:50px;text-align:center;border-right:1px solid #ddd;">'+
							 '<img style="margin-top:4px;" onclick="uploader.removeFile(uploader.getFile($(this).parent().parent().attr(\'id\')));$(this).parent().parent().remove();" src="${pageContext.request.contextPath}/js/plugins/easyui/icons/icon-ext/edit_remove1.png"></td>'+
							 '</tr>' ;
					$("#filelist").append(fl) ;
				});
			},
			FileUploaded: function(up, file, info) {
				var response = $.parseJSON(info.response);
				if (response.status) {
					$("#filesFail").html("&nbsp;"+uploader.total.failed+"&nbsp;") ;
					$("#filesSuccess").html("&nbsp;"+uploader.total.uploaded+"&nbsp;") ;
					$("#img_"+file.id).empty();
				} else {
					$('#filelist').append("<div>错误: " + response.msg+"</div>");
				}
			},
			UploadProgress: function(up, file) {
				$('#' + file.id + " b").html(file.percent + "%");
			},

			Error: function(up, err) {
				$('#filelist').append("<tr><td colspan='5'>错误: " + err.code + ", 消息: " + err.message + (err.file ? ", File: " + err.file.name : "") + "</td></tr>");
				up.refresh(); 
			}
		}
	});

	uploader.init();
});
</script>
<style>
.tbh{background:#eee;width:100%;height:50px}
.tbh th{height:26px;border-bottom:1px solid #ddd;border-left:1px solid #ddd}
.tbc{width:100%}.tbc td{height:30px;border-bottom:1px solid #ddd;border-left:1px solid #ddd}
.tl{display:block;height:264px;overflow-y:scroll}
#container{width:100%;height:41px;border:1px solid #ddd;border-left:0;margin-top:2px;text-align:center;position:relative}
#filescountlist{position:absolute;top:13px;right:12px;display:none}
</style>
<table class="tbh">
	<tr>
		<th style="width:40px;text-align:center;">序号</th>
		<th style="padding-left:5px;">文件名称</th>
		<th style="width:80px;text-align:center;">大小</th>
		<th style="width:50px;text-align:center;">完成</th>
		<th style="width:50px;text-align:center;border-right:1px solid #ddd;">删除</th>
		<th style="width:15px;text-align:center;border:0px;border-bottom:1px solid #ddd;">&nbsp;</th>
	</tr>
</table>
<div class="tl">
	<table class="tbc" id="filelist">
		<!-- 
		<tr>
			<td style="width:40px;text-align:center;">序号</td>
			<td style="padding-left:5px;">文件名称</th>
			<td style="width:80px;text-align:center;">大小</td>
			<td style="width:50px;text-align:center;">完成</td>
		</tr>
		 -->
	</table>
</div>
<div id="container">
	<a id="pickfiles" style="margin-top:8px;" class="easyui-linkbutton" data-options="plain: false, iconCls: 'icon-hamburg-config', iconAlign: 'left'" >选择文件</a>
	&nbsp;&nbsp;
	<a id="uploadfiles" style="margin-top:8px;" class="easyui-linkbutton" data-options="plain: false, iconCls: 'icon-hamburg-config', iconAlign: 'left'" >上传文件</a>
	<span id="filescountlist">
		[<b id="fileFail" style="color:red;">&nbsp;0&nbsp;</b><b id="filesSuccess" style="color:green;">&nbsp;0&nbsp;</b>&nbsp;/&nbsp;<b id="filesTotal" style="color:block;">&nbsp;0&nbsp;</b>]
	</span>
</div>





























