package com.red.verb.utils;

/**
 * string util
 * <pre>
 *  Version         Date            Author          Description
 * ------------------------------------------------------------
 *  1.0.0           2019/05/26     red        -
 * </pre>
 *
 * @author redli
 * @version 1.0.0 2019-05-26 10:11
 * @since 1.0.0
 */
public class StringUtil {
	/**
	 * 定义下划线
	 */
	private static final char UNDERLINE = '_';

	/**
	 * String为空判断(不允许空格)
	 *
	 * @param str str
	 * @return boolean
	 * @author red
	 * @date 2018/9/4 14:49
	 */
	public static boolean isBlank(String str) {
		return str == null || "".equals(str.trim());
	}

	/**
	 * String不为空判断(不允许空格)
	 *
	 * @param str str
	 * @return boolean
	 * @author red
	 * @date 2018/9/4 14:51
	 */
	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	/**
	 * Byte数组为空判断
	 *
	 * @param bytes bytes
	 * @return boolean
	 * @author red
	 * @date 2018/9/4 15:39
	 */
	public static boolean isNull(byte[] bytes) {
		// 根据byte数组长度为0判断
		return bytes == null || bytes.length == 0;
	}

	/**
	 * Byte数组不为空判断
	 *
	 * @param bytes bytes
	 * @return boolean
	 * @author red
	 * @date 2018/9/4 15:41
	 */
	public static boolean isNotNull(byte[] bytes) {
		return !isNull(bytes);
	}

	/**
	 * 驼峰转下划线工具
	 *
	 * @param param param
	 * @return java.lang.String
	 * @author red
	 * @date 2018/9/4 14:52
	 */
	public static String camelToUnderline(String param) {
		if (isNotBlank(param)) {
			int len = param.length();
			StringBuilder sb = new StringBuilder(len);
			for (int i = 0; i < len; i++) {
				char c = param.charAt(i);
				if (Character.isUpperCase(c)) {
					sb.append(UNDERLINE);
					sb.append(Character.toLowerCase(c));
				} else {
					sb.append(c);
				}
			}
			return sb.toString();
		} else {
			return "";
		}
	}
}
