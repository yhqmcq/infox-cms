package com.infox.common.util;

/*
 * CookieSupport.java
 * Copyright (C) 2007-3-19  <JustinLei@gmail.com>
 *
 *        This program is free software; you can redistribute it and/or modify
 *        it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation; either version 2 of the License, or
 *     (at your option) any later version.
 *
 *       This program is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *        GNU General Public License for more details.
 *
 */
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

/**
 * @author TangLei <justinlei@gmail.com>
 * @date 2008-12-17
 */
public class CookieSupport {
	private CookieSupport() {
	}

	/**
	 * 写cookies
	 * 
	 * @param response
	 * @param cookieParams
	 * @param maxAge
	 */
	public static final void writeCookies(HttpServletResponse response, Map<String, String> cookieParams, int maxAge) {
		if (cookieParams == null || cookieParams.size() == 0)
			return;
		Set<String> keySet = cookieParams.keySet();
		for (String key : keySet) {
			Cookie cookie = new Cookie(key, cookieParams.get(key));
			cookie.setMaxAge(maxAge);
			response.addCookie(cookie);
		}
	}

	/**
	 * 删除所有的cookies
	 * 
	 * @param request
	 * @param response
	 */
	public static final void removeAllCookies(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null || cookies.length == 0)
			return;
		Map<String, String> cookiesMap = new HashMap<String, String>();
		for (Cookie cookie : cookies) {
			cookiesMap.put(cookie.getName(), "");
		}
		writeCookies(response, cookiesMap, 0);
	}

	/**
	 * 读取cookies
	 * 
	 * @param request
	 * @param cookieName
	 * @return
	 */
	public static final Cookie[] readCookies(HttpServletRequest request, String cookieName) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null || cookies.length == 0)
			return null;
		if (StringUtils.isEmpty(cookieName))
			return cookies;
		for (int i = 0; i < cookies.length; i++) {
			if (cookies[i].getName().equals(cookieName))
				return new Cookie[] { cookies[i] };
		}
		return null;
	}
}
