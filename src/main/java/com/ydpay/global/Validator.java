package com.ydpay.global;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.ydpay.cache.redis.RedisCache;
import com.ydpay.entity.MethodArgs;
import com.ydpay.enums.base.TunnelType;
import com.ydpay.security.AesEncryption;
import com.ydpay.utils.CommandHelper;
import com.ydpay.utils.Constants;
import com.ydpay.utils.MgException;
import com.ydpay.utils.YdpayLogger;

public class Validator {
	static Log log = LogFactory.getLog(Validator.class);
	public static MethodArgs CheckPostMethodData(Map<String, String> postdata,
			int sessionflag, int signflag) {

		postdata.put(Constants.SESSION_FLAG, sessionflag + "");
		postdata.put(Constants.SIGN_FLAG, signflag + "");
		MethodArgs methodArgs = new MethodArgs();

		String params = "";
		String message = "";
		int ret = 0;
		methodArgs.setRet(0);
		methodArgs.setMessage("");
		methodArgs.setMethodName(CommandHelper.GetMethod(postdata
				.get(Constants.METHOD)));
		methodArgs.setServiceName(CommandHelper.GetServiceName(postdata
				.get(Constants.METHOD)));
		try {
			String result = ApiAuthorityService.checkAuthorityCom(JSONObject.toJSONString(postdata));
			JSONObject resultObj = JSONObject.parseObject(result);
			// 请求API访问校验成功,
			ret = resultObj.getInteger(Constants.API_RETURN_CODE);
			message = resultObj.getString(Constants.API_RETURN_MESSAGE);
			params = resultObj.getString(Constants.API_RETURN_DATA);

		} catch (MgException e) {
			ret = e.getRet();
			message = e.getMessage();
		} catch (Exception e) {
			ret = 17;
			message = "CheckPostData处理异常";
			YdpayLogger.error(postdata.get(Constants.METHOD)
					+ "#CheckPostData处理异常#" + e.getMessage(), e);
		}
		methodArgs.setRet(ret);
		methodArgs.setMessage(message);
		methodArgs.setMethodData(params);
		return methodArgs;
	}

	
	/***
	 * 支付平台回调参数处理
	 * @param data
	 * @return
	 */
	public static JSONObject checkMasgetData(String data) {
		JSONObject requestObj = JSONObject.parseObject(data);
		String masgetsessioninfo = RedisCache.getInstance().get(
				"appidsession_masget_" + requestObj.getString("Appid")+"_"+TunnelType.XINFU.getCode());
		YdpayLogger.info("masgetsessioninfo=" + masgetsessioninfo);
		JSONObject masgetsessioninfoObj = JSONObject.parseObject(masgetsessioninfo);
		String value = "";
		try {
			value = AesEncryption.Desencrypt(
					requestObj.getString("Data"),
					masgetsessioninfoObj.getString("key"),
					masgetsessioninfoObj.getString("key"));
		} catch (Exception e) {
			YdpayLogger.error("#checkMasgetData处理异常#" + e.getMessage(), e);
		}
		return JSONObject.parseObject(value.trim());
	}
}
