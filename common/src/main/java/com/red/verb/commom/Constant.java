package com.red.verb.commom;


/**
 * @author red
 * 常量类
 */
public class Constant {
	/**
	 * 默认日期时间格式
	 */
	public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 默认日期格式
	 */
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	/**
	 * 默认时间格式
	 */
	public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
	/**
	 * redis-OK
	 */
	public final static String OK = "OK";

	/**
	 * redis过期时间，以秒为单位，一分钟
	 */
	public final static int EXRP_MINUTE = 60;

	/**
	 * redis过期时间，以秒为单位，一小时
	 */
	public final static int EXRP_HOUR = 60 * 60;

	/**
	 * redis过期时间，以秒为单位，一天
	 */
	public final static int EXRP_DAY = 60 * 60 * 24;

	/**
	 * redis-key-前缀-shiro:cache:
	 */
	public final static String PREFIX_SHIRO_CACHE = "shiro:cache:";

	/**
	 * redis-key-前缀-shiro:access_token:
	 */
	public final static String PREFIX_SHIRO_ACCESS_TOKEN = "shiro:access_token:";

	/**
	 * redis-key-前缀-shiro:refresh_token:
	 */
	public final static String PREFIX_SHIRO_REFRESH_TOKEN = "shiro:refresh_token:";

	/**
	 * JWT-account:
	 */
	public final static String ACCOUNT = "account";

	/**
	 * JWT-currentTimeMillis:
	 */
	public final static String CURRENT_TIME_MILLIS = "currentTimeMillis";

	/**
	 * PASSWORD_MAX_LEN
	 */
	public static final Integer PASSWORD_MAX_LEN = 8;
}
