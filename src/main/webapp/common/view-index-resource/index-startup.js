$(function(){
	$.util.namespace("mainpage");
	//浏览器版本过低则直接退出，不在执行后面代码。
	if(window.mainpage.browse()) {return}
	
	
	window.mainpage.instMainMenus();
	window.mainpage.instFavoMenus();
	window.mainpage.instTimerSpan();
	window.mainpage.bindNavTabButtonEvent();
	window.mainpage.bindToolbarButtonEvent();
	window.mainpage.bindMainTabButtonEvent();
	window.mainpage.online();
	
	var layout = $("#mainLayout"), navTab = $("#navTab");
	 
	$.util.exec(function () {
		if ($.util.browser.webkit) { navTab.tabs("select", 1).tabs("select", 0); }
        layout.removeClass("hidden").layout("resize");

        var size = $.util.windowSize();
        //  判断当浏览器窗口宽度小于像素 1280 时，右侧 region-panel 自动收缩
        if (size.width < 1280) { layout.layout("collapse", "east"); }
        
        $("#maskContainer-loading").remove();
        
        loginView();
        
		
	},100);
});

/**
 * 浏览器版本检测
 */
window.mainpage.browse = function() {
	var lameIE = $.util.browser.msie && $.util.browser.version < 9;
	if(lameIE || lameIE==undefined) {
		var $mask = $('#maskContainer-loading .datagrid-mask');
		var $mask_msg = $('#maskContainer-loading .datagrid-mask-msg');
		$mask.css({ display: 'block' });
		$mask_msg.css({ display: 'block' });
	} else {
		$('#browser_version').html($.util.browser.version);
		
		var $mask = $('#maskContainer-browse .datagrid-mask');
		var $mask_msg = $('#maskContainer-browse .datagrid-mask-msg');
		
		$mask.css({
			display: 'block'
		});
		$mask_msg.css({
			display: 'block',
			width: 560,
			height: 90,
			'z-index': 9999, //最顶层，用户才能点到链接
			padding: '10px 10px 10px 60px', //覆盖原来的样式
			background: '#ffc url("${ctx}/jquery-easyui/themes/default/images/messager_warning.gif") no-repeat scroll 10px 10px', //覆盖原来的样式
			left: ($.util.windowSize().width/4),
			top: ($.util.windowSize().height/2),
			margin: '10px 0px 0px 52.5px'
		});
		
		$(window).resize(function() {
			$mask.css({
				width:  $.util.windowSize().width, 
				height: $.util.windowSize().height
			});
			$mask_msg.css({
				left: ($.util.windowSize().width/4),
				top: ($.util.windowSize().height/2)-100,
			});
		}).resize();
		return true;
	} 
};