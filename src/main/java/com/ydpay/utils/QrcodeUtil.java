package com.ydpay.utils;

import com.alibaba.fastjson.JSONObject;

/**
 * 二维码工具类
 */
public class QrcodeUtil {

	public static String createQrcode(String source) {
		JSONObject obj = new JSONObject();
		obj.put("source", source);
		String result = ProviderUtil.doPostForOtherSystem(
				"ydpay.base.com.qrcode.create", obj.toJSONString());
		JSONObject resultObj = JSONObject.parseObject(result);
		if (resultObj.getInteger("ret") != 0) {
			return null;
		}
		return resultObj.getJSONObject("data").getString("qrcodeurl");
	}

	public static JSONObject createShortUrl(String longurl) {
		JSONObject obj = new JSONObject();
		obj.put("longurl", longurl);
		String result = ProviderUtil.doPostForOtherSystem(
				"ydpay.base.com.shorturl.create", obj.toJSONString());
		JSONObject resultObj = JSONObject.parseObject(result);
		if (resultObj.getInteger("ret") != 0) {
			return null;
		}
		return resultObj.getJSONObject("data");
	}

}
