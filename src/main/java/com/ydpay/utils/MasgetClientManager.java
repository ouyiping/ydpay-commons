package com.ydpay.utils;

import com.alibaba.fastjson.JSONObject;
import com.ydpay.cache.redis.RedisCache;
import com.ydpay.entity.RetStruct;
import com.ydpay.enums.base.TunnelType;
import com.ydpay.security.AesEncryption;
import com.ydpay.security.MD5Util;

public class MasgetClientManager {
	private static final String FORMAT = "json";
	private static final String VERSION = "2.0";

	public static String methodInvoke(String openApiUrl, String appid,
			String session, String secretkey, String method,
			JSONObject requestObj, int redirectflag) {
		return methodInvoke(openApiUrl, appid, session, secretkey, method,
				requestObj.toJSONString(), redirectflag);
	}

	public static String methodInvoke(String openApiUrl, String appid,
			String session, String secretkey, String method, String data,
			int redirectflag) {
		String result = "";
		try {
			String encryptdata = AesEncryption.Encrypt(data, secretkey,
					secretkey);
			String timestamp = TimeUtil.getTime();
			String signstr = MD5Util.string2MD5(secretkey + appid + encryptdata
					+ FORMAT + method + session + timestamp + VERSION
					+ secretkey);
			JSONObject requestObj = new JSONObject();
			requestObj.put("appid", appid);
			requestObj.put("method", method);
			requestObj.put("session", session);
			requestObj.put("format", FORMAT);
			requestObj.put("data", encryptdata);
			requestObj.put("v", VERSION);
			requestObj.put("timestamp", timestamp);
			requestObj.put("sign", signstr);

			YdpayLogger.info("请求masget明文:" + data + "\n请求masget报文:"
					+ requestObj.toJSONString());
			if (redirectflag == 1) {
				requestObj.put("redirectflag", ""+redirectflag);
				return openApiUrl + "?"+ Util.buildQuery(requestObj);
			} else {
				result = HttpsUtil.doSslPost(openApiUrl,
						requestObj.toJSONString(), "utf-8");
				YdpayLogger.info("请求masget结果:" + result);
			}
		} catch (Exception ex) {
			YdpayLogger.error("请求异常", ex);
			result = ex.getMessage();
		}
		return result;
	}
	public static String gateway(String method, String data) {
		return gateway(null, method, data, TunnelType.XINFU.getCode());
	}
	public static String gateway(String method, String data,long tunnelcode) {
		return gateway(null, method, data, tunnelcode);
	}

	public static String gateway(String appid, String method, String data,
			int redirectflag,long tunnelcode) {
		if (StringUtil.isEmpty(appid)) {
			appid = ServerParamsUtil.get("appid");
		}

		String value = RedisCache.getInstance().get("appidsession_" + appid+"_"+tunnelcode);
		if (StringUtil.isEmpty(value))
			return new RetStruct(404, "获取系统数据失败").toString();

		JSONObject masgetSessionObj = JSONObject.parseObject(value);
		return methodInvoke(masgetSessionObj.getString("apiurl"),
				masgetSessionObj.getString("appid"),
				masgetSessionObj.getString("session"),
				masgetSessionObj.getString("key"), method, data, redirectflag);
	}

	public static String gateway(String appid, String method, String data,long tunnelcode) {
		if (StringUtil.isEmpty(appid)) {
			appid = ServerParamsUtil.get("appid");
		}

		String value = RedisCache.getInstance().get("appidsession_" + appid+"_"+tunnelcode);
		if (StringUtil.isEmpty(value))
			return new RetStruct(404, "获取系统数据失败").toString();

		JSONObject masgetSessionObj = JSONObject.parseObject(value);
		return methodInvoke(ServerParamsUtil.get("rb_service_url"),
				masgetSessionObj.getString("appid"),
				masgetSessionObj.getString("session"),
				masgetSessionObj.getString("key"), method, data, 2);
	}
	public static String gateway(String appid, String method, String data) {
		if (StringUtil.isEmpty(appid)) {
			appid = ServerParamsUtil.get("appid");
		}

		String value = RedisCache.getInstance().get("appidsession_" + appid+"_"+TunnelType.XINFU.getCode());
		if (StringUtil.isEmpty(value))
			return new RetStruct(404, "获取系统数据失败").toString();

		JSONObject masgetSessionObj = JSONObject.parseObject(value);
		return methodInvoke(ServerParamsUtil.get("rb_service_url"),
				masgetSessionObj.getString("appid"),
				masgetSessionObj.getString("session"),
				masgetSessionObj.getString("key"), method, data, 2);
	}
}
