package com.ydpay.utils;

import com.alibaba.fastjson.JSONObject;

public class SmsUtil {

//	public static boolean send(String mobilephone, String text) {
//		JSONObject obj = new JSONObject();
//		obj.put("mobilephone", mobilephone);
//		obj.put("text", text);
//		String result = ProviderUtil.doPostForOtherSystem(
//				"ydpay.base.com.sms.send", obj.toJSONString());
//		JSONObject resultObj = JSONObject.parseObject(result);
//		if (resultObj.getInteger("ret") == 0)
//			return true;
//		return false;
//	}

	public static boolean sendVerifycode(String mobilephone, int actiontype) {
		JSONObject obj = new JSONObject();
		obj.put("mobilephone", mobilephone);
		obj.put("actiontype", actiontype);
		String result = ProviderUtil.doPostForOtherSystem(
				"ydpay.base.com.sms.verifycode.send", obj.toJSONString());
		JSONObject resultObj = JSONObject.parseObject(result);
		if (resultObj.getInteger("ret") == 0)
			return true;
		return false;
	}

	public static boolean verifyVerifycode(String mobilephone, String verifycode) {
		JSONObject obj = new JSONObject();
		obj.put("mobilephone", mobilephone);
		obj.put("verifycode", verifycode);
		String result = ProviderUtil.doPostForOtherSystem(
				"ydpay.base.com.sms.verifycode.verify", obj.toJSONString());
		JSONObject resultObj = JSONObject.parseObject(result);
		if (resultObj.getInteger("ret") == 0)
			return true;
		return false;
	}

}
