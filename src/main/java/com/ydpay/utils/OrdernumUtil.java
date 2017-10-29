package com.ydpay.utils;

import com.ydpay.cache.redis.RedisCache;

/**
 * 订单号获取工具
 * 
 */
public class OrdernumUtil {

	/**
	 * 年份基准点
	 */
	public final static int BASE_YEAR = 2015;

	/**
	 * 根据订单类型生成订单号
	 * 
	 * @param ordertypeid
	 */
	public static String get(String ordertypeid) {
		StringBuffer strBuffer = new StringBuffer(ordertypeid);
		String key = "ordernumber_" + ordertypeid + "_"
				+ TimeUtil.getDateFormat("yyyyMMddHH");
		String seriesNumber = seriesNumber(key);
		strBuffer.append(TimeUtil.getDateFormat("yyyyMMddHH") + seriesNumber);
		return strBuffer.toString();
	}

	/**
	 * 根据订单类型生成订单号
	 * 
	 * @param ordertypeid
	 */
	public static String get() {
		StringBuffer strBuffer = new StringBuffer();
		String key = "compayorder_ordernumber_" + TimeUtil.getDateFormat("yyyyMMddHH");
		String seriesNumber = seriesNumber(key);
		strBuffer.append(ServerParamsUtil.get("ordernumber_prefix"));
		strBuffer.append(TimeUtil.getDateFormat("yyyyMMddHH") + seriesNumber);
		return strBuffer.toString();
	}
	
	/**
	 * 根据订单类型产生唯一序列号
	 * 
	 * @param key
	 * @return
	 */
	public static String seriesNumber(String key) {
		long count = RedisCache.getInstance().incr(key);
		return StringUtil.PadLeft(count + "", 6, "0");
	}

	public static String identifier(String ordertypeid) {
		return get(ordertypeid);
	}
}
