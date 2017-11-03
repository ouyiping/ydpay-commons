package com.ydpay.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.ydpay.cache.redis.RedisCache;

/**
 * 获取每个表的主键最大值+1 工具类
 */
public class MaxIdUtil {
	private static String dateStr = "";

	private static Map<String, Integer> keyMap = new HashMap<String, Integer>();

	/**
	 * 获取redis里每个表的主键最大值+1
	 * 
	 * @param primaryKey
	 *            主键的标识 命名规范：config-service.数据库名.表名.表的主键id名
	 *            如：config-service.mgbase40.companystaff.companystaffid
	 * @return 返回redis里键最大值+1
	 * @throws Exception
	 */
	public static long getMaxId(String primaryKey) {
		return RedisCache.getInstance().incr(primaryKey);
	}

	public static long getMaxId(String schemaname, String tablename,
			String columnname) {
		return getMaxId(getPrimarykey(schemaname, tablename, columnname));
	}

	public static String getPrimarykey(String schemaname, String tablename,
			String columnname) {
		return "config-service." + schemaname + "." + tablename + "."
				+ columnname;
	}

	public static long getMaxPrimaryKey(String schemaname, String tablename,
			String columnname) {
		return ObjectParser.toLong(getkey(getPrimarykey(schemaname,
				tablename, columnname)));
	}

	public static String getkey(String primaryKey) {
		return getkey(primaryKey, 2);
	}

	/**
	 * @param primaryKey
	 * @return <每秒归零>
	 * @throws Exception
	 */
	public synchronized static String getkey(String primaryKey, int length) {
		try {
			DateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
			String str = sdf.format(new Date());
			if (keyMap.containsKey(primaryKey)) {
				if (!dateStr.equals(str)) {
					keyMap.put(primaryKey, 0);
				}
			} else {
				keyMap.put(primaryKey, 0);
			}
			dateStr = str;
			Integer index = keyMap.get(primaryKey) + 1;
			keyMap.put(primaryKey, index);
			return dateStr + StringUtil.PadLeft(index + "", length, "0");
		} catch (Exception e) {
			YdpayLogger.error("获取key失败", e);
			return "0";
		}
	}
	
	/**
	 * 获取uuid主键
	 * @return
	 */
	public static String getUUIDPrimaryKey(){
		return UUID.randomUUID().toString().replace("-", "");
	}
}
