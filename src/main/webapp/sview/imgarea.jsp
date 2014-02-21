<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>EasyUI Administrator</title>
<%@ include file="/common/base/meta.jsp"%>
<%@ include file="/common/base/script.jsp"%>

<script type="text/javascript">
$(function () {
	$('img#photo').imgAreaSelect({
        handles: true,
        onSelectChange: function(img, selection) {
        	$('input').val('');
            $('input[name="x1"]').val(selection.x1);  
            $('input[name="y1"]').val(selection.y1);  
            $('input[name="x2"]').val(selection.x2);  
            $('input[name="y2"]').val(selection.y2);  
            $('input[name="width"]').val(selection.width);  
            $('input[name="height"]').val(selection.height);  
        },
        onSelectEnd: function (img, selection) {  
            $('input[name="x1"]').val(selection.x1);  
            $('input[name="y1"]').val(selection.y1);  
            $('input[name="x2"]').val(selection.x2);  
            $('input[name="y2"]').val(selection.y2);  
            $('input[name="width"]').val(selection.width);  
            $('input[name="height"]').val(selection.height);  
        }
    });
	
});
</script>

</head>

<body style="padding: 0px; margin: 0px;">
	<div style="margin:0 auto;width:800px; height:700px;border:1px solid red;">
		x1<input type="text" name="x1" /><br>
	    y1<input type=text name="y1" /><br>
	    x2<input type="text" name="x2" /><br>  
	    y2<input type="text" name="y2" /><br>  
	    width<input type="text" name="width" /><br>  
	    height<input type="text" name="height" /><br>  
	     
	    <div id="facediv" style="z-index:100;">  
	        <img id="photo" src="a1.jpg" style="width:500px;height:500px;" />  
	    </div>  
	</div>

</body>
</html>