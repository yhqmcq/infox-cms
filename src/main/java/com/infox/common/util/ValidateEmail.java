package com.infox.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 说明：验证Email地址
 * <br/>编写者：杨浩泉
 * <br/>日期：2010-3-1
 */
public class ValidateEmail {

	/**
	 * 判断该邮箱地址的格式是否正确
	 * @param mailAddr 待判断的邮箱地址
	 * @return
	 */
	public static boolean checkMail(String mailAddr) {
		String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*" ;
		return match(regex,mailAddr) ;
	}
	/**
	 * 通过Java的正则表达式来判断邮箱地址
	 * @param regex 正则表达式
	 * @param str 待判断的邮箱地址
	 * @return
	 */
	public static boolean match(String regex,String str) {
		Pattern pattern = Pattern.compile(regex) ;
		Matcher matcher = pattern.matcher(str) ;
		return matcher.matches() ;
	}
	
}
