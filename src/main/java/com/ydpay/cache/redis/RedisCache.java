package com.ydpay.cache.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ydpay.dao.cache.InitCache;
import com.ydpay.utils.StringUtil;

public class RedisCache {
	private static RedisCache instance = null;

	public static RedisCache getInstance() {
		if (instance == null)
			instance = new RedisCache();
		return instance;
	}

	public long incr(String key) {
		Jedis jedis = InitCache.getPool().getResource();
		try {
			return jedis.incr(key);
		} finally {
			InitCache.getPool().returnResourceObject(jedis);
		}
	}

	public long incrBy(String key, long value) {
		Jedis jedis = InitCache.getPool().getResource();
		try {
			return jedis.incrBy(key, value);
		} finally {
			InitCache.getPool().returnResourceObject(jedis);
		}
	}

	public long decr(String key) {
		Jedis jedis = InitCache.getPool().getResource();
		try {
			return jedis.decr(key);
		} finally {
			InitCache.getPool().returnResourceObject(jedis);
		}
	}

	public long decrBy(String key, long value) {
		Jedis jedis = InitCache.getPool().getResource();
		try {
			return jedis.decrBy(key, value);
		} finally {
			InitCache.getPool().returnResourceObject(jedis);
		}
	}

	public boolean exists(String key) {
		Jedis jedis = InitCache.getPool().getResource();
		try {
			boolean bresult = false;
			if (jedis == null)
				return bresult;
			return jedis.exists(key);
		} finally {
			InitCache.getPool().returnResourceObject(jedis);
		}
	}

	public boolean set(String key, String value) {
		return set(key, value, 0);
	}

	public boolean set(String key, String value, int expiretime /* seconds */) {
		Jedis jedis = InitCache.getPool().getResource();
		try {
			boolean bresult = false;
			if (jedis == null)
				return bresult;

			if (value != null && !"".equals(value)) {
				jedis.set(key, value);
				if (expiretime > 0)
					jedis.expire(key, expiretime);
				bresult = true;
			}
			return bresult;
		} finally {
			InitCache.getPool().returnResourceObject(jedis);
		}

	}

	public String get(String key) {
		Jedis jedis = InitCache.getPool().getResource();
		try {
			String sresult = "";
			if (jedis == null)
				return sresult;

			if (jedis.exists(key))
				sresult = jedis.get(key);

			return sresult;
		} finally {
			InitCache.getPool().returnResourceObject(jedis);
		}
	}

	public boolean remove(String key) {
		Jedis jedis = InitCache.getPool().getResource();
		try {
			boolean bresult = false;
			if (jedis == null)
				return bresult;

			if (jedis.exists(key)) {
				jedis.del(key);
				bresult = true;
			}

			return bresult;
		} finally {
			InitCache.getPool().returnResourceObject(jedis);
		}
	}

	public boolean setBatch(List<Map<String, String>> rows) {
		Jedis jedis = InitCache.getPool().getResource();
		boolean bresult = false;
		if (jedis == null)
			return bresult;
		try {
			for (Map<String, String> row : rows) {
				jedis.set(row.get("key"), row.get("value"));
			}
			bresult = true;
		} finally {
			InitCache.getPool().returnResourceObject(jedis);
		}

		return bresult;
	}

	public JSONArray getArray(String keyMatch) {
		Jedis jedis = InitCache.getPool().getResource();
		try {
			Set<String> keySet = jedis.keys(keyMatch);
			JSONArray array = new JSONArray();
			for (String key : keySet) {
				String value = jedis.get(key);
				JSONObject obj = new JSONObject();
				obj.put("key", key);
				obj.put("value", value);
				array.add(obj);
			}
			return array;
		} finally {
			InitCache.getPool().returnResourceObject(jedis);
		}
	}

	/**
	 * 进队列
	 * 
	 * @param key
	 * @param values
	 */
	public long push(String key, String... values) {
		Jedis jedis = InitCache.getPool().getResource();
		try {
			return jedis.lpush(key, values);
		} finally {
			InitCache.getPool().returnResourceObject(jedis);
		}
	}

	/**
	 * 出队列
	 * 
	 * @param key
	 */
	public String pop(String key) {
		Jedis jedis = InitCache.getPool().getResource();
		try {
			return jedis.rpop(key);
		} finally {
			InitCache.getPool().returnResourceObject(jedis);
		}
	}

	/**
	 * 递归队列
	 * 
	 * @param key
	 * @return
	 */
	public String recursive(String key) {
		Jedis jedis = InitCache.getPool().getResource();
		try {
			String value = jedis.rpop(key);
			if (StringUtil.isNotEmpty(value))
				jedis.lpush(key, value);
			return value;
		} finally {
			InitCache.getPool().returnResourceObject(jedis);
		}
	}

	public long getQueueLength(String key) {
		Jedis jedis = InitCache.getPool().getResource();
		try {
			return jedis.llen(key);
		} finally {
			InitCache.getPool().returnResourceObject(jedis);
		}
	}

	public List<String> getQueue(String key, long start, long end) {
		Jedis jedis = InitCache.getPool().getResource();
		try {
			return jedis.lrange(key, start, end);
		} finally {
			InitCache.getPool().returnResourceObject(jedis);
		}
	}

}
