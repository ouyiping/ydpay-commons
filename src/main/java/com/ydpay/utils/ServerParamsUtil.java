package com.ydpay.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 获取每个表的主键最大值+1 工具类
 */
public class ServerParamsUtil {
	static Log logger = LogFactory.getLog(ServerParamsUtil.class);
	private static Map<String, String> hashParamTemp;
	private static Map<String, String> hashParam;
	private static int serverid = 1;
	public static long companyid = 0;

	@SuppressWarnings("unchecked")
	public static void init(int trigflag) {
		if (trigflag == 0)
			logger.info("初始化temp私有参数…… ");
		else
			logger.info("temp私有参数触发重加载……");
		try {
			Properties propsbase = new Properties();
			propsbase.load(ServerParamsUtil.class
					.getResourceAsStream("/config.properties"));
			serverid = Integer.parseInt(propsbase.getProperty("serverid")
					.toString());
			companyid = ObjectParser.toLong(propsbase.getProperty("companyid")
					.toString());
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		// Load params
		if (hashParamTemp != null)
			hashParamTemp.clear();
		else
			hashParamTemp = new HashMap<String, String>();
		JSONObject json = new JSONObject();
		json.put("serverid", serverid);
		json.put("companyid", companyid);

		String result = ProviderUtil.doPostForOtherSystem(
				"ydpay.base.serverparam.list.get", json.toString());

		Gson gson = new Gson();
		Map<String, Object> dataMap = gson.fromJson(result,
				new TypeToken<Map<String, Object>>() {
				}.getType());
		
		// 第二步：把所有参数名和参数值串在一起
		List<Map<String, Object>> paramresult = (List<Map<String, Object>>) dataMap
				.get("data");
		for (Map<String, Object> param : paramresult) {
			hashParamTemp.put(param.get("paramkey").toString(),
					param.get("paramvalue").toString());
		}
		logger.info("temp私有参数加载完毕！");
		if (hashParamTemp != null) {
			// Load params
			if (hashParam != null)
				hashParam.clear();
			else
				hashParam = new HashMap<String, String>();
			
			hashParam.putAll(hashParamTemp);

			logger.info("私有参数加载完毕！");
		} else
			logger.info("私有参数加载失败！");
	}

	public static void putAll(Map<String, String> params){
		if(hashParam == null){
			hashParam = new HashMap<String, String>();
		}else{
			hashParam.clear();
		}
		hashParam.putAll(params);
	}
	
	public static String get(String key) {
		String param = null;
		if (hashParam != null) {
			param = hashParam.get(key);
		}
		logger.info("getParam:" + key + "=" + param);
		return param;
	}

}
