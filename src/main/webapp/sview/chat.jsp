<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>EasyUI Administrator</title>
<%@ include file="/common/base/meta.jsp"%>
<%@ include file="/common/base/script.jsp"%>

<%-- 引入Highcharts --%>
<script src="<%=basePath%>/js/plugins/Highcharts-3.0.9/js/highcharts.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=basePath%>/js/plugins/Highcharts-3.0.9/js/modules/exporting.js" type="text/javascript" charset="utf-8"></script>
<%-- 引入Highcharts扩展 --%>
<script src="<%=basePath%>/js/plugins/Highcharts-3.0.9/ExtHighcharts.js" type="text/javascript" charset="utf-8"></script>

<script type="text/javascript">
$(function () {
        $('#container').highcharts({
        	exporting : {
				filename : '用户注册时间分布'
			},
            chart: {
                type: 'area'
            },
            title: {
                text: 'Area chart with negative values'
            },
            xAxis: {
                categories: ['Apples', 'Oranges', 'Pears', 'Grapes', 'Bananas']
            },
            credits: {
                enabled: false
            },
            series: [{
                name: 'John',
                data: [5, 3, 4, 7, 2]
            }, {
                name: 'Jane',
                data: [2, -2, -3, 2, 1]
            }, {
                name: 'Joe',
                data: [3, 4, 4, -2, 5]
            }]
        });
    });
</script>

</head>

<body style="padding: 0px; margin: 0px;">
	<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
</body>
</html>