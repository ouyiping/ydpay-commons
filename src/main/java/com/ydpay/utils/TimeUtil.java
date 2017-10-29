package com.ydpay.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

public class TimeUtil {

	private static Logger logger = Logger.getLogger(TimeUtil.class);

	public static String getTime() throws ParseException {
		return String.valueOf(System.currentTimeMillis());
	}

	/**
	 * long类型时间转换成"MM/dd/yyyy HH:mm:ss"
	 * 
	 * @param timeStr
	 *            时间格式的时间
	 * @param timeFormat
	 *            时间格式
	 * @return 时间格式的时间
	 */
	public static Date convertTimeFormat(String timeStr, String timeFormat) {
		if (timeStr.length() == 10)
			timeStr = timeStr + "000";
		long time = Long.valueOf(timeStr);
		DateFormat sdf = new SimpleDateFormat(timeFormat);
		String timestr = sdf.format(new Date(time));

		try {
			return sdf.parse(timestr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getTimestamp() {
		return getDateFormat("yyyy-MM-dd HH:mm:ss");
	}

	public static String getTimestamp(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String str = sdf.format(date);
		return str;
	}

	public static String getDateFormat(String format) {
		Date date = new Date();
		return getTimestamp(date, format);
	}

	/**
	 * 获取当前年份
	 * 
	 * @return
	 * @createTime 2016-1-9
	 * @author shenbin
	 */
	public static int getCurrentYear() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.YEAR);
	}

	/**
	 * 获取当前时间与第二天0点的时间间隔
	 * 
	 * @return
	 * @createTime 2015-12-11
	 * @author shenbin
	 */
	public static long getTimeToNextDateFirstTime() {
		// String nextdata = getNextDateFirstTime();
		return getIntervalBetPointTimeAndCurrentTime("00:00:00");
	}

	/**
	 * 获取指定时间(未来时间,不含日期)和当前时间的最近时间间隔</br>
	 * 
	 * @author shenbin
	 * @CreateTime 2015年5月26日
	 * @param pointtime
	 *            指定时间
	 * @return 时间间隔，单位秒
	 */
	public static long getIntervalBetPointTimeAndCurrentTime(String pointtime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long interval = 0;
		try {
			Date date = new Date();
			Date pointdate = sdf.parse(contactDateHeadToTrail(date, pointtime));
			interval = pointdate.getTime() - date.getTime();
			if (interval < 0) {
				interval = 86400000 + interval;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return interval;
	}

	/**
	 * 获取指定天数前或后的时间
	 * 
	 * @param separatedDay
	 * @return
	 * @createTime 2016-5-27
	 * @author shenbin
	 */
	public static Date getSeparatedDate(int separatedDay) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, separatedDay);
		return c.getTime();
	}

	/**
	 * 取currentTime日期头，与timertrail拼成日期字符串，再转成时间
	 * 
	 * @param currentTime
	 * @param timetrail
	 *            当为Date类型时，取Date时间，可为字符串
	 * @return
	 * @createTime 2015-9-7
	 * @author shenbin
	 */
	public static Date getContactDate(Date currentTime, Object timetrail) {
		try {
			String trail = "00:00:00";
			if (timetrail != null) {
				if (timetrail instanceof Date) {
					SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
					trail = sd.format(timetrail);
				} else
					trail = (String) timetrail;
			}
			String contactDate = contactDateHeadToTrail(currentTime, trail);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.parse(contactDate);
		} catch (ParseException e) {
			logger.error(e);
		}
		return null;
	}

	/**
	 * 获取date时间
	 * 
	 * @param date
	 * @return
	 * @createTime 2015-12-16
	 * @author shenbin
	 */
	public static String getTailOfDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(date);
	}

	/**
	 * 取currentTime日期头，与timertrail拼成日期字符串
	 * 
	 * @author shenbin
	 * @CreateTime 2015年5月26日
	 * @param currentTime
	 *            当前时间
	 * @param timetrail
	 *            需要拼的时间尾巴
	 * @return
	 */
	private static String contactDateHeadToTrail(Date currentTime,
			String timetrail) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String current = sdf.format(currentTime);
		return current + " " + timetrail;
	}

	/**
	 * 依据当前时间，获取第二天零点 </br> 如当前时间是2015-5-26 23:30:00,则获取2015-5-27 00:00:00
	 * 
	 * @author shenbin
	 * @CreateTime 2015年5月26日
	 * @return
	 */
	public static String getNextDateFirstTime() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		return contactDateHeadToTrail(cal.getTime(), "00:00:00");
	}
	/**
	 * 解析订单日期
	 * 20170307转成2017-03-07
	 * @param date
	 * @return
	 */
	public static String getDate(String date){
		String YY = date.substring(0, 4);
		String MM = date.substring(4, 6);
		String RR = date.substring(6, 8);
		return YY+"-"+MM+"-"+RR;
	}
	/**
	 * 解析订单时间
	 * 070809转成09:08:09
	 * @param time
	 * @return
	 */
	public static String getTime(String time) {
		String hh = time.substring(0, 2);
		String mm = time.substring(2, 4);
		String ss = time.substring(4, 6);
		return hh+":"+mm+":"+ss;
	}
}
