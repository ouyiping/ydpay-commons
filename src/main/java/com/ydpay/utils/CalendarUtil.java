package com.ydpay.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

public class CalendarUtil {
	private static Logger logger =Logger.getLogger(CalendarUtil.class);
	
	//返回下一个工作日日期
	public static String getNowtime(String format){
		if(format == null)
			return null;
		Calendar calendar = Calendar.getInstance();
		return CalendartoString(calendar,format);
	}
	
	public static String getFormatTimeString(String datetime,String format){
		Calendar cal = CalendarFormat(datetime,format);
		return CalendartoString(cal,format);
	}
	
	//返回星期1-7
	public static int getDayofWeek(String datetime){
		return DayofWeek(datetime);
	}
	
	public static void Sleep(long millis){
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static String timeFormatConverter(String timeStr, String timeFormat1, String timeFormat2){
		String resultTimeStr = null;
		try {
			SimpleDateFormat sdf1 = new SimpleDateFormat(timeFormat1);
			SimpleDateFormat sdf2 = new SimpleDateFormat(timeFormat2);
			resultTimeStr = sdf2.format(sdf1.parse(timeStr));
		}
		catch(ParseException e) {
			logger.error("[" + timeFormat1 + "时间格式的时间转 " + timeFormat2 + "时间格式的时间异常！e = " + e +"]");
			e.printStackTrace();
		}
		return resultTimeStr;
	}
	private static String DayofWeekCN(int dateofweek){
		switch(dateofweek){
			case Calendar.SUNDAY:
				return "日";
			case Calendar.MONDAY:
				return "一";
			case Calendar.TUESDAY:
				return "二";
			case Calendar.WEDNESDAY:
				return "三";
			case Calendar.THURSDAY:
				return "四";
			case Calendar.FRIDAY:
				return "五";
			case Calendar.SATURDAY:
				return "六";
			default:
				return null;
		}
	}
	//判断日期为星期几
	//SUNDAY 1
	//
	private static int DayofWeek(String datetime){
		Calendar calendar = CalendarFormat(datetime,"yyyy-MM-dd");
		int dateofweek = calendar.get(Calendar.DAY_OF_WEEK);
		logger.info(datetime + "是星期" + DayofWeekCN(dateofweek));
		return dateofweek;
	}
	
	private static Date DateFormat(String datetime,String format){
		if(datetime==null)
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date;
		try {
			date = sdf.parse(datetime);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static Calendar CalendarFormat(String datetime,String format){
		if(datetime==null)
			return null;
		Date date = DateFormat(datetime,format);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}
	
	//days 正数表示加，负数表述减
	public static Calendar CalendaraddDays(String datetime,int days){
		if(datetime==null)
			return null;
		Calendar calendar = CalendarFormat(datetime,"yyyy-MM-dd HH:mm:ss");
		calendar.add(Calendar.DATE,days);
		return calendar;
	}

	//
	private static String CalendartoString(Calendar calendar,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String dateStr = sdf.format(calendar.getTime());
		return dateStr;
	}

}
 