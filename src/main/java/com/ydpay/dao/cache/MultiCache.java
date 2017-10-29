package com.ydpay.dao.cache;

import com.ydpay.cache.redis.RedisCache;
import com.ydpay.utils.StringUtil;

/**
 * 
 * 二级缓存基础版本
 */
public class MultiCache {

	/**
	 * 获取缓存数据，如果本地不存在，则向redis获取，如果存在,则直接使用<br/>
	 * 本地缓存默认5分钟有效 <br/>
	 * 如果redis中也不存在,则标记这个key,在一个有效期范围内，不再从redis获取
	 * 
	 * @param type
	 * @param key
	 * @return
	 */
	public static String get(String type, String key) {
		String value = HashCache.get(type, key);
		if (StringUtil.isEmpty(value) && !HashCache.getB(type, key)) {
			value = RedisCache.getInstance().get(key);
			if (StringUtil.isNotEmpty(value)) {
				HashCache.put(type, key, value);
			} else {
				HashCache.putB(type, key, true);
			}
		}
		return value;
	}

	public static boolean getB(String type, String key) {
		return HashCache.getB(type, key);
	}

	/**
	 * 设置缓存数据，将数据保存到本地，同时同步到redis<br/>
	 * 本地缓存默认5分钟有效<br/>
	 * 目前不支持修改其他节点数据
	 * 
	 * @param type
	 * @param key
	 * @param value
	 */
	public static void put(String type, String key, String value) {
		HashCache.put(type, key, value);
		HashCache.removeB(type, key);// 清除不存在标志
		RedisCache.getInstance().set(key, value);
	}

	/**
	 * 删除缓存数据，删除本地缓存，同时删除redis缓存<br/>
	 * <h3>目前不支持删除其他节点数据</h3><br/>
	 * 
	 * @param type
	 * @param key
	 */
	public static void remove(String type, String key) {
		HashCache.remove(type, key);
		HashCache.removeB(type, key);
		RedisCache.getInstance().remove(key);
	}

	public static boolean getB(String key) {
		return getB("base", key);
	}

	public static String get(String key) {
		return get("base", key);
	}

	public static void put(String key, String value) {
		put("base", key, value);
	}

	public static void remove(String key) {
		remove("base", key);
	}
}
