package com.red.verb.commom;

/**
 * @author redli
 * @time
 * @description: 枚举类型声明返回值, 响应的扩展枚举类
 */
public enum ResponseCode {

	/**
	 * 0代表SUCCESS
	 * 1代表ERROR
	 * 10代表需要登录
	 */
	SUCCESS(200, "后台处理成功！"),
	ERROR(100, "后台处理失败！"),
	NEED_LOGIN(10, "NEED_LOGIN"),
	/**
	 * 资源没有找到
	 */
	RESOURCE_NOT_FOUND(101, "资源没有找到"),
	/**
	 * 数组越界了！
	 */
	INDEX_OUT_BOUNDS(102, "数组越界了！"),
	/**
	 * 系统错误，未知的错误
	 */
	SYSTEM_ERROR(103, "系统错误，未知的错误!"),
	/**
	 * 空指针错误
	 */
	NULL_POINTER(104, "空指针异常！"),
	/**
	 * IO异常
	 */
	IO_EXCEPTION(105, "IO异常！"),
	/**
	 * 未知的方法
	 */
	NO_SUCH_METHOD(106, "找不到方法，未知的方法"),
	/**
	 * 类型转换错误
	 */
	CLASS_CAST(107, "类型转换出错，请检查"),
	/**
	 * 文件未找到错误
	 */
	FILE_NOT_FOUND(108, "文件没有找到，请确认文件位置！"),
	/**
	 * 字符串转换数字异常
	 */
	NUMBER_FORMAT(109, "字符串转换数字出错了!"),
	/**
	 * 操作数据库错误
	 */
	SQL_EXCEPTION(110, "操作数据库出错了!"),
	/**
	 * 传递的参数出错了
	 */
	ILLEGAL_ARGUMENT(111, "传递的参数出错了！"),
	/**
	 * 堆栈溢出错误
	 */
	STACK_OVERFLOW(112, "栈溢出了！"),
	/**
	 * 用户未注册
	 */
	USER_UNREGISTERED(113, "用户未注册"),
	/**
	 * 用户已注册
	 */
	USER_REGISTERED(114, "用户已注册"),
	/**
	 * 用户名或密码错误
	 */
	PASSWORD_ERROR(115, "用户名或密码错误！"),
	/**
	 * 验证码发送失败
	 */
	SEND_FAIL(116, "验证码发送失败"),
	/**
	 * 缺少参数或值为空
	 */
	PARAMETER_IS_NULL(117, "缺少参数或值为空"),
	/**
	 * 参数不合法
	 */
	PARAMETER_IS_WRONGFUL(118, "参数不合法"),
	/**
	 * 无效的Token
	 */
	INVALID_TOKEN(119, "无效的Token"),
	/**
	 * 无操作权限
	 */
	NO_OPERATION_AUTHORITY(120, "无操作权限"),
	/**
	 * 自定义异常
	 */
	CUSTOMIZE_EXCEPTION(121, "自定义异常，请检查"),
	/**
	 * token过期了，请重新登录!
	 */
	LOGIN_AGIN(122, "token过期了，请重新登录!"),
	/**
	 * 400错误
	 */
	BAD_REQUEST(400, "Bad Request!"),
	/**
	 * 405错误
	 */
	METHOD_NOT_ALLOWED(405, "Method Not Allowed! 有可能是接口类型错误！"),
	/**
	 * 500错误
	 */
	INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
	/**
	 * 404错误拦截
	 */
	NO_HANDLER(404, "这个页面石沉大海了！接口没找到");
	private final int code;
	private final String desc;

	/**
	 * 构造器
	 */
	ResponseCode(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	/**
	 * 将构造器开放
	 *
	 * @return
	 */
	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
}
