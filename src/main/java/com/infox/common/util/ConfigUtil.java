package com.infox.common.util;

import java.util.ResourceBundle;

/**
 * 项目参数工具类
 * 
 * @author 杨浩泉
 * 
 */
public class ConfigUtil {

	private static final ResourceBundle bundle = java.util.ResourceBundle
			.getBundle("resources/config");

	/**
	 * 通过键获取值
	 * 
	 * @param key
	 * @return
	 */
	public static final String get(String key) {
		return bundle.getString(key);
	}

}
