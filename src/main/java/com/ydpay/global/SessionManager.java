package com.ydpay.global;

import com.alibaba.fastjson.JSONObject;
import com.ydpay.cache.redis.RedisCache;
import com.ydpay.utils.MgException;
import com.ydpay.utils.StringUtil;

public class SessionManager {

	private static SessionManager sessionManager = new SessionManager();
	private static final String SESSION_PREFIX = "user_login_session_prefix_";
	private static final String SESSION_LOGINNAME_KEY = "user_login_session_loginname_key_";

	public static SessionManager getInstance() {
		return sessionManager;
	}

	/**
	 * 获取session
	 * 
	 * @param sessionid
	 * @return
	 */
	public tf_session getSession(String sessionid) {
		String result = RedisCache.getInstance()
				.get(SESSION_PREFIX + sessionid);
		if (StringUtil.isEmpty(result))
			throw new MgException(10, "获取session失败");
		return JSONObject.parseObject(result, tf_session.class);

	}

	/**
	 * 获取session
	 * 
	 * @param sessionid
	 * @return
	 */
	public boolean saveSession(tf_session session) {
		RedisCache.getInstance().set(SESSION_PREFIX + session.getSession(),
				JSONObject.toJSONString(session), 0);
		String key = SESSION_LOGINNAME_KEY + session.getMobilephone() + "_"
				+ session.getDevicetype();
		String value = RedisCache.getInstance().get(key);
		if (!StringUtil.isEmpty(value))
			RedisCache.getInstance().remove(SESSION_PREFIX + value);
		RedisCache.getInstance().set(key, session.getSession(), 0);
		return true;
	}
	/**
	 * 清除session
	 * 
	 * @param sessionid
	 * @return
	 */
	public void logout(tf_session session) {
		RedisCache.getInstance().remove(SESSION_PREFIX + session.getSession());
		String key = SESSION_LOGINNAME_KEY + session.getMobilephone() + "_"
				+ session.getDevicetype();
		RedisCache.getInstance().remove(key);
	}

}
