<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
var count = 1;
var interCall;
function createProgress() {
	$.post(yhq.basePath+"/sysmgr/data/backup_progress.do",{},function(data){
		if(data.tablename!=""){
			//$("#progressContainer").append("正在备份表["+count+"]"+data.tablename + "<br>");
			count++;
		}else{
			$("#progressContainer").append("备份完成！");
			window.clearInterval(interCall);
		}
	},"json");
}
$(function() {
	$("#progressContainer").append("数据备份中，请稍等...<br>");
	createProgress();
});
interCall=window.setInterval("createProgress()",1000);
</script>

<div id="progressContainer"></div>
