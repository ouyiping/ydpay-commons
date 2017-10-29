package com.ydpay.utils;

public class Constants {
	// 默认服务器分页
	public static final int DEFAULT_PAGESIZE = 100;
	public static final int DEFAULT_PAGENUM = 1;

	// API访问协议参数
	public static final String APPID = "appid";
	public static final String METHOD = "method";
	public static final String FORMAT = "format";
	public static final String DATA = "data";
	public static final String V = "v";
	public static final String TIMESTAMP = "timestamp";
	public static final String SIGN = "sign";
	public static final String SESSION = "session";
	public static final String TARGET_APPID = "target_appid";

	public static final String SESSION_FLAG = "sessionflag";
	public static final String SIGN_FLAG = "signflag";

	// API请求授权校验接口
	public static final String AUTHORITY_METHOD = "ydpay.authority.com.authority.check";

	// API访问协议标准返回结构体 返回码 返回信息 返回数据
	public static final String API_RETURN_CODE = "ret";
	public static final String API_RETURN_MESSAGE = "message";
	public static final String API_RETURN_DATA = "data";

	// 系统配置参数key
	public static final String OPEN_API_URL = "openApiUrl";
	public static final String SECRET_KEY = "secretkey";
	public static final String LOGIN_PWD = "loginpwd";
	public static final String LOGIN_NAME = "loginname";

	/** MD5签名方式 */
	public static final String SIGN_METHOD_MD5 = "md5";
	/** HMAC签名方式 */
	public static final String SIGN_METHOD_HMAC = "hmac";
	/** UTF-8字符集 **/
	public static final String CHARSET_UTF8 = "UTF-8";
	/** GBK字符集 **/
	public static final String CHARSET_GBK = "GBK";
	
}
