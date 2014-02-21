<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>

<title>Plupload - jQuery UI Widget</title>

<%@ include file="/common/base/meta.jsp"%>
<%@ include file="/common/base/script.jsp"%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/js/plugins/plupload-2.1.1/jquery.ui/jquery-ui.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/js/plugins/plupload-2.1.1/js/jquery.ui.plupload/css/jquery.ui.plupload.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/plupload-2.1.1/jquery.ui/jquery-ui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/plupload-2.1.1/js/plupload.full.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/plupload-2.1.1/js/jquery.ui.plupload/jquery.ui.plupload.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/plupload-2.1.1/js/i18n/zh_CN.js"></script>
<script type="text/javascript">
$(function() {
	$("#uploader").plupload({
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


	// Handle the case when form was submitted before uploading has finished
	$('#form').submit(function(e) {
		// Files in queue upload them first
		if ($('#uploader').plupload('getFiles').length > 0) {

			//当所有文件上传完成后提交表单
			$('#uploader').on('complete', function() {
				$('#form')[0].submit();
			});

			$('#uploader').plupload('start');
		} else {
			alert("You must have at least one file in the queue.");
		}
		return false; // Keep the form from submitting
	});
});
</script>
</head>
<body style="font: 13px Verdana; background: #eee; color: #333">
	<div id="uploader" style="width:560px;">
		<p>Your browser doesn't have Flash, Silverlight or HTML5 support.</p>
	</div>
</body>
</html>
