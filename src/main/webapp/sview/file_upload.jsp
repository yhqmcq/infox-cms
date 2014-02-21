<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>批量上传文件</title>
<%@ include file="/common/base/meta.jsp"%>
<%@ include file="/common/base/script.jsp"%>

<!-- plupload -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/js/plugins/plupload-2.1.1/jquery.ui/jquery-ui.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/js/plugins/plupload-2.1.1/js/jquery.ui.plupload/css/jquery.ui.plupload.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/plupload-2.1.1/jquery.ui/jquery-ui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/plupload-2.1.1/js/plupload.full.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/plupload-2.1.1/js/jquery.ui.plupload/jquery.ui.plupload.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/plupload-2.1.1/js/i18n/zh_CN.js"></script>

<!-- pluploadQueue -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/js/plugins/plupload-2.1.1/js/jquery.plupload.queue/css/jquery.plupload.queue.css" type="text/css" media="screen" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/plupload-2.1.1/js/plupload.full.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/plupload-2.1.1/js/jquery.plupload.queue/jquery.plupload.queue.js"></script>

<script type="text/javascript">
var uploader ;
$(function() {
	
	uploader = new plupload.Uploader({
		runtimes : 'html5,flash,silverlight,html4',
		flash_swf_url : '${pageContext.request.contextPath}/js/plugins/plupload-2.1.1/js/Moxie.swf',	//Flash支持
		url : '${pageContext.request.contextPath}/plupload?fileFolder=/advertise&isParent=true&dateFolder=true',
		browse_button : 'pickfiles', // you can pass in id...
		container: 'container', // ... or DOM Element itself
		
		filters : {
			max_file_size : '10mb',
			mime_types: [
				{title : "Image files", extensions : "jpg,gif,png"},
				{title : "Zip files", extensions : "zip"}
			]
		},

		init: {
			PostInit: function() {
				document.getElementById('filelist').innerHTML = '';

				document.getElementById('uploadfiles').onclick = function() {
					uploader.start();
					return false;
				};
			},

			FilesAdded: function(up, files) {
				plupload.each(files, function(file) {
					document.getElementById('filelist').innerHTML += '<div id="' + file.id + '">' + file.name + ' (' + plupload.formatSize(file.size) + ') <b></b></div>';
				});
			},

			FileUploaded: function(up, file, info) {
				var response = $.parseJSON(info.response);
				if (response.status) {
	        		console.info(response) ;
				} else {
					$('#filelist').append("<div>错误: " + response.msg+"</div>");
				}
			},

			UploadProgress: function(up, file) {
				document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = '<span>' + file.percent + "%</span>";
			},

			Error: function(up, err) {
				document.getElementById('console').innerHTML += "\nError #" + err.code + ": " + err.message;
			}
		}
	});

	uploader.init();
	
	/*
	uploader = $("#uploader").pluploadQueue({
		runtimes : 'html5,flash,silverlight,html4',
		flash_swf_url : '${pageContext.request.contextPath}/js/plugins/plupload-2.1.1/js/Moxie.swf',	//Flash支持
		url : '${pageContext.request.contextPath}/plupload?fileFolder=/advertise&isParent=true&dateFolder=true',
		max_file_count: 20,	//最大可上传文件个数
		chunk_size: '1mb',	//文件切割大小
		resize : { width : 200, height : 200, quality : 90, crop: true },//缩略图，crop: true 剪裁到精确的尺寸
		rename: true,//点击文件标题重命名
		sortable: true,//允许拖动排序
		dragdrop: true,//文件拖放（仅支持HTML5）
		resize : {width : 320, height : 240, quality : 90},
		filters : { 
			max_file_size : '1000mb',
			mime_types: [ {title : "Image files", extensions : "jpg,gif,png"}, {title : "Zip files", extensions : "zip"} ]
		},
	});
	*/
	/*
	uploader = $("#uploader").plupload({
		runtimes : 'html5,flash,silverlight,html4',
		flash_swf_url : '${pageContext.request.contextPath}/js/plugins/plupload-2.1.1/js/Moxie.swf',	//Flash支持
		url : '${pageContext.request.contextPath}/plupload?fileFolder=/advertise&isParent=true&dateFolder=true',
		max_file_count: 20,	//最大可上传文件个数
		chunk_size: '1mb',	//文件切割大小
		resize : { width : 200, height : 200, quality : 90, crop: true },//缩略图，crop: true 剪裁到精确的尺寸
		rename: true,//点击文件标题重命名
		sortable: true,//允许拖动排序
		dragdrop: true,//文件拖放（仅支持HTML5）
		//显示缩略图，在线预览(1.文件显示列表,2.缩略图)
		views: { list: true, thumbs: true, active: 'thumbs' },
		filters : {
			//允许文件上传的大小
			max_file_size : '1000mb',
			//允许上传的文件类型
			mime_types: [ 
				{title : "Image files", extensions : "jpg,gif,png"}, 
				{title : "Zip files", extensions : "zip"} 
			]
		}
	});
	
	$('#uploader').on('uploaded', function(a, aa, aaa) {
		//var response = $.parseJSON(info.response);
		
		var s = $('#uploader').plupload('getUploader');
		
		console.info(s) ;
		//console.info(a) ; console.info(aa) ; console.info(aaa) ;
	});
	
	$('#uploader').on("progress", function(event, args) {
		//console.info(event) ;
		//console.info(args) ;
	});
	*/
	
});
</script>
</head>
<body style="padding: 0px; margin: 0px;">
<div id="filelist">Your browser doesn't have Flash, Silverlight or HTML5 support.</div>
	<div id="uploader">
		<p>你的暂浏览器不支持该上传插件。请使用支持HTML5，和已安装Flash插件的浏览器。</p>
	</div>
	
	<div id="container">
	    <a id="pickfiles" href="javascript:;">[Select files]</a> 
	    <a id="uploadfiles" href="javascript:;">[Upload files]</a>
	</div>
</body>
</html>
