package com.ydpay.dao.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class HashCache {

	private static ConcurrentMap<String, ExpiryMap<String, String>> cacheMap = new ConcurrentHashMap<String, ExpiryMap<String, String>>();
	private static ConcurrentMap<String, ExpiryMap<String, Boolean>> bCacheMap = new ConcurrentHashMap<String, ExpiryMap<String, Boolean>>();

	/**
	 * 
	 * @param type
	 * @return
	 */
	private static ExpiryMap<String, String> getExpiryMap(String type) {
		if (cacheMap.containsKey(type))
			return cacheMap.get(type);
		return null;
	}

	public static String get(String type, String key) {
		ExpiryMap<String, String> expiryMap = getExpiryMap(type);
		if (expiryMap == null)
			return null;
		return expiryMap.get(key);
	}

	public static synchronized void put(String type, String key, String value) {
		ExpiryMap<String, String> expiryMap = getExpiryMap(type);
		if (expiryMap == null) {
			expiryMap = new ExpiryMap<String, String>();
			cacheMap.put(type, expiryMap);
		}
		removeB(type, key);
		expiryMap.put(key, value);
	}

	public static void remove(String type, String key) {
		ExpiryMap<String, String> expiryMap = getExpiryMap(type);
		if (expiryMap == null)
			return;
		if (expiryMap.containsKey(key))
			expiryMap.remove(key);
		putB(type, key, true);
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

	/**
	 * 
	 * @param type
	 * @return
	 */
	private static ExpiryMap<String, Boolean> getBExpiryMap(String type) {
		if (bCacheMap.containsKey(type))
			return bCacheMap.get(type);
		return null;
	}

	public static boolean getB(String type, String key) {
		ExpiryMap<String, Boolean> expiryMap = getBExpiryMap(type);
		if (expiryMap == null)
			return false;
		return expiryMap.get(key, false);
	}

	public static synchronized void putB(String type, String key, Boolean value) {
		ExpiryMap<String, Boolean> expiryMap = getBExpiryMap(type);
		if (expiryMap == null) {
			expiryMap = new ExpiryMap<String, Boolean>();
			bCacheMap.put(type, expiryMap);
		}
		expiryMap.put(key, value);
	}

	public static void removeB(String type, String key) {
		ExpiryMap<String, Boolean> expiryMap = getBExpiryMap(type);
		if (expiryMap == null)
			return;
		if (expiryMap.containsKey(key))
			expiryMap.remove(key);
	}
}
