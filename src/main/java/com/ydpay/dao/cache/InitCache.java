package com.ydpay.dao.cache;

import java.util.ResourceBundle;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

public class InitCache {

	private static JedisPool pool;

	public static JedisPool getPool() {
		return pool;
	}

	public static void InitPoolConfig() {

		ResourceBundle bundle = ResourceBundle.getBundle("redis");
		if (bundle == null) {
			throw new IllegalArgumentException(
					"[redis.properties] is not found!");
		}

		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(Integer.valueOf(bundle
				.getString("redis.pool.maxActive")));
		config.setMaxIdle(Integer.valueOf(bundle
				.getString("redis.pool.maxIdle")));
		config.setMaxWaitMillis(Long.valueOf(bundle
				.getString("redis.pool.maxWait")));
		config.setTestOnBorrow(Boolean.valueOf(bundle
				.getString("redis.pool.testOnBorrow")));
		config.setTestOnReturn(Boolean.valueOf(bundle
				.getString("redis.pool.testOnReturn")));

		String password = bundle.getString("redis.password");
		int database = bundle.containsKey("redis.database") ? Integer
				.valueOf(bundle.getString("redis.database")) : 0;

		pool = new JedisPool(config, bundle.getString("redis.ip"),
				Integer.valueOf(bundle.getString("redis.port")),
				Protocol.DEFAULT_TIMEOUT, password, database);
	}
}
