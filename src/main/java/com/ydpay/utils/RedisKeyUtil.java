package com.ydpay.utils;

/**
 * redis缓存前缀管理工具类
 * 
 */
public class RedisKeyUtil {

	/*********************************微信模块redis key  begin*******************************************************/
	public static final String weixinjssdktoken = "weixinjssdktoken";
	public static final String weixinPublicSignal = "weixinPublicSignal";
	public static final String weixinSmallRoutine = "weixinSmallRoutine";
	
	/*********************************微信模块redis key  end*******************************************************/
	public static final String systemDockingInfo = "systemDockingInfo_prefix_";
	
	public static final String SESSION_PREFIX = "user_login_session_prefix_";    //勿动，与登录session前缀保持一致
	
	public static String getKey(String prefix, String... strings) {
		String key = prefix;
		for (String row : strings) {
			key = key.concat("_").concat(row);
		}
		return key;
	}
}
