package com.ydpay.dao.cache;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 
 * @Description: 带有效期map 简单实现 实现了基本的方法
 * @author: qd-ankang
 * @date: 2016-11-24 下午4:08:46
 * @param <K>
 * @param <V>
 */
public class ExpiryMap<K, V> extends ConcurrentHashMap<K, V> {

	private static final long serialVersionUID = 1L;

	/**
	 * default expiry time 1m
	 */
	private long EXPIRY = 1000 * 60 * 30;

	private ConcurrentMap<K, Long> expiryMap = new ConcurrentHashMap<K, Long>();

	public ExpiryMap() {
		super();
	}

	public ExpiryMap(long defaultExpiryTime) {
		this(1 << 10, defaultExpiryTime);
	}

	public ExpiryMap(int initialCapacity, long defaultExpiryTime) {
		super(initialCapacity);
		this.EXPIRY = defaultExpiryTime;
	}

	public V put(K key, V value) {
		expiryMap.put(key, System.currentTimeMillis() + EXPIRY);
		return super.put(key, value);
	}

	public boolean containsKey(Object key) {
		return !checkExpiry(key, true) && super.containsKey(key);
	}

	/**
	 * @param key
	 * @param value
	 * @param expiryTime
	 *            键值对有效期 毫秒
	 * @return
	 */
	public V put(K key, V value, long expiryTime) {
		expiryMap.put(key, System.currentTimeMillis() + expiryTime);
		return super.put(key, value);
	}

	public int size() {
		return entrySet().size();
	}

	public boolean isEmpty() {
		return entrySet().size() == 0;
	}

	public boolean containsValue(Object value) {
		if (value == null)
			return Boolean.FALSE;
		Set<java.util.Map.Entry<K, V>> set = super.entrySet();
		Iterator<java.util.Map.Entry<K, V>> iterator = set.iterator();
		while (iterator.hasNext()) {
			java.util.Map.Entry<K, V> entry = iterator.next();
			if (value.equals(entry.getValue())) {
				if (checkExpiry(entry.getKey(), false)) {
					iterator.remove();
					return Boolean.FALSE;
				} else
					return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	public Collection<V> values() {

		Collection<V> values = super.values();

		if (values == null || values.size() < 1)
			return values;

		Iterator<V> iterator = values.iterator();

		while (iterator.hasNext()) {
			V next = iterator.next();
			if (!containsValue(next))
				iterator.remove();
		}
		return values;
	}

	public V get(Object key) {
		if (key == null)
			return null;
		if (checkExpiry(key, true))
			return null;
		return super.get(key);
	}

	public V get(Object key, V defaultValue) {
		if (key == null)
			return defaultValue;
		if (checkExpiry(key, true))
			return defaultValue;
		return super.get(key);
	}

	/**
	 * 
	 * @Description: 是否过期
	 * @param key
	 * @return null:不存在、已过期或key为null 存在且没过期返回value 因为过期的不是实时删除，所以稍微有点作用
	 */
	public Object isInvalid(Object key) {
		if (key == null)
			return null;
		if (!expiryMap.containsKey(key)) {
			return null;
		}
		long expiryTime = expiryMap.get(key);

		boolean flag = System.currentTimeMillis() > expiryTime;

		if (flag) {
			super.remove(key);
			expiryMap.remove(key);
			return null;
		}
		return super.get(key);
	}

	public void putAll(Map<? extends K, ? extends V> m) {
		for (Map.Entry<? extends K, ? extends V> e : m.entrySet())
			expiryMap.put(e.getKey(), System.currentTimeMillis() + EXPIRY);
		super.putAll(m);
	}

	public Set<Map.Entry<K, V>> entrySet() {
		Set<java.util.Map.Entry<K, V>> set = super.entrySet();
		Iterator<java.util.Map.Entry<K, V>> iterator = set.iterator();
		while (iterator.hasNext()) {
			java.util.Map.Entry<K, V> entry = iterator.next();
			if (checkExpiry(entry.getKey(), false))
				iterator.remove();
		}

		return set;
	}

	/**
	 * 
	 * @Description: 是否过期
	 * 
	 * @return 1.不存在，表示已过期 2.存在,但当前时间大于过期时间, 过期 3.存在，并且当前时间小于过期时间, 不过期
	 */
	private boolean checkExpiry(Object key, boolean isRemoveSuper) {

		if (!expiryMap.containsKey(key)) {
			return Boolean.TRUE;
		}
		long expiryTime = expiryMap.get(key);

		boolean flag = System.currentTimeMillis() > expiryTime;

		if (flag) {// 过期,删除原有数据
			if (isRemoveSuper)
				super.remove(key);
			expiryMap.remove(key);
		}
		return flag;
	}

}
