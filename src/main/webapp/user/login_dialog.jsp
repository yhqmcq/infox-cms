<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
	var loginDialog;
	var loginTabs;
	$(function() {
		if ('${LOGIN_USER_KEY.user.id}') {/*如果已经登陆显示用户信息*/
			$("#loginInfo").html('欢迎您：${LOGIN_USER_KEY.user.truename}') ;
		}
		
		loginTabs = $('#loginTabs').tabs({
			onSelect: function(title) {
				kaptcha();
			}
		});
	});
	
	function loginView(){
		loginDialog = $('#loginDialog').show().dialog({
			title: "&nbsp;登陆",
            width: 430, height: 250, top: 200,
            closable : false,
            iconCls: "ext_lock",
            modal: true,
			buttons : [ {
				text : '&nbsp;&nbsp;登录',
				iconCls : 'ext_lock',
				handler : function() {
					loginFun();
				}
			} ]
		});
		var USER_ID = '${LOGIN_USER_KEY.user.id}';
		if (USER_ID) {/*如果已经登陆过了，那么刷新页面后也不需要弹出登录窗体*/
			loginDialog.dialog('close');
		} else {
			kaptcha();
		}
		$('form input').bind('keyup', function(event) {
			if (event.keyCode == '13') {
				loginFun();
			}
		});
	}
	
	function loginFun() {
		var k = $('#loginTabs').tabs('getSelected').find('form input[name=kaptcha]').val() ;
		tabsForm = $('#loginTabs').tabs('getSelected').find('form');//当前选中的tab
		if(tabsForm.form('validate') && "" != k) {
    		$.post(siteUtil.basePath+"/sysmgr/userAction/doNotNeedSession_login.do", tabsForm.form("getData"), function(result) {
    			if (result.status) {
    				$("#loginInfo").html('欢迎您：'+result.obj.user.truename) ;
    				$("#loginDialog").dialog('close');
    				$(mainTab).tabs("getSelected").panel('refresh');
    				window.mainpage.online();
    			} else {
    				jrumble();
    				$.easyui.messager.show({ icon: "info", msg: result.msg });
    			}
    		}, 'json');
    	} else {
    		jrumble();
    		$.easyui.messager.show({ icon: "info", msg: "验证码不能为空！" });
    	}
	}
	
	function loginOut(b){
    	$.post(siteUtil.basePath+"/sysmgr/userAction/doNotNeedSession_logout.do", null, function(result) {
    		if ("exit"==b) {
    			location.replace(siteUtil.basePath+'/index.jsp');
    		} else {
    			$("#loginInfo").html('') ;
    			window.mainpage.online() ;
    			$('#loginDialog').dialog('open');
    			kaptcha();
    		}
    	}, 'json');
    };
    
    function kaptcha() {
    	loginTabs.tabs('getSelected').find("#form-login input")[0].focus();
    	loginTabs.tabs('getSelected').find("#form-login input").val("");
    	var k= loginTabs.tabs('getSelected').find('form #kaptcha') ;
    	k.attr('src',siteUtil.basePath+'/kaptcha.jpg?' + Math.floor(Math.random() * 100)).fadeIn() ;
		k.click(     
	        function() {     
			   k.attr('src',siteUtil.basePath+'/images/loading5.gif').fadeIn() ;
	           $(this).hide().attr('src',siteUtil.basePath+'/kaptcha.jpg?' + Math.floor(Math.random() * 100)).fadeIn();     
	    });
    }
	//表单晃动
	function jrumble() {
		$('.inner').jrumble({
			x : 4,
			y : 0,
			rotation : 0
		});
		$('.inner').trigger('startRumble');
		setTimeout('$(".inner").trigger("stopRumble")', 500);
	}
</script>
<div id="maskContainer-loading">
	<div class="datagrid-mask"></div>
	<div class="datagrid-mask-msg" style="left: 50%; margin-left: -52.5px;"> 正在加载...</div>
</div>
<div id="maskContainer-browse">
	<div class="datagrid-mask"></div>
	<div class="datagrid-mask-msg">
	  <div style="font-weight:bold; color:red;">对不起，您使用的浏览器版本太低（Internet Explorer <span id="browser_version"></span>），访问系统会存在速度和兼容性的问题！</div>
	  <div style="font-weight:bold; color:green;">我们推荐您使用最新版的 Chrome、Firefox、或 IE9 及以上版本的浏览器。</div>
	  <div><a href="http://www.google.cn/chrome/intl/zh-CN/landing_chrome.html" target="_blank">下载 Chrome 浏览器</a></div>
	  <div><a href="http://firefox.com.cn/" target="_blank">下载 Firefox 火狐浏览器</a> </div>
	  <div><a href="http://windows.microsoft.com/zh-CN/internet-explorer/products/ie/home/" target="_blank">下载 Internet Explorer 9 浏览器</a> </div>
	</div>
</div>
<div id="loginDialog" class="inner" style="display: none;">
	<div id="loginTabs" class="easyui-tabs" data-options="fit:true,border:false">
		<div data-options="title: '登陆', refreshable: false,selected:true">
			<form id="form-login" class="easyui-form">
			 	<table id="login_box">
					<tr>
						<th style="width:90px;text-align:right;">账&nbsp;&nbsp;号：</th>
						<td><input name="account" value="admin" class="easyui-validatebox" style="width:250px;height:25px;" type="text" data-options="required:true, prompt: '登陆账号'" ></td>
					</tr>	
					<tr>
						<th style="width:90px;text-align:right;">账&nbsp;&nbsp;号：</th>
						<td><input name="password" value="admin" class="easyui-validatebox" style="width:250px;height:25px;" type="text" data-options="required:true, prompt: '登陆账号'" ></td>
					</tr>	
					<tr>
						<th style="width:90px;text-align:right;">验证码：</th> 
						<td>
							<input name="kaptcha" class="easyui-validatebox" style="float:left;width:120px;height:25px;" type="text" >
							<img id="kaptcha" src="${pageContext.request.contextPath}/images/loading5.gif">
						</td>
					</tr>	
			 	</table>
			</form>
		</div>
	</div>
</div>