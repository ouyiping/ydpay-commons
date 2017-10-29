package com.ydpay.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.ydpay.global.GlobalContainer;
import com.ydpay.global.tf_session;
import com.ydpay.provider.webinterface.mginterface;
import com.ydpay.security.AesEncryption;
import com.ydpay.security.Base64Method;
import com.ydpay.security.MD5Util;

public class ProviderUtil {
	private static Logger logger = Logger.getLogger(ProviderUtil.class);

	/**
	 * 
	 * @description: 不需要加密调用子系统(主要是子系统中不需要从session中获取登录信息时，可使用)
	 * @param serviceName
	 * @param method
	 * @param data
	 * @return(设定参数)
	 * @return String(返回值说明)
	 * @author Zhanghaifeng 2015年9月18日
	 */
	public static String doPostForOtherSystem(String serviceName,
			String method, String data) {
		try {
			mginterface service = (mginterface) GlobalContainer
					.getApplicationContext().getBean(serviceName);
			String result = service.doPostForOtherSystem(method, data);
			return result;
		} catch (Exception e) {
			logger.error(
					"调用子系统失败:\nserviceName:" + serviceName + "\nmethod:"
							+ method + "\ndata:" + data + "\nmessage:"
							+ e.getMessage(), e);
			throw new MgException(45, "调用子系统失败", e);
		}
	}

	/**
	 * 调用其他模块中不需要用户授权的接口
	 * 
	 * @param method
	 * @param data
	 */
	public static String doPostForOtherSystem(String method, String data) {
		return doPostForOtherSystem(getServiceName(method), method,
				Base64Method.EncryptBase64(data));
	}

	/**
	 * 用于系统内部接口调用需要用户授权的接口
	 * 
	 * @param method
	 * @param data
	 * @param session
	 */
	public static String doPost(String method, String data, tf_session session)
			throws Exception {
		return doPost(
				method,
				makeProtocalParams(method, data, session,
						com.masget.api.Constants.NOT_TO_REDIRECT));
	}

	public static Map<String, String> makeProtocalParams(String method,
			String data, tf_session session, int redirectflag) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put(Constants.APPID, session.getAppid());
		params.put(Constants.METHOD, method);
		params.put(Constants.FORMAT, "json");
		params.put(Constants.V, "2.0");
		params.put(Constants.TIMESTAMP, TimeUtil.getTimestamp());
		params.put(Constants.SESSION, session.getSession());
		params.put(
				Constants.DATA,
				AesEncryption.Encrypt(data, session.getSecretkey(),
						session.getSecretkey()));
		params.put(Constants.TARGET_APPID, "");
		String sign = CommonUtil.signRequest(params, session.getSecretkey());
		params.put(Constants.SIGN, sign);
		if (redirectflag == com.masget.api.Constants.NEED_TO_REDIRECT)
			params.put("redirectflag", "1");
		return params;
	}

	private static String doPost(String method, Map<String, String> params) {
		try {
			mginterface service = (mginterface) GlobalContainer
					.getApplicationContext().getBean(getServiceName(method));
			String result = service.doPost(params);
			return result;
		} catch (Exception e) {
			throw new MgException(45, "doPost调用子系统失败", e);
		}
	}

	public static String getServiceName(String method) {
		String result = "";

		String[] strs = method.split("\\.");
		if (strs.length > 2) {
			result = strs[1];
			result = result + "Service";
		}

		return result;
	}

	public static String getMethod(String[] urlsplit) throws Exception {
		String result = "";

		result = "masget.";
		result = result + StringUtils.join(urlsplit, ".");

		return result;
	}

	public static String makeMerchantReportParams(long appid, String method,
			String data, String secretkey) throws Exception {
		String base64Data = AesEncryption.Encrypt(data, secretkey, secretkey);
		String sign = MD5Util.string2MD5(base64Data + secretkey);
		String sendData = "Data=" + base64Data + "&Sign=" + sign + "&Method="
				+ method + "&Appid=" + appid;
		return sendData;
	}
}
