package com.infox.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static final String FORMAT_STYLE_A = "yyyy-MM";
	public static final String FORMAT_STYLE_B = "MM-dd";
	public static final String FORMAT_STYLE_C = "HH:mm";
	public static final String FORMAT_STYLE_D = "MM-dd HH:mm";
	public static final String FORMAT_STYLE_E = "yyyy-MM-dd HH:mm";
	public static final String FORMAT_STYLE_F = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_STYLE_G = "yyyy-MM-dd";
	public static final String FORMAT_STYLE_H = "MM月dd日";
	public static final String FORMAT_STYLE_I = "yyyyMMdd-HHmmss";

	/** 年-月 */
	public static String formatA(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_STYLE_A);
		return sdf.format(date);
	}

	/** 月-日 */
	public static String formatB(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_STYLE_B);
		return sdf.format(date);
	}

	/** 时-分 */
	public static String formatC(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_STYLE_C);
		return sdf.format(date);
	}

	/** 月-日 时:分 */
	public static String formatD(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_STYLE_D);
		return sdf.format(date);
	}

	/** 年-月-日 时:分 */
	public static String formatE(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_STYLE_E);
		return sdf.format(date);
	}
	
	/** 年-月-日 时:分:秒 */
	public static String formatF(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_STYLE_F);
		if(null != date && !"".equals(date)) {
			return sdf.format(date);
		}
		return null;
	}
	
	/** 年月日 时分秒 */
	public static String formatI(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_STYLE_I);
		if(null != date && !"".equals(date)) {
			return sdf.format(date);
		}
		return null;
	}
	
	/** 年-月-日 时:分:秒 */
	public static Date formatFF(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_STYLE_F);
		try {
			if(null != date && !"".equals(date)) {
				return sdf.parse(date) ;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/** 年-月-日 */
	public static String formatG(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_STYLE_G);
		if(null != date && !"".equals(date)) {
			return sdf.format(date);
		}
		return null;
	}
	
	/** 年-月-日 */
	public static Date formatGG(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_STYLE_G);
		try {
			if(null != date && !"".equals(date)) {
				return sdf.parse(date) ;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/** 月-日 中午 */
	public static String formatH(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_STYLE_H);
		return sdf.format(date);
	}

	/**
	 * 获得上、下午
	 * 
	 * @return
	 */
	public static String getAP_PM() {
		int am_pm = Calendar.getInstance().get(Calendar.AM_PM);
		switch (am_pm) {
		case 0:
			return "上午";
		case 1:
			return "下午";
		}
		return "获得AM_PM失败";
	}

	/**
	 * 获得当前日期的周几
	 * 
	 * @return
	 */
	public static String getWeek() {
		return convertWeek(Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1);
	}

	/**
	 * 获得指定日期的周几
	 * 
	 * @param date
	 *            java.util.Date日期对象
	 * @return
	 */
	public static String getWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return convertWeek(Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1);
	}

	/**
	 * 日期类型转字符串类型
	 * 
	 * @param date
	 * @param dateFormat
	 * @return
	 */
	public static String dateToString(Date date, String dateFormat) {
		if (date == null)
			return "";
		try {
			return new SimpleDateFormat(dateFormat).format(date);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * 获得指定日期的周几
	 * 
	 * @param date
	 *            字符串
	 * @return
	 */
	public static String getWeek(String date) {
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(new SimpleDateFormat(FORMAT_STYLE_G).parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return convertWeek(calendar.get(Calendar.DAY_OF_WEEK) - 1);
	}

	/**
	 * 获得周几
	 * 
	 * @return
	 */
	public static String convertWeek(int week) {

		switch (week) {
		case 0:
			return "星期日";
		case 1:
			return "星期一";
		case 2:
			return "星期二";
		case 3:
			return "星期三";
		case 4:
			return "星期四";
		case 5:
			return "星期五";
		case 6:
			return "星期六";
		}
		return "日前操作失败";
	}

	/**
	 * 比较两个日期大小及计算相隔天数
	 * 
	 * 第一个日期
	 * 
	 * @param firstYear
	 * @param firstMonth
	 * @param firstDay
	 *            第二个日期
	 * @param secondYear
	 * @param secondMonth
	 * @param secondDay
	 */
	public static void compare_date1(int firstYear, int firstMonth, int firstDay, int secondYear, int secondMonth, int secondDay) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(firstYear, firstMonth, firstDay);

		long timeOne = calendar.getTimeInMillis();
		calendar.set(secondYear, secondMonth, secondDay);
		long timeTwo = calendar.getTimeInMillis();
		Date date1 = new Date(timeOne);
		Date date2 = new Date(timeTwo);
		if (date2.equals(date1)) {
			System.out.println("两个日期相同");
		} else if (date2.after(date1)) {
			System.out.println("第二个日期大");
		} else if (date2.before(date1)) {
			System.out.println("第一个日期大");
		}
		long days = (timeOne - timeTwo) / (1000 * 60 * 60 * 24);
		System.out.println(firstYear + "年" + firstMonth + "月" + firstDay + "日");
		System.out.println(secondYear + "年" + secondMonth + "月" + secondDay + "日");
		System.out.println("相隔天数 " + days);
	}

	/**
	 * 比较两个日期大小
	 * 
	 * @param finalDate
	 * @param enddate
	 * @return 返回-1、0、1（小于、相等、大于）
	 */
	public static int compare_date2(String finalDate, String enddate) {
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date d1 = df.parse(finalDate);
			Date d2 = df.parse(enddate);
			int ss = d1.compareTo(d2);
			return ss;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 计算两个日期的间隔(yyyy-MM-dd HH:mm:ss)
	 * @param startdate 开始日期
	 * @param enddate 结束日期
	 * @return
	 */
	public static String getBetweenDataTime(String startdate, String enddate) {
		try {
			SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date begin = dfs.parse(startdate);
			Date end = dfs.parse(enddate);
			long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
			
			long day1 = between / (24 * 3600);
			long hour1 = between % (24 * 3600) / 3600;
			long minute1 = between % 3600 / 60;
			long second1 = between % 60 / 60;
			
			return day1 + "天 " + hour1 + "小时 " + minute1 + "分 " + second1 + "秒 ";
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 计算两个日期的间隔(yyyy-MM-dd)
	 * @param startdate 开始日期
	 * @param enddate 结束日期
	 * @return
	 */
	public static String getBetweenDays(String startdate, String enddate) {
		try {
			SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd");
			Date begin = dfs.parse(startdate);
			Date end = dfs.parse(enddate);
			long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
			
			long day1 = between / (24 * 3600);
			
			return day1 + "天";
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 计算两个日期的间隔(HH:mm:ss)
	 * @param startdate 开始日期
	 * @param enddate 结束日期
	 * @return
	 */
	public static String getBetweenTime(String startdate, String enddate) {
		try {
			SimpleDateFormat dfs = new SimpleDateFormat("HH:mm:ss");
			Date begin = dfs.parse(startdate);
			Date end = dfs.parse(enddate);
			long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒

			long hour1 = between % (24 * 3600) / 3600;
			long minute1 = between % 3600 / 60;
			long second1 = between % 60 / 60;

			return hour1 + "小时 " + minute1 + "分 " + second1 + "秒 ";
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 计算年龄精确到年月日
	 * @param birthday
	 * @return
	 */
	@SuppressWarnings("unused")
	public static String getBabyAge(String birthday) {
		String age = "";
		int day = 0;
		int y = 0;
		int m = 0;
		int d = 0;

		if (birthday != null && birthday.length() == 10) {
			String[] time = birthday.split("-");
			y = Integer.parseInt(time[0]);
			m = Integer.parseInt(time[1]);
			d = Integer.parseInt(time[2]);
	

		Calendar selectDate = Calendar.getInstance();
		Calendar currentDate = Calendar.getInstance();
		selectDate.set(Calendar.YEAR, y);
		selectDate.set(Calendar.MONTH, m - 1);
		selectDate.set(Calendar.DAY_OF_MONTH, d);
		//上一个月
		int lastMonth=(currentDate.get(Calendar.MONTH)+1)-1; 
		int years = currentDate.get(Calendar.YEAR) - selectDate.get(Calendar.YEAR);
		int months = currentDate.get(Calendar.MONTH) - selectDate.get(Calendar.MONTH);
		int days = currentDate.get(Calendar.DAY_OF_MONTH)- selectDate.get(Calendar.DAY_OF_MONTH);
		if (days < 0) {
			months = months - 1;
			switch (lastMonth) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				day = 31;
				break;
			case 4:
			case 6:
			case 9:
			case 11:
				day = 30;
				break;
			default:
				if (Calendar.YEAR % 4 == 0 && Calendar.YEAR % 100 != 0 || Calendar.YEAR % 400 == 0) {
					day = 28;
				} else {
					day = 29;
				}
				break;
			}
			days = days + day;
		}
		if (months < 0) {
			years = years - 1;
			months = months + 12;
		}
		if (years < 0) {
			years = 0;
		}
		if(years==0){
			if(months==0){
				if(days==0){
					age="今天是宝宝的出生日期";
				}else{
					age=days + "天";
				}
			}else{
				if(days==0){
					age=months + "个月";
				}else{
					age =months + "个月又" + days + "天";
				}
			}
		}else{
			if(months==0){
				if(days==0){
					age = years + "岁";
				}else{
					age = years + "岁又" + days + "天";
				}
			}else{
				if(days==0){
					age = years + "岁" + months + "个月";
				}else{
					age = years + "岁" + months + "个月又" + days + "天";
				}
				
			}
		}
		
	  }
		return age;
	}

	public static void main(String[] args) {
		String startdate = "2013-10-21 17:10:11";
		String enddate = "2013-10-21 17:10:11";

		System.out.println(getBetweenDataTime(startdate, enddate));
		System.out.println(getBetweenDays(startdate, enddate));
		
		
		String startdate1 = "08:30:30";
		String enddate1 = "09:50:30";
		System.out.println(getBetweenTime(startdate1, enddate1));
		
		System.out.println(getBabyAge("1988-05-11"));
	}

}
