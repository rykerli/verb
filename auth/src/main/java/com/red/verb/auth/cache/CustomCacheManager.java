package com.red.verb.auth.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

/**
 * CustomCacheManager 重写Shiro缓存管理器
 * <pre>
 *  Version         Date            Author          Description
 * ------------------------------------------------------------
 *  1.0.0           2019/05/26     red        -
 * </pre>
 *
 * @author redli
 * @version 1.0.0 2019-05-26 11:59
 * @since 1.0.0
 */
public class CustomCacheManager implements CacheManager {
	@Override
	public <K, V> Cache<K, V> getCache(String s) throws CacheException {
		return new CustomCache<K, V>();
	}
}
