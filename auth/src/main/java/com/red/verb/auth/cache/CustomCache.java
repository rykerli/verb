package com.red.verb.auth.cache;

import com.red.verb.auth.utils.JWTUtil;
import com.red.verb.cache.utils.JedisUtil;
import com.red.verb.commom.Constant;
import com.red.verb.utils.PropertiesUtil;
import com.red.verb.utils.SerializableUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import java.util.*;

/**
 * CustomCache
 * <pre>
 *  Version         Date            Author          Description
 * ------------------------------------------------------------
 *  1.0.0           2019/05/26     red        -
 * </pre>
 *
 * @author redli
 * @version 1.0.0 2019-05-26 11:49
 * @since 1.0.0
 */
@SuppressWarnings({"warn", "unchecked"})
public class CustomCache<K, V> implements Cache<K, V> {
	/**
	 * 缓存的key名称获取为shiro:cache:account
	 *
	 * @param key
	 * @return java.lang.String
	 * @author Wang926454
	 * @date 2018/9/4 18:33
	 */
	private String getKey(Object key) {
		return Constant.PREFIX_SHIRO_CACHE + JWTUtil.getClaim(key.toString(), Constant.ACCOUNT);
	}

	/**
	 * 获取缓存
	 */
	@Override
	public Object get(Object key) throws CacheException {
		if (!JedisUtil.exists(this.getKey(key))) {
			return null;
		}
		return JedisUtil.getObject(this.getKey(key));
	}

	/**
	 * 保存缓存
	 */
	@Override
	public Object put(Object key, Object value) throws CacheException {
		// 读取配置文件，获取Redis的Shiro缓存过期时间
		String shiroCacheExpireTime = PropertiesUtil.getProperty("shiroCacheExpireTime");
		// 设置Redis的Shiro缓存
		return JedisUtil.setObject(this.getKey(key), value, Integer.parseInt(
				Objects.requireNonNull(shiroCacheExpireTime)));
	}

	/**
	 * 移除缓存
	 */
	@Override
	public Object remove(Object key) throws CacheException {
		if (!JedisUtil.exists(this.getKey(key))) {
			return null;
		}
		JedisUtil.delKey(this.getKey(key));
		return null;
	}

	/**
	 * 清空所有缓存
	 */
	@Override
	public void clear() throws CacheException {
		Objects.requireNonNull(JedisUtil.getJedis()).flushDB();
	}

	/**
	 * 缓存的个数
	 */
	@Override
	public int size() {
		Long size = Objects.requireNonNull(JedisUtil.getJedis()).dbSize();
		return size.intValue();
	}

	/**
	 * 获取所有的key
	 */
	@Override
	public Set keys() {
		Set<byte[]> keys = Objects.requireNonNull(JedisUtil.getJedis()).keys("*".getBytes());
		Set<Object> set = new HashSet<Object>();
		for (byte[] bs : keys) {
			set.add(SerializableUtil.unserializable(bs));
		}
		return set;
	}

	/**
	 * 获取所有的value
	 */
	@Override
	public Collection values() {
		Set keys = this.keys();
		List<Object> values = new ArrayList<Object>();
		for (Object key : keys) {
			values.add(JedisUtil.getObject(this.getKey(key)));
		}
		return values;
	}
}
