package com.ydpay.global;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.ydpay.entity.RetStruct;
import com.ydpay.enums.base.EncryptFlag;
import com.ydpay.security.AesEncryption;
import com.ydpay.security.Base64Method;
import com.ydpay.utils.CommonUtil;
import com.ydpay.utils.Constants;
import com.ydpay.utils.MgException;
import com.ydpay.utils.ServerParamsUtil;
import com.ydpay.utils.StringUtil;
import com.ydpay.utils.YdpayLogger;

/**
 * 平台应用API校验API
 * 
 */
public class ApiAuthorityService {

	/**
	 * <pre>
	 * 校验当前请求appid是否有访问API的权限
	 * </pre>
	 * 
	 * @param apidefine
	 *            (接口定义=method)
	 * @param appkey
	 *            (当前请求的用户id=appid)
	 */
	public static boolean checkApiAuthority(String apidefine, String appkey) {
		// :TODO
		return true;
	}

	/**
	 * 
	 * @description:
	 * @param encryptflag
	 * @param appid
	 * @param session
	 * @return(设定参数)
	 * @return String(返回值说明)
	 * @author Zhanghaifeng 2016年4月5日
	 */
	public static String getApiSecretKey(int encryptflag, String appid,
			String session) {
		String secretKey = "";
		if (encryptflag == EncryptFlag.DONT_NEED_ENCRYPT.getid()
				&& StringUtil.isEmpty(session)) {
			if (appid.equals(ServerParamsUtil.get("appid"))) {
				secretKey = ServerParamsUtil.get("secretkey");
			} else {
				throw new MgException(47, "没有找到满足条件的系统信息");
			}
		} else if (encryptflag == EncryptFlag.NEED_ENCRYPT.getid()
				&& StringUtil.isEmpty(session)) {
			throw new MgException(50, "缺少用户授权参数(session)");
		} else {
			tf_session sessionValue = SessionManager.getInstance().getSession(
					session);
			if (sessionValue == null)
				throw new MgException(10, "获取用户session信息失败");
			if (!appid.equals(sessionValue.getAppid()))
				throw new MgException(52, "appid与session关联信息不一致");
			secretKey = sessionValue.getSecretkey();
		}
		return secretKey;
	}

	private static Map<String, Boolean> hashMap = new HashMap<String, Boolean>();
	static {
		hashMap.put("ydpay.base.com.user.login", true);
		hashMap.put("ydpay.base.com.user.reg", true);
		hashMap.put("ydpay.base.com.user.session.check", true);
		hashMap.put("ydpay.base.com.masget.gateway", true);
		hashMap.put("ydpay.base.serverparam.list.get", true);
		hashMap.put("ydpay.pay.compay.router.front.report", true);
		hashMap.put("ydpay.pay.compay.router.back.report", true);
		hashMap.put("ydpay.pay.compay.router.ordernumber.get", true);
		hashMap.put("ydpay.base.com.sms.send", true);
		hashMap.put("ydpay.base.com.sms.verifycode.send", true);
		hashMap.put("ydpay.base.com.sms.verifycode.verify", true);
		hashMap.put("ydpay.base.com.user.mobilephone.check", true);
		hashMap.put("ydpay.base.com.memberinfo.getone", true);
		hashMap.put("ydpay.base.com.companyinfo.get", true);
		hashMap.put("ydpay.pay.compay.router.front.pay", true);
		hashMap.put("ydpay.base.com.user.password.modify", true);
		hashMap.put("ydpay.base.com.shorturl.create", true);
		hashMap.put("ydpay.base.com.shorturl.expand", true);
		hashMap.put("ydpay.base.rate.package.masget.sync", true);
		hashMap.put("ydpay.base.rate.package.agent.sync", true);
		hashMap.put("ydpay.base.cache.primarykey.schema.sync", true);
		hashMap.put("ydpay.base.rate.package.cache.init", true);
		hashMap.put("ydpay.base.com.qrcode.create", true);
		hashMap.put("ydpay.pay.compay.router.open.list", true);
		hashMap.put("ydpay.base.excel.template.redis.refresh", true);
		
		hashMap.put("ydpay.weixin.com.weixinpublic.signal.authorize", true);
		hashMap.put("ydpay.weixin.com.weixinpublic.signal.authorize.redirect", true);
		hashMap.put("ydpay.weixin.com.weixinpublic.signal.jssdktoken.get", true);
		hashMap.put("ydpay.weixin.com.weixinpublic.signal.userinfo.get", true);
		hashMap.put("ydpay.weixin.com.weixinpublic.signal.template.send", true);
		hashMap.put("ydpay.weixin.com.weixinsmall.routine.sessionkey.get", true);
		hashMap.put("ydpay.weixin.com.weixinsmall.routine.template.send", true);
		hashMap.put("ydpay.weixin.com.weixinpublic.signal.jssdkticket.get", true);
		hashMap.put("ydpay.weixin.com.weixinpublic.signal.jsapi.sign", true);
		hashMap.put("ydpay.weixin.com.weixinpublic.signal.bacthaddcard.sign", true);
		hashMap.put("ydpay.weixin.com.weixinpublic.signal.choosecard.sign", true);
	}
	/**
	 * // 获取API授权标志
	 * 
	 * @return
	 */
	public static int getApiEncryptFlag(String method) {

		int encryptflag = 0;
		if (hashMap.containsKey(method) && hashMap.get(method))
			encryptflag = EncryptFlag.DONT_NEED_ENCRYPT.getid();
		else
			encryptflag = EncryptFlag.NEED_ENCRYPT.getid();
		return encryptflag;

	}

	/**
	 * 
	 * <pre>
	 * 	API请求校验<br/>
	 * API访问授权校验说明:</br>
	 * 1.校验appid访问API的权限
	 * 2.获取API是否需要用户授权
	 * 3.如果需要用户授权或者不需要用户授权但协议参数session不为空，则获取用户session,同时校验appid是否与session关联id一致,获取密钥
	 * 4.如果不需要用户授权且协议参数session为空，则直接获取该appid的固定密钥
	 * 5.验证签名:使用3或4获取的密钥，对API协议数据进行签名验证
	 * 6.如果签名验证成功,则使用密钥对协议数据进行AES解密
	 * 7.返回授权结果，如果解密成功，则同时返回解密后的数据
	 * </pre>
	 * 
	 */

	public static String checkAuthorityCom(String data) throws Exception {
		YdpayLogger.debug("授权中心请求数据:" + data);
		JSONObject obj = JSONObject.parseObject(data);
		//
		int sessionflag = obj.containsKey(Constants.SESSION_FLAG) ? obj
				.getInteger(Constants.SESSION_FLAG) : 1;
		int signflag = obj.containsKey(Constants.SIGN_FLAG) ? obj
				.getInteger(Constants.SIGN_FLAG) : 1;

		// String request_data = "";
		// doPost进入
		if (sessionflag == 1 && signflag == 1) {
			return checkNewAuthorityCom(obj);
		} else {
			String request_data = Base64Method.DecryptBase64(obj
					.getString("data"));
			JSONObject result = new JSONObject();
			result.put(Constants.API_RETURN_CODE, 0);
			result.put(Constants.API_RETURN_MESSAGE, "API访问授权校验成功");
			result.put("data", JSONObject.parseObject(request_data));

			return result.toString();
		}
		// JSONObject result = new JSONObject();
		// result.put(Constants.API_RETURN_CODE, 0);
		// result.put(Constants.API_RETURN_MESSAGE, "API访问授权校验成功");
		// result.put("data", request_data);
		//
		// return result.toString();
	}

	/**
	 * 
	 * <pre>
	 * 新的API访问协议
	 * </pre>
	 * 
	 */
	public static String checkNewAuthorityCom(JSONObject obj) throws Exception {
		try {
			String method = obj.getString(Constants.METHOD);
			String appid = obj.getString(Constants.APPID);
			String session = obj.getString(Constants.SESSION);

			String request_data = "";
			int encryptflag = getApiEncryptFlag(method);
			// 根据授权标志和session参数，获取密钥
			String secretKey = getApiSecretKey(encryptflag, appid, session);

			// 校验签名参数
			if (!CommonUtil.verifyMasgetAPISign(appid, method,
					obj.getString(Constants.FORMAT),
					obj.getString(Constants.V),
					obj.getString(Constants.TIMESTAMP), session,
					obj.getString(Constants.DATA),
					obj.getString(Constants.TARGET_APPID), secretKey,
					obj.getString(Constants.SIGN))) {
				return new RetStruct(16, "签名校验失败").toString();
			}
			// 对数据进行AES解密操作
			request_data = AesEncryption.Desencrypt(
					obj.getString(Constants.DATA), secretKey, secretKey).trim();

			YdpayLogger.info("\n新版本协议请求\nappid:" + appid + "接口:" + method
					+ "\n请求数据解析结果:" + request_data);
			// 解析请求数据，在请求数据中添加请求id
			if (StringUtil.isEmpty(request_data)) {
				request_data = "{}";
			}
			try {
				JSONObject request_data_obj = JSONObject
						.parseObject(request_data);
				request_data_obj.put(Constants.APPID, appid);

				JSONObject result = new JSONObject();
				result.put(Constants.API_RETURN_CODE, 0);
				result.put(Constants.API_RETURN_MESSAGE, "API访问授权校验成功");
				result.put("data", request_data_obj.toString());

				return result.toString();
			} catch (Exception e) {
				YdpayLogger.error("请求参数数据解析异常:request_data=" + request_data, e);
				return new RetStruct(44, "请求参数数据解析异常").toString();
			}
		} catch (MgException e) {
			return new RetStruct(e.getRet(), "API访问权限校验失败:" + e.getMessage())
					.toString();
		} catch (Exception e) {
			YdpayLogger.error("API访问权限校验失败:" + e.getMessage(), e);
			return new RetStruct(44, "API访问权限校验失败").toString();
		}
	}
}
