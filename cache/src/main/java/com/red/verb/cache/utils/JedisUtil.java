package com.red.verb.cache.utils;

import com.red.verb.exception.MyException;
import com.red.verb.utils.SerializableUtil;
import com.red.verb.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * jedis util
 * <pre>
 *  Version         Date            Author          Description
 * ------------------------------------------------------------
 *  1.0.0           2019/05/26     red        -
 * </pre>
 *
 * @author redli
 * @version 1.0.0 2019-05-26 09:53
 * @since 1.0.0
 */
@Component
@Slf4j
public class JedisUtil {
	/**
	 * Status-OK
	 */
	private final static String OK = "OK";

	/**
	 * 静态注入JedisPool连接池
	 * 本来是正常注入JedisUtil，可以在Controller和Service层使用，但是重写Shiro的CustomCache无法注入JedisUtil
	 * 现在改为静态注入JedisPool连接池，JedisUtil直接调用静态方法即可
	 */
	private static JedisPool jedisPool;

	@Autowired
	public void setJedisPool(JedisPool jedisPool) {
		JedisUtil.jedisPool = jedisPool;
	}

	/**
	 * 获取Jedis实例
	 *
	 * @return redis.clients.jedis.Jedis
	 * @author red
	 * @date 2018/9/4 15:47
	 */
	public static synchronized Jedis getJedis() {
		try {
			if (jedisPool != null) {
				return jedisPool.getResource();
			} else {
				return null;
			}
		} catch (Exception e) {
			throw new MyException("获取Jedis资源异常:" + e.getMessage());
		}
	}

	/**
	 * 释放Jedis资源
	 *
	 * @author red
	 * @date 2018/9/5 9:16
	 */
	public static void closePool() {
		try {
			jedisPool.close();
		} catch (Exception e) {
			throw new MyException("释放Jedis资源异常:" + e.getMessage());
		}
	}

	/**
	 * 获取redis键值-object
	 *
	 * @param key key
	 * @return java.lang.Object
	 * @author red
	 * @date 2018/9/4 15:47
	 */
	public static Object getObject(String key) {
		try (Jedis jedis = jedisPool.getResource()) {
			byte[] bytes = jedis.get(key.getBytes());
			if (StringUtil.isNotNull(bytes)) {
				return SerializableUtil.unserializable(bytes);
			}
		} catch (Exception e) {
			throw new MyException("获取Redis键值getObject方法异常:key=" + key + " cause=" + e.getMessage());
		}
		return null;
	}

	/**
	 * 设置redis键值-object
	 *
	 * @param key
	 * @param value
	 * @return java.lang.String
	 * @author red
	 * @date 2018/9/4 15:49
	 */
	public static String setObject(String key, Object value) {
		try (Jedis jedis = jedisPool.getResource()) {
			return jedis.set(key.getBytes(), SerializableUtil.serializable(value));
		} catch (Exception e) {
			throw new MyException("设置Redis键值setObject方法异常:key=" + key + " value=" + value + " cause=" + e.getMessage());
		}
	}

	/**
	 * 设置redis键值-object-expiretime
	 *
	 * @param key        key
	 * @param value      value
	 * @param expireTime expireTime
	 * @return java.lang.String
	 * @author red
	 * @date 2018/9/4 15:50
	 */
	public static String setObject(String key, Object value, int expireTime) {
		String result = "";
		try (Jedis jedis = jedisPool.getResource()) {
			result = jedis.set(key.getBytes(), SerializableUtil.serializable(value));
			if (OK.equals(result)) {
				jedis.expire(key.getBytes(), expireTime);
			}
			return result;
		} catch (Exception e) {
			throw new MyException("设置Redis键值setObject方法异常:key=" + key + " value=" + value + " cause=" + e.getMessage());
		}
	}

	/**
	 * 获取redis键值-Json
	 *
	 * @param key key
	 * @return java.lang.Object
	 * @author red
	 * @date 2018/9/4 15:47
	 */
	public static String getJson(String key) {
		try (Jedis jedis = jedisPool.getResource()) {
			return jedis.get(key);
		} catch (Exception e) {
			throw new MyException("获取Redis键值getJson方法异常:key=" + key + " cause=" + e.getMessage());
		}
	}

	/**
	 * 设置redis键值-Json
	 *
	 * @param key   key
	 * @param value value
	 * @return java.lang.String
	 * @author red
	 * @date 2018/9/4 15:49
	 */
	public static String setJson(String key, String value) {
		try (Jedis jedis = jedisPool.getResource()) {
			return jedis.set(key, value);
		} catch (Exception e) {
			throw new MyException("设置Redis键值setJson方法异常:key=" + key + " value=" + value + " cause=" + e.getMessage());
		}
	}

	/**
	 * 设置redis键值-Json-expireTime
	 *
	 * @param key        key
	 * @param value      value
	 * @param expireTime expireTime
	 * @return java.lang.String
	 * @author red
	 * @date 2018/9/4 15:50
	 */
	public static String setJson(String key, String value, int expireTime) {
		String result = "";
		try (Jedis jedis = jedisPool.getResource()) {
			result = jedis.set(key, value);
			if (OK.equals(result)) {
				jedis.expire(key, expireTime);
			}
			return result;
		} catch (Exception e) {
			throw new MyException("设置Redis键值setJson方法异常:key=" + key + " value=" + value + " cause=" + e.getMessage());
		}
	}

	/**
	 * 删除key
	 *
	 * @param key key
	 * @return java.lang.Long
	 * @author red
	 * @date 2018/9/4 15:50
	 */
	public static Long delKey(String key) {
		try (Jedis jedis = jedisPool.getResource()) {
			return jedis.del(key.getBytes());
		} catch (Exception e) {
			throw new MyException("删除Redis的键delKey方法异常:key=" + key + " cause=" + e.getMessage());
		}
	}

	/**
	 * key是否存在
	 *
	 * @param key key
	 * @return java.lang.Boolean
	 * @author red
	 * @date 2018/9/4 15:51
	 */
	public static Boolean exists(String key) {
		try (Jedis jedis = jedisPool.getResource()) {
			return jedis.exists(key.getBytes());
		} catch (Exception e) {
			throw new MyException("查询Redis的键是否存在exists方法异常:key=" + key + " cause=" + e.getMessage());
		}
	}

	/**
	 * 模糊查询获取key集合
	 *
	 * @param key key
	 * @return java.util.Set<java.lang.String>
	 * @author red
	 * @date 2018/9/6 9:43
	 */
	public static Set<String> keysS(String key) {
		try (Jedis jedis = jedisPool.getResource()) {
			return jedis.keys(key);
		} catch (Exception e) {
			throw new MyException("模糊查询Redis的键集合keysS方法异常:key=" + key + " cause=" + e.getMessage());
		}
	}

	/**
	 * 模糊查询获取key集合
	 *
	 * @param key key
	 * @return java.util.Set<java.lang.String>
	 * @author red
	 * @date 2018/9/6 9:43
	 */
	public static Set<byte[]> keysB(String key) {
		try (Jedis jedis = jedisPool.getResource()) {
			return jedis.keys(key.getBytes());
		} catch (Exception e) {
			throw new MyException("模糊查询Redis的键集合keysB方法异常:key=" + key + " cause=" + e.getMessage());
		}
	}

	/**
	 * 获取过期剩余时间
	 *
	 * @param key key
	 * @return java.lang.String
	 * @author red
	 * @date 2018/9/11 16:26
	 */
	public static Long getExpireTime(String key) {
		Long result = -2L;
		try (Jedis jedis = jedisPool.getResource()) {
			result = jedis.ttl(key);
			return result;
		} catch (Exception e) {
			throw new MyException("获取Redis键过期剩余时间getExpireTime方法异常:key=" + key + " cause=" + e.getMessage());
		}
	}

}
