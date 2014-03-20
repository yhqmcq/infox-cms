package com.infox.common.interceptors;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.infox.common.util.Constants;
import com.infox.common.util.HttpRequestDeviceUtils;
import com.infox.common.web.page.LoginInfoSession;

/**
 * @程序编写者：杨浩泉
 * @日期：2013-7-15
 * @类说明：权限拦截器
 */
public class SecurityInterceptor implements HandlerInterceptor {

	private List<String> excludeUrls;// 不需要拦截的资源

	public List<String> getExcludeUrls() {
		return excludeUrls;
	}

	public void setExcludeUrls(List<String> excludeUrls) {
		this.excludeUrls = excludeUrls;
	}

	/**
	 * 完成页面的render后调用
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception) throws Exception {
	}

	/**
	 * 在调用controller具体方法后拦截
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView) throws Exception {
	}

	/**
	 * 在调用controller具体方法前拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String url = requestUri.substring(contextPath.length()+1);
		if (url.indexOf("/baseController/") > -1 || excludeUrls.contains(url)) {
			return true;
		}
		
		
		// 如果要访问的资源是不需要验证的
		for (String str : excludeUrls) {
			if(url.indexOf(str) > -1) {
				return true ;
			} 
		}
		
		LoginInfoSession sessionInfo = (LoginInfoSession) request.getSession().getAttribute(Constants.SESSION_INFO_NAME);
		
		if (sessionInfo == null || sessionInfo.getEmp().getId().equalsIgnoreCase("")) {// 如果没有登录或登录超时
			if(HttpRequestDeviceUtils.isAjaxReqeuest(request)){
				//response.setContentType("text/html;charset=utf-8");
				//PrintWriter out = response.getWriter();
				//out.print("您还没有登录或登录已超时，请重新登录，然后再刷新本功能！");
				//out.flush();
				//out.close();
				//return false;
				return true;
			} else {
				//request.setAttribute("msg", "您还没有登录或登录已超时，请重新登录，然后再刷新本功能！");
				//request.getRequestDispatcher("/common/errors/noSession.jsp").forward(request, response);
				return true;
			}
		}
		
		if(sessionInfo.getEmp().getAccount().equals("admin")) {
			return true;
		}
		
		if (!sessionInfo.getResourceList().contains(url)) {// 如果当前用户没有访问此资源的权限
			if(HttpRequestDeviceUtils.isAjaxReqeuest(request)){
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out = response.getWriter();
				out.print("您没有访问此资源的权限！请联系超管赋予您<br/>[" + url + "]<br/>的资源访问权限！");
				out.flush();
				out.close();
				//return false;
				return true;
			} else {
				request.setAttribute("msg", "您没有访问此资源的权限！请联系超管赋予您<br/>[" + url + "]<br/>的资源访问权限！");
				request.getRequestDispatcher("/common/errors/noSecurity.jsp").forward(request, response);
				return true;
			}
		}

		return true;
	}
}
