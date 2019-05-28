package com.red.verb.cache.config;

import com.red.verb.utils.StringUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * JedisConfig
 * <pre>
 *  Version         Date            Author          Description
 * ------------------------------------------------------------
 *  1.0.0           2019/05/26     red        -
 * </pre>
 *
 * @author redli
 * @version 1.0.0 2019-05-26 09:50
 * @since 1.0.0
 */
@Configuration
@EnableAutoConfiguration
@PropertySource("classpath:redis.properties")
@ConfigurationProperties(prefix = "redis")
@Slf4j
@Data
public class JedisConfig {

	private String host;
	private int port;
	private String password;
	private int timeout;

	@Value("${redis.pool.max-active}")
	private int maxActive;

	@Value("${redis.pool.max-wait}")
	private int maxWait;

	@Value("${redis.pool.max-idle}")
	private int maxIdle;

	@Value("${redis.pool.min-idle}")
	private int minIdle;

	@Bean
	public JedisPool redisPoolFactory() {
		try {
			JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
			jedisPoolConfig.setMaxIdle(maxIdle);
			jedisPoolConfig.setMaxWaitMillis(maxWait);
			jedisPoolConfig.setMaxTotal(maxActive);
			jedisPoolConfig.setMinIdle(minIdle);

			String pwd = StringUtil.isBlank(password) ? null : password;
			JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, pwd);

			log.info("初始化Redis连接池JedisPool成功!" + " Redis地址: " + host + ":" + port);
			return jedisPool;
		} catch (Exception e) {
			log.error("初始化Redis连接池JedisPool异常:" + e.getMessage());
		}
		return null;
	}
}
