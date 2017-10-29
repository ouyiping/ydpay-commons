package com.ydpay.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ydpay.security.MD5Util;

public class CommonUtil {

	private static Gson gson = new GsonBuilder().serializeNulls()
			.setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	private static Logger logger = Logger.getLogger(CommonUtil.class);

	public static String toJson(Map<String, Object> dataMap) {
		return gson.toJson(dataMap);
	}

	public static String toJson(Object object) {
		// gson.fromJson(json, typeOfT)
		return gson.toJson(object);
	}

	public static Gson getGson() {
		return gson;
	}

	public static void error(String message, Throwable e) {
		logger.error(message, e);
	}

	public static void error(String message) {
		logger.error(message);
	}

	/*
	 * 基础数据检查
	 * 
	 * @param data
	 * 
	 * @return
	 */
	public static Map<String, Object> baseDataCheck(String data)
			throws Exception {
		if (data == null || data.equals("")) {
			throw new MgException(33, "接口输入参数不能为空");
		}
		Map<String, Object> dataMap = null;
		try {
			dataMap = JSONObject.parseObject(data, new TypeToken<Map<String, Object>>() {
			}.getType());
		} catch (Exception e) {
			logger.error("CommonUtil->baseDataCheck->输入参数结构错误: " + data);
			throw new MgException(30, "解析参数出错, 请检查json的格式是否正确!", e);
		}

		return dataMap;
	}

	/*
	 * 基础List数据检查
	 * 
	 * @param data
	 * 
	 * @return
	 */
	public static List<Map<String, Object>> baseListDataCheck(String data)
			throws Exception {
		if (data == null || data.equals("")) {
			throw new MgException(33, "接口输入参数不能为空");
		}
		List<Map<String, Object>> dataListMap = null;
		try {
			dataListMap = gson.fromJson(data,
					new TypeToken<List<Map<String, Object>>>() {
					}.getType());
		} catch (Exception e) {
			logger.error("CommonUtil->baseListDataCheck->输入参数结构错误: " + data);
			throw new MgException(30, "解析参数出错，请检查JSON格式", e);
		}
		return dataListMap;
	}

	public static String makeSign(String appKey, String method, String data,
			String appSecret) {
		String format = "json";
		String v = "1.0";
		String timestamp = TimeUtil.getTimestamp();
		String md5sign = MD5Util.string2MD5(
				appKey + method + format + data + v + timestamp + appSecret)
				.toLowerCase();
		return md5sign;
	}

	public static void checkStringMapExist(Map<String, Object> dataMap,
			String key) {
		if (!dataMap.containsKey(key)
				|| StringUtil.isEmpty((String) dataMap.get(key))) {
			throw new MgException(34, "缺少必要参数:" + key + "不能为空");
		}
	}

	public static void checkStringMapExist(Map<String, Object> dataMap,
			String key, String name) {
		if (!dataMap.containsKey(key)
				|| StringUtil.isEmpty((String) dataMap.get(key))) {
			throw new MgException(34, "缺少必要参数:" + name + "不能为空");
		}
	}

	public static void checkObjectExist(Map<String, Object> dataMap, String key) {
		if (!dataMap.containsKey(key)) {
			throw new MgException(34, "缺少必要参数:" + key + "不能为空");
		}
	}

	public static void checkLongMapExist(Map<String, Object> dataMap, String key) {
		if (!dataMap.containsKey(key)
				|| ObjectParser.toLong(dataMap.get(key)) <= 0) {
			throw new MgException(34, "缺少必要参数:" + key + "不能为空");
		}
	}

	public static void checkLongMapExist(Map<String, Object> dataMap,
			String key, String name) {
		if (!dataMap.containsKey(key)
				|| ObjectParser.toLong(dataMap.get(key)) <= 0) {
			throw new MgException(34, "缺少必要参数:" + name + "不能为空");
		}
	}

	public static void checkLongMapExist_0(Map<String, Object> dataMap,
			String key) {
		if (!dataMap.containsKey(key)
				|| ObjectParser.toLong(dataMap.get(key)) < 0) {
			throw new MgException(34, "缺少必要参数:" + key + "不能为空");
		}
	}

	public static void checkIntegerMapExist(Map<String, Object> dataMap,
			String key) {
		if (!dataMap.containsKey(key)
				|| ObjectParser.toInteger(dataMap.get(key)) <= 0) {
			throw new MgException(34, "缺少必要参数:" + key + "不能为空");
		}
	}

	public static void checkIntegerMapExist(Map<String, Object> dataMap,
			String key, String name) {
		if (!dataMap.containsKey(key)
				|| ObjectParser.toInteger(dataMap.get(key)) <= 0) {
			throw new MgException(34, "缺少必要参数:" + name + "不能为空");
		}
	}

	public static void checkIntegerMapExist_0(Map<String, Object> dataMap,
			String key) {
		if (!dataMap.containsKey(key)
				|| ObjectParser.toInteger(dataMap.get(key)) < 0) {
			throw new MgException(34, "缺少必要参数:" + key + "不能为空");
		}
	}

	public static void checkDoubleMapExist(Map<String, Object> dataMap,
			String key) {
		if (!dataMap.containsKey(key)
				|| ObjectParser.toDouble(dataMap.get(key)) <= 0) {
			throw new MgException(34, "缺少必要参数:" + key + "不能为空");
		}
	}

	public static void checkDoubleMapExist_0(Map<String, Object> dataMap,
			String key) {
		if (!dataMap.containsKey(key)
				|| ObjectParser.toDouble(dataMap.get(key)) < 0) {
			throw new MgException(34, "缺少必要参数:" + key + "不能为空");
		}
	}

	public static void checkDoubleMapExist(Map<String, Object> dataMap,
			String key, String name) {
		if (!dataMap.containsKey(key)
				|| ObjectParser.toDouble(dataMap.get(key)) <= 0) {
			throw new MgException(34, "缺少必要参数:" + name + "不能为空");
		}
	}

	public static void checkDoubleMapExist_0(Map<String, Object> dataMap,
			String key, String name) {
		if (!dataMap.containsKey(key)
				|| ObjectParser.toDouble(dataMap.get(key)) < 0) {
			throw new MgException(34, "缺少必要参数:" + name + "不能为空");
		}
	}

	public static int getOrderTypeId(String ordernum) {
		return ObjectParser.toInteger(ordernum.substring(0, 3));
	}

	/**
	 * <pre>
	 * 根据ydpay开放平台API访问协议参数，进行签名
	 * </pre>
	 * 
	 * @param appid
	 * @param method
	 * @param format
	 * @param v
	 * @param timestamp
	 * @param session
	 * @param data
	 * @param target_appid
	 * @param secretKey
	 * @return(设定参数)
	 * @return String(返回值说明)
	 * @author Zhanghaifeng 2016年4月2日
	 */
	public static String signydpayAPIRequest(String appid, String method,
			String format, String v, String timestamp, String session,
			String data, String target_appid, String secretKey) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(Constants.APPID, appid);
		params.put(Constants.METHOD, method);
		params.put(Constants.FORMAT, format);
		params.put(Constants.V, v);
		params.put(Constants.TIMESTAMP, timestamp);
		params.put(Constants.SESSION, session);
		params.put(Constants.DATA, data);
		params.put(Constants.TARGET_APPID, target_appid);

		return signRequest(params, secretKey);
	}

	/**
	 * 
	 * <pre>
	 * 根据请求参数的key按ASCII码顺序进行排序,并进行md5签名
	 * </pre>
	 * 
	 * @param params
	 * @param secretKey
	 * @return(设定参数)
	 * @return String(返回值说明)
	 * @author Zhanghaifeng 2016年4月2日
	 */
	public static String signRequest(Map<String, String> params,
			String secretKey) {
		// 第一步：把参数key按ASCII码顺序进行排序
		String[] keys = params.keySet().toArray(new String[0]);
		Arrays.sort(keys);

		// 第二步：把所有参数名和参数值串在一起
		StringBuilder query = new StringBuilder();
		query.append(secretKey);
		for (String key : keys) {
			String value = params.get(key);
			if (!StringUtil.isEmpty(value)) {
				query.append(value);
			}
		}
		query.append(secretKey);
		YdpayLogger.info(query.toString());
		// 第三步: 进行md5签名
		return MD5Util.string2MD5(query.toString());
	}
	
	public static String asciiRequest(Map<String, String> params) {
		// 第一步：把参数key按ASCII码顺序进行排序
		String[] keys = params.keySet().toArray(new String[0]);
		Arrays.sort(keys);
		
		// 第二步：把所有参数名和参数值串在一起
		StringBuilder query = new StringBuilder();
		for (String key : keys) {
			String value = params.get(key);
			if (!StringUtil.isEmpty(value)) {
				query.append(key+"="+value+"&");
			}
		}
		query.delete(query.length()-1, query.length());
		YdpayLogger.info(query.toString());
		// 第三步: 进行md5签名
		return query.toString();
	}

	/**
	 * 
	 * <pre>
	 * 对ydpay开放平台API访问协议参数进行签名验证
	 * </pre>
	 * 
	 * @param appid
	 * @param method
	 * @param format
	 * @param v
	 * @param timestamp
	 * @param session
	 * @param data
	 * @param target_appid
	 * @param secretKey
	 * @param sign
	 * @return(设定参数)
	 * @return boolean(返回值说明)
	 * @author Zhanghaifeng 2016年4月2日
	 */
	public static boolean verifyydpayAPISign(String appid, String method,
			String format, String v, String timestamp, String session,
			String data, String target_appid, String secretKey, String sign) {
		String verifySign = signydpayAPIRequest(appid, method, format, v,
				timestamp, session, data, target_appid, secretKey);

		return verifySign.equals(sign);
	}

	public static boolean verifyOldydpayAPIRequest(String appid,
			String method, String format, String v, String timestamp,
			String session, String data, String secretKey, String sign) {

		String verifySign = MD5Util.string2MD5(appid + method + format + data
				+ v + timestamp + session + secretKey);
		return verifySign.equals(sign);
	}
	/**
	 * list<Map>转list<class>方法
	 * @param list map对象集合
	 * @param t 转换对象类
	 * @author tangchanghu
	 * @return
	 */
	public static  <T> List<T> listMapToListclass(List<Map<String, Object>> list,Class<T> objectClass)throws Exception{
   	 	List<T> listClass = new ArrayList<T>();
   	 	if (list.size()>0) {
	   		 //list转json
	   		 String listJson = JSONObject.toJSONString(list);
	   		 //json转对象
	   		 listClass = JSONObject.parseArray(listJson, objectClass);
		}
		return listClass;
 	}
	
	/**
	 * list<Map>转list<class>方法
	 * 对象集合转Map集合
	 * @param list 对象集合
	 * @author tangchanghu
	 * @param <T>
	 * @return
	 */
	public static <T> List<Map<String, Object>> listClassToListMap(List<T> list) throws Exception{
   	 	List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
   	 	if (list.size()>0) {
	   		 //list转json
	   		 String listJson = JSONObject.toJSONString(list);
	   		 //json转list<Map>
	   		 listMap = getGson().fromJson(listJson, new TypeToken<List<Map<String, Object>>>(){}.getType());
		}
		return listMap;
 	}
	/**
	 * <class>对象转<Map>
	 * @param t
	 * @author tangchanghu
	 * @return
	 */
	public static <T> Map<String, Object> classToMap(T t) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		if (t!=null) {
			String jsonString = JSONObject.toJSONString(t);
			map = getGson().fromJson(jsonString, new TypeToken<Map<String, Object>>(){}.getType());
		}
		return map;
	}
	/**
	 * <class>对象转JsonObject
	 * @param <T>
	 * @author tangchanghu
	 * @return
	 */
	public static <T> JSONObject classToJsonObject(T t)throws Exception{
		JSONObject jsonObject = new JSONObject();
		if (t!=null) {
			String jsonString = JSONObject.toJSONString(t);
			jsonObject = JSONObject.parseObject(jsonString);
		}
		return jsonObject;
	}
	/**
	 * <Map>转<class>对象
	 * @param map Map对象
	 * @param objectClass Object对象
	 * @author tangchanghu
	 * @return
	 */
	public static <T> T MapToclass(Map<String, Object> map,Class<T> objectClass) throws Exception{
		String jsonString = JSONObject.toJSONString(map);
		T t =   JSONObject.parseObject(jsonString,objectClass);
		return t;
	}

	/**
	 * 
	 * <pre>
	 * 对masget开放平台API访问协议参数进行签名验证
	 * </pre>
	 * 
	 * @param appid
	 * @param method
	 * @param format
	 * @param v
	 * @param timestamp
	 * @param session
	 * @param data
	 * @param target_appid
	 * @param secretKey
	 * @param sign
	 * @return(设定参数)
	 * @return boolean(返回值说明)
	 * @author Zhanghaifeng 2016年4月2日
	 */
	public static boolean verifyMasgetAPISign(String appid, String method,
			String format, String v, String timestamp, String session,
			String data, String target_appid, String secretKey, String sign) {
		String verifySign = signMasgetAPIRequest(appid, method, format, v,
				timestamp, session, data, target_appid, secretKey);

		return verifySign.equals(sign);
	}
	/**
	 * <pre>
	 * 根据masget开放平台API访问协议参数，进行签名
	 * </pre>
	 * 
	 * @param appid
	 * @param method
	 * @param format
	 * @param v
	 * @param timestamp
	 * @param session
	 * @param data
	 * @param target_appid
	 * @param secretKey
	 * @return(设定参数)
	 * @return String(返回值说明)
	 * @author Zhanghaifeng 2016年4月2日
	 */
	public static String signMasgetAPIRequest(String appid, String method,
			String format, String v, String timestamp, String session,
			String data, String target_appid, String secretKey) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(Constants.APPID, appid);
		params.put(Constants.METHOD, method);
		params.put(Constants.FORMAT, format);
		params.put(Constants.V, v);
		params.put(Constants.TIMESTAMP, timestamp);
		params.put(Constants.SESSION, session);
		params.put(Constants.DATA, data);
		params.put(Constants.TARGET_APPID, target_appid);

		return signRequest(params, secretKey);
	}

}
