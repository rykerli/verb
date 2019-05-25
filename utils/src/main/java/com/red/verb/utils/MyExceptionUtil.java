package com.red.verb.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.red.verb.exception.MyException;

/**
 * exception 工具类
 * <pre>
 *  Version         Date            Author          Description
 * ------------------------------------------------------------
 *  1.0.0           2019/05/24     red        -
 * </pre>
 *
 * @author redli
 * @version 1.0.0 2019-05-24 09:40
 * @since 1.0.0
 */
public class MyExceptionUtil {
	public MyExceptionUtil() {
	}

	public static MyException mxe(String msg, Throwable t, Object... params) {
		return new MyException(StringUtils.format(msg, params), t);
	}

	public static MyException mxe(String msg, Object... params) {
		return new MyException(StringUtils.format(msg, params));
	}

	public static MyException mxe(Throwable t) {
		return new MyException(t);
	}
}
