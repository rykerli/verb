package com.red.verb.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Base64工具
 * <pre>
 *  Version         Date            Author          Description
 * ------------------------------------------------------------
 *  1.0.0           2019/05/26     red        -
 * </pre>
 *
 * @author redli
 * @version 1.0.0 2019-05-26 10:42
 * @since 1.0.0
 */
public class Base64ConvertUtil {
	/**
	 * 加密JDK1.8
	 *
	 * @param str
	 * @return java.lang.String
	 * @author red
	 * @date 2018/8/21 15:28
	 */
	public static String encode(String str) throws UnsupportedEncodingException {
		byte[] encodeBytes = Base64.getEncoder().encode(str.getBytes(StandardCharsets.UTF_8));
		return new String(encodeBytes);
	}

	/**
	 * 解密JDK1.8
	 *
	 * @param str
	 * @return java.lang.String
	 * @author red
	 * @date 2018/8/21 15:28
	 */
	public static String decode(String str) throws UnsupportedEncodingException {
		byte[] decodeBytes = Base64.getDecoder().decode(str.getBytes(StandardCharsets.UTF_8));
		return new String(decodeBytes);
	}
}
