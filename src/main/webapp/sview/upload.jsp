<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>

<title>Plupload - jQuery UI Widget</title>

<%@ include file="/common/base/meta.jsp"%>
<%@ include file="/common/base/script.jsp"%>

<!-- 
<link rel="stylesheet" href="//ajax.googleapis.com/ajax/libs/jqueryui/1.8.9/themes/base/jquery-ui.css" type="text/css" />
 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/js/plugins/plupload-2.1.1/jquery.ui/jquery-ui.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/js/plugins/plupload-2.1.1/js/jquery.ui.plupload/css/jquery.ui.plupload.css" type="text/css" />

<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/plupload-2.1.1/jquery.ui/jquery-ui.min.js"></script>
<!-- 
<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.2/jquery-ui.min.js"></script>
 -->

<!-- production -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/plupload-2.1.1/js/plupload.full.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/plupload-2.1.1/js/jquery.ui.plupload/jquery.ui.plupload.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/plupload-2.1.1/js/i18n/zh_CN.js"></script>

<!-- debug 
<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/plupload-2.1.1/js/moxie.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/plupload-2.1.1/js/plupload.dev.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/plugins/plupload-2.1.1/js/jquery.ui.plupload/jquery.ui.plupload.js"></script>
-->

</head>
<body style="font: 13px Verdana; background: #eee; color: #333">

<h1>jQuery UI Widget</h1>

<p>You can see this example with different themes on the <a href="http://plupload.com/example_jquery_ui.php">www.plupload.com</a> website.</p>

<form id="form" method="post">
	<div id="uploader">
		<p>Your browser doesn't have Flash, Silverlight or HTML5 support.</p>
	</div>
	<br />
	<input type="submit" value="Submit" />
</form>

<script type="text/javascript">
// Initialize the widget when the DOM is ready
$(function() {
	$("#uploader").plupload({
		// General settings
		runtimes : 'html5,flash,silverlight,html4',
		url : '${pageContext.request.contextPath}/plupload?fileFolder=/advertise&isParent=true&dateFolder=true',

		// User can upload no more then 20 files in one go (sets multiple_queues to false)
		max_file_count: 20,
		
		chunk_size: '1mb',

		// Resize images on clientside if we can
		resize : {
			width : 200, 
			height : 200, 
			quality : 90,
			crop: true // crop to exact dimensions
		},
		
		filters : {
			// Maximum file size
			max_file_size : '1000mb',
			// Specify what files to browse for
			mime_types: [
				{title : "Image files", extensions : "jpg,gif,png"},
				{title : "Zip files", extensions : "zip"}
			]
		},

		// Rename files by clicking on their titles
		rename: true,
		
		// Sort files
		sortable: true,

		// Enable ability to drag'n'drop files onto the widget (currently only HTML5 supports that)
		dragdrop: true,

		// Views to activate
		views: {
			list: true,
			thumbs: true, // Show thumbs
			active: 'thumbs'
		},

		// Flash settings
		flash_swf_url : '${pageContext.request.contextPath}/js/plugins/plupload-2.1.1/js/Moxie.swf',

		// Silverlight settings
		silverlight_xap_url : '${pageContext.request.contextPath}/js/plugins/plupload-2.1.1/js/Moxie.xap'
	});


	// Handle the case when form was submitted before uploading has finished
	$('#form').submit(function(e) {
		// Files in queue upload them first
		if ($('#uploader').plupload('getFiles').length > 0) {

			// When all files are uploaded submit form
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
</body>
</html>
