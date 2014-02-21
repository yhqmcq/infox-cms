<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();%>
<%
java.util.Map<String, Cookie> cookieMap = new java.util.HashMap<String, Cookie>();
Cookie[] cookies = request.getCookies();
	if (null != cookies) {
		for (Cookie cookie : cookies) {
		cookieMap.put(cookie.getName(), cookie);
	}
}
String themeName = "bootstrap";//指定如果用户未选择样式，那么初始化一个默认样式
if (cookieMap.containsKey("themeName")) {
	Cookie cookie = (Cookie) cookieMap.get("themeName");
	themeName = cookie.getValue();
}
%>

<script type="text/javascript">var yhq = yhq || {};yhq.basePath = '<%=basePath%>';</script>

<link href="<%=basePath%>/js/plugins/easyui/jquery-easyui-theme/<%=themeName%>/easyui.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>/js/plugins/easyui/jquery-easyui-theme/icon.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>/js/plugins/easyui/icons/icon-all.css" rel="stylesheet" type="text/css" />

<script src="<%=basePath%>/js/plugins/jquery/jquery-1.10.2.js" type="text/javascript"></script>

<script src="<%=basePath%>/js/plugins/easyui/jquery-easyui-1.3.5/jquery.easyui.min.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/plugins/easyui/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>

<script src="<%=basePath%>/js/util/jquery.jdirk.js" type="text/javascript"></script>

<script src="<%=basePath%>/js/plugins/easyui/jeasyui-extensions/jeasyui.extensions.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/plugins/easyui/jeasyui-extensions/jeasyui.extensions.linkbutton.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/plugins/easyui/jeasyui-extensions/jeasyui.extensions.validatebox.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/plugins/easyui/jeasyui-extensions/jeasyui.extensions.combo.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/plugins/easyui/jeasyui-extensions/jeasyui.extensions.combobox.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/plugins/easyui/jeasyui-extensions/jeasyui.extensions.form.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/plugins/easyui/jeasyui-extensions/jeasyui.extensions.menu.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/plugins/easyui/jeasyui-extensions/jeasyui.extensions.panel.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/plugins/easyui/jeasyui-extensions/jeasyui.extensions.window.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/plugins/easyui/jeasyui-extensions/jeasyui.extensions.dialog.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/plugins/easyui/jeasyui-extensions/jeasyui.extensions.layout.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/plugins/easyui/jeasyui-extensions/jeasyui.extensions.tree.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/plugins/easyui/jeasyui-extensions/jeasyui.extensions.datagrid.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/plugins/easyui/jeasyui-extensions/jeasyui.extensions.treegrid.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/plugins/easyui/jeasyui-extensions/jeasyui.extensions.combogrid.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/plugins/easyui/jeasyui-extensions/jeasyui.extensions.combotree.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/plugins/easyui/jeasyui-extensions/jeasyui.extensions.tabs.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/plugins/easyui/jeasyui-extensions/jeasyui.extensions.theme.js" type="text/javascript"></script>
<!--<script src="release/jeasyui.extensions.all.min.js"></script>-->


<script src="<%=basePath%>/js/plugins/easyui/icons/jeasyui.icons.all.js" type="text/javascript"></script>
<!--<script src="release/jeasyui.icons.all.min.js"></script>-->


<script src="<%=basePath%>/js/plugins/easyui/jeasyui-extensions/jeasyui.extensions.icons.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/plugins/easyui/jeasyui-extensions/jquery-easyui-toolbar/jquery.toolbar.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/plugins/easyui/jeasyui-extensions/jquery-easyui-comboicons/jquery.comboicons.js" type="text/javascript"></script>

<script src="<%=basePath%>/js/plugins/easyui/jeasyui-extensions/jeasyui.extensions.gridselector.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/plugins/easyui/jeasyui-extensions/jquery-easyui-comboselector/jquery.comboselector.js" type="text/javascript"></script>

<script src="<%=basePath%>/js/plugins/easyui/jeasyui-extensions/jquery-easyui-portal/jquery.portal.js" type="text/javascript"></script>
<link href="<%=basePath%>/js/plugins/easyui/jeasyui-extensions/jquery-easyui-portal/portal.css" rel="stylesheet" type="text/css" />

<script src="<%=basePath%>/js/plugins/easyui/my-ext/my-easyui-ext.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/plugins/other/jquery-jrumble.js" type="text/javascript"></script>
<link href="<%=basePath%>/common/view-index-resource/base.css" rel="stylesheet" />

<link rel="stylesheet" type="text/css" href="<%=basePath%>/js/plugins/jquery.imgareaselect-0.9.10/css/imgareaselect-default.css" />
<script type="text/javascript" src="<%=basePath%>/js/plugins/jquery.imgareaselect-0.9.10/scripts/jquery.imgareaselect.pack.js"></script>

<script type="text/javascript" src="<%=basePath%>/js/plugins/plupload-2.1.1/js/plupload.full.min.js"></script>