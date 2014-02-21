<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>EasyUI Administrator</title>
<%@ include file="/common/base/meta.jsp"%>
<%@ include file="/common/base/script.jsp"%>

<link href="<%=basePath%>/common/view-index-resource/index.css" rel="stylesheet" />
<script src="<%=basePath%>/common/view-index-resource/index.js" type="text/javascript"></script>
<script src="<%=basePath%>/common/view-index-resource/index-startup.js"></script>
</head>

<body style="padding: 0px; margin: 0px;">
	<jsp:include page="login_dialog.jsp" />
    
	<div id="mainLayout" class="easyui-layout hidden" data-options="fit: true">
	
		<div id="northPanel" data-options="region: 'north', border: false" style="height: 80px; overflow: hidden;">
			<div id="topbar" class="top-bar">
                <div class="top-bar-left">
                	<!-- 
                    <h1 style="margin-left: 10px; margin-top: 10px;">info-x</h1>
                	 -->
                </div>
                <div class="top-bar-right">
                    <div id="timerSpan"></div>
                    <div id="themeSpan">
                        <span>更换皮肤风格：</span>
                        <select id="themeSelector"></select>
                        <a id="btnHideNorth" class="easyui-linkbutton" data-options="plain: true, iconCls: 'layout-button-up'"></a>
                    </div>
                </div>
            </div>
            
            <div id="toolbar" class="panel-header panel-header-noborder top-toolbar">
            	<div id="infobar">
                    <span id="loginInfo" class="icon-hamburg-user" style="padding:1px 1px 0px 25px; background-position: left center;"></span>
                </div>
                <div id="buttonbar">
                    <a id="btnContact" class="easyui-linkbutton easyui-tooltip" title="联系杨浩泉" data-options="plain: true, iconCls: 'icon-hamburg-contact'">Mr.Yang</a>
                    <a id="btnFullScreen" class="easyui-linkbutton" data-options="plain: true, iconCls: 'icon-standard-arrow-inout'">全屏切换</a>
                    <a id="btnLook" name="look" class="easyui-linkbutton" data-options="plain: true, iconCls: 'icon-hamburg-sign-out'">锁定</a>
                    <a id="btnExit" name="exit" class="easyui-linkbutton" data-options="plain: true, iconCls: 'icon-hamburg-sign-out'">退出系统</a>
                    <a id="btnShowNorth" class="easyui-linkbutton" data-options="plain: true, iconCls: 'layout-button-down'" style="display: none;"></a>
                </div>
            </div>
		</div>
		
		<div data-options="region: 'west', title: '菜单导航栏', iconCls: 'icon-standard-map', split: true, minWidth: 250, maxWidth: 500" style="width: 250px; padding: 1px;">
			<div id="navTab_tools" class="tabs-tool">
                <table>
                    <tr>
                        <td><a id="navMenu_refresh" class="easyui-linkbutton easyui-tooltip" title="刷新该选项卡及其导航菜单" data-options="plain: true, iconCls: 'icon-hamburg-refresh'"></a></td>
                    </tr>
                </table>
            </div>
            
            <div id="navTab" class="easyui-tabs" data-options="fit: true, border: true, tools: '#navTab_tools'">
            	<div data-options="title: '导航菜单', iconCls: 'icon-standard-application-view-tile', refreshable: false, selected: true">
            		<div id="westLayout" class="easyui-layout" data-options="fit: true">
                        <div data-options="region: 'center', border: false" style="border-bottom-width: 1px;">
                            <div id="westCenterLayout" class="easyui-layout" data-options="fit: true">
                                <div data-options="region: 'north', split: false, border: false" style="height: 31px;">
                                    <div class="easyui-toolbar">
                                        <a id="navMenu_expand" class="easyui-splitbutton" data-options="iconCls: 'icon-metro-expand2', menu: '#navMenu_toggleMenu'">展开</a>
                                        <a id="navMenu_Favo" class="easyui-linkbutton" data-options="plain: true, iconCls: 'icon-standard-feed-add'">收藏</a>
                                        <a id="navMenu_Rename" class="easyui-linkbutton" data-options="plain: true, iconCls: 'icon-hamburg-pencil'">重命名</a>
                                        <div id="navMenu_toggleMenu" class="easyui-menu">
                                            <div id="navMenu_collapse" data-options="iconCls: 'icon-metro-contract2'">折叠当前</div>
                                            <div class="menu-sep"></div>
                                            <div id="navMenu_collapseCurrentAll" data-options="iconCls: 'icon-metro-expand'">展开当前所有</div>
                                            <div id="navMenu_expandCurrentAll" data-options="iconCls: 'icon-metro-contract'">折叠当前所有</div>
                                            <div class="menu-sep"></div>
                                            <div id="navMenu_collapseAll" data-options="iconCls: 'icon-standard-arrow-out'">展开所有</div>
                                            <div id="navMenu_expandAll" data-options="iconCls: 'icon-standard-arrow-in'">折叠所有</div>
                                        </div>
                                    </div>
                                </div>
                                <div data-options="region: 'center', border: false">
                                    <ul id="navMenu_Tree" style="padding-top: 2px; padding-bottom: 2px;"></ul>
                                </div>
                            </div>
                        </div>
                        <div id="westSouthPanel" data-options="region: 'south', border: false, split: true, minHeight: 32, maxHeight: 275" style="height: 275px; border-top-width: 1px;">
                            <ul id="navMenu_list"></ul>
                        </div>
                    </div>
            	</div>
            	
            	 <div data-options="title: '个人收藏', iconCls: 'icon-hamburg-star', refreshable: false">
            	 	<div id="westFavoLayout" class="easyui-layout" data-options="fit: true">
                        <div data-options="region: 'north', split: false, border: false" style="height: 31px;">
                            <div class="easyui-toolbar">
                                <a id="favoMenu_expand" class="easyui-splitbutton" data-options="iconCls: 'icon-metro-expand2', menu: '#favoMenu_toggleMenu'">展开</a>
                                <a id="favoMenu_Favo" class="easyui-linkbutton" data-options="plain: true, iconCls: 'icon-standard-feed-delete'">取消收藏</a>
                                <a id="favoMenu_Rename" class="easyui-linkbutton" data-options="plain: true, iconCls: 'icon-hamburg-pencil'">重命名</a>
                                <div id="favoMenu_toggleMenu" class="easyui-menu">
                                    <div id="favoMenu_collapse" data-options="iconCls: 'icon-metro-contract2'">折叠当前</div>
                                    <div class="menu-sep"></div>
                                    <div id="favoMenu_collapseCurrentAll" data-options="iconCls: 'icon-metro-expand'">展开当前所有</div>
                                    <div id="favoMenu_expandCurrentAll" data-options="iconCls: 'icon-metro-contract'">折叠当前所有</div>
                                    <div class="menu-sep"></div>
                                    <div id="favoMenu_collapseAll" data-options="iconCls: 'icon-standard-arrow-out'">展开所有</div>
                                    <div id="favoMenu_expandAll" data-options="iconCls: 'icon-standard-arrow-in'">折叠所有</div>
                                </div>
                            </div>
                        </div>
                        <div data-options="region: 'center', border: false">
                            <ul id="favoMenu_Tree" style="padding-top: 2px; padding-bottom: 2px;"></ul>
                        </div>
                    </div>
            	 </div>
            </div>
		</div>
		 
		<div data-options="region: 'center'" style="padding: 1px;">
			<div id="mainTab_tools" class="tabs-tool">
                <table>
                    <tr>
                        <td><a id="mainTab_jumpHome" class="easyui-linkbutton easyui-tooltip" title="跳转至主页选项卡" data-options="plain: true, iconCls: 'icon-hamburg-home'"></a></td>
                        <td><div class="datagrid-btn-separator"></div></td>
                        <td><a id="mainTab_toggleAll" class="easyui-linkbutton easyui-tooltip" title="展开/折叠面板使选项卡最大化" data-options="plain: true, iconCls: 'icon-standard-arrow-inout'"></a></td>
                        <td><div class="datagrid-btn-separator"></div></td>
                        <td><a id="mainTab_jumpTab" class="easyui-linkbutton easyui-tooltip" title="在新页面中打开该选项卡" data-options="plain: true, iconCls: 'icon-standard-shape-move-forwards'"></a></td>
                        <td><div class="datagrid-btn-separator"></div></td>
                        <td><a id="mainTab_closeTab" class="easyui-linkbutton easyui-tooltip" title="关闭当前选中的选项卡" data-options="plain: true, iconCls: 'icon-standard-application-form-delete'"></a></td>
                        <td><a id="mainTab_closeOther" class="easyui-linkbutton easyui-tooltip" title="关闭除当前选中外的其他所有选项卡" data-options="plain: true, iconCls: 'icon-standard-cancel'"></a></td>
                        <td><div class="datagrid-btn-separator"></div></td>
                        <td><a id="mainTab_closeLeft" class="easyui-linkbutton easyui-tooltip" title="关闭左侧所有选项卡" data-options="plain: true, iconCls: 'icon-standard-tab-close-left'"></a></td>
                        <td><a id="mainTab_closeRight" class="easyui-linkbutton easyui-tooltip" title="关闭右侧所有选项卡" data-options="plain: true, iconCls: 'icon-standard-tab-close-right'"></a></td>
                        <td><div class="datagrid-btn-separator"></div></td>
                        <td><a id="mainTab_closeAll" class="easyui-linkbutton easyui-tooltip" title="关闭所有选项卡" data-options="plain: true, iconCls: 'icon-standard-cross'"></a></td>
                    </tr>
                </table>
            </div>
            
            <div id="mainTab" class="easyui-tabs" data-options="fit: true, border: true, showOption: true, enableNewTabMenu: true, repeatable: true, tools: '#mainTab_tools', enableJumpTabMenu: true">
            	<div id="homePanel" data-options="title: '主页', iconCls: 'icon-hamburg-home', selected: true">
            		<div class="easyui-layout" data-options="fit: true">
                       	<div data-options="region: 'center', border: false" style="overflow: hidden;">
                            <div id="portal" class="easyui-portal" data-options="fit: true, border: false">
                                <div style="width: 33%;">
                                    <div data-options="title: '项目信息', height: 310, collapsible: true, closable: true">
                                    </div>
                                    <div data-options="title: '功能简介', height: 310, collapsible: true, closable: true">
                                    </div>
                                </div>
                                <div style="width: 33%;">
                                    <div data-options="title: '项目信息', height: 310, collapsible: true, closable: true">
                                    </div>
                                    <div data-options="title: '功能简介', height: 310, collapsible: true, closable: true">
                                    </div>
                                </div>
                                <div style="width: 33%;">
                                    <div data-options="title: '项目信息', height: 310, collapsible: true, closable: true">
                                    </div>
                                    <div data-options="title: '功能简介', height: 310, collapsible: true, closable: true">
                                    </div>                                    
                                </div>
                            </div>
                        </div>
                    </div>
            	</div>
            </div>
		</div>
		
		<div id="eastLayout" data-options="region: 'east', title: '日历', iconCls: 'icon-standard-date', split: false, minWidth: 200, maxWidth: 500" style="width: 220px; padding: 1px; border-left-width: 0px;">
			<div id="eastLayout" class="easyui-layout" data-options="fit: true">
                <div data-options="region: 'north', split: false, border: false" style="height: 220px;">
                    <div class="easyui-calendar" data-options="fit: true"></div>
                </div>
                <div data-options="region: 'center', title: '在线用户', iconCls: 'icon-hamburg-link'">
                    <div id="online"></div>
                </div>
            </div>
		</div>
		
		<div data-options="region: 'south', title: '关于...', iconCls: 'icon-standard-information', collapsed: true, border: false" style="height: 70px;">
			<div style="color: #4e5766; padding: 6px 0px 0px 0px; margin: 0px auto; text-align: center; font-size:12px; font-family:微软雅黑;">
            	@2014 Copyright:
                <a href="javascript:;" target="_blank" style="text-decoration: none;">Mr.Yang.</a>
                &nbsp;&nbsp;|&nbsp;&nbsp;系统版本 Version1.0<br />
            	 建议使用&nbsp;
                <a href="http://windows.microsoft.com/zh-CN/internet-explorer/products/ie/home" target="_blank" style="text-decoration: none;">IE(Version 9/10/11)</a>/
                <a href="https://www.google.com/intl/zh-CN/chrome/browser/" target="_blank" style="text-decoration: none;">Chrome</a>/
                <a href="http://firefox.com.cn/download/" target="_blank" style="text-decoration: none;">Firefox</a>
                &nbsp;系列浏览器。
            </div>
		</div>
	
	</div>
	
</body>
</html>