package com.red.verb.commom.exception;

import com.red.verb.commom.ResponseCode;
import com.red.verb.commom.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

/**
 * GlobalExceptionHandler
 * <pre>
 *  Version         Date            Author          Description
 * ------------------------------------------------------------
 *  1.0.0           2019/05/24     red        -
 * </pre>
 *
 * @author redli
 * @version 1.0.0 2019-05-24 10:09
 * @since 1.0.0
 */
@Slf4j
public class GlobalExceptionHandler {
	/**
	 *
	 * 全局异常类中定义的异常都可以被拦截，只是触发条件不一样，如IO异常这种必须抛出异常到
	 * controller中才可以被拦截，或者在类中用try..catch自己处理
	 * 绝大部分不需要向上抛出异常即可被拦截，返回前端json数据，如数组下标越界，404 500 400等错误
	 * 如果自己想要写，按着以下格式增加异常即可
	 *HttpMessageNotReadableException
	 */


	/**
	 * 启动应用后，被 @ExceptionHandler、@InitBinder、@ModelAttribute 注解的方法，
	 * 都会作用在 被 @RequestMapping 注解的方法上。
	 *
	 * @param binder 参数
	 */
	@InitBinder
	public void initWebBinder(WebDataBinder binder) {

	}

	/**
	 * 系统错误，未知的错误   已测试
	 *
	 * @param ex 异常信息
	 * @return 返回前端异常信息
	 */
	@ExceptionHandler({Exception.class})
	public ServerResponse exception(Exception ex) {
		log.error("错误详情：" + ex.getMessage(), ex);
		return ServerResponse.createByErrorCodeMsg(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
	}

	/**
	 * 文件没有找到错误拦截   **要把错误信息抛出到controller层  已测试
	 *
	 * @param ex 异常信息
	 * @return 返回前端异常信息
	 */
	@ExceptionHandler(FileNotFoundException.class)
	public ServerResponse fileNotFound(FileNotFoundException ex) {
		log.error("错误详情：" + ex.getMessage(), ex);
		return ServerResponse
				.createByErrorCodeMsg(ResponseCode.FILE_NOT_FOUND.getCode(), ResponseCode.FILE_NOT_FOUND.getDesc());
	}

	/**
	 * 字符串转换为数字异常   已测试  不需要抛出到controller即可被拦截
	 *
	 * @param ex 异常信息
	 * @return 返回前端异常信息
	 */
	@ExceptionHandler(NumberFormatException.class)
	public ServerResponse numberFormatEx(NumberFormatException ex) {
		log.error("错误详情：" + ex.getMessage(), ex);
		return ServerResponse
				.createByErrorCodeMsg(ResponseCode.NUMBER_FORMAT.getCode(), ResponseCode.NUMBER_FORMAT.getDesc());

	}

	/**
	 * sql操作数据库出错了
	 *
	 * @param ex 异常信息
	 * @return 返回前端异常信息
	 */
	@ExceptionHandler(SQLException.class)
	public ServerResponse sqlException(SQLException ex) {
		log.error("错误详情：" + ex.getMessage(), ex);
		return ServerResponse
				.createByErrorCodeMsg(ResponseCode.SQL_EXCEPTION.getCode(), ResponseCode.SQL_EXCEPTION.getDesc());
	}

	/**
	 * 参数传递出错了
	 *
	 * @param ex 异常信息
	 * @return 返回前端异常信息
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ServerResponse sqlException(IllegalArgumentException ex) {
		log.error("错误详情：" + ex.getMessage(), ex);
		return ServerResponse
				.createByErrorCodeMsg(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
	}

	/**
	 * 栈溢出错误
	 *
	 * @param ex 异常信息
	 * @return 返回前端异常信息
	 */
	@ExceptionHandler(StackOverflowError.class)
	public ServerResponse stackOverflow(StackOverflowError ex) {
		log.error("错误详情：" + ex.getMessage(), ex);
		return ServerResponse
				.createByErrorCodeMsg(ResponseCode.STACK_OVERFLOW.getCode(), ResponseCode.STACK_OVERFLOW.getDesc());
	}

	/**
	 * 404错误拦截   已测试
	 *
	 * @param ex 异常信息
	 * @return 返回前端异常信息
	 */
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ServerResponse noHandlerNotFound(NoHandlerFoundException ex) {
		log.error("错误详情：" + ex.getMessage(), ex);
		return ServerResponse
				.createByErrorCodeMsg(ResponseCode.NO_HANDLER.getCode(), ResponseCode.NO_HANDLER.getDesc());
	}

	/**
	 * 400错误拦截
	 *
	 * @param ex 异常信息
	 * @return 返回前端异常信息
	 */
	@ExceptionHandler(TypeMismatchException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ServerResponse request400(TypeMismatchException ex) {
		log.error("错误详情：" + ex.getMessage(), ex);
		return ServerResponse
				.createByErrorCodeMsg(ResponseCode.BAD_REQUEST.getCode(), ResponseCode.BAD_REQUEST.getDesc());
	}

	/**
	 * 400错误拦截
	 *
	 * @param ex 异常信息
	 * @return 返回前端异常信息
	 */
	@ExceptionHandler(MissingServletRequestParameterException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ServerResponse request400(MissingServletRequestParameterException ex) {
		log.error("错误详情：" + ex.getMessage(), ex);
		return ServerResponse
				.createByErrorCodeMsg(ResponseCode.BAD_REQUEST.getCode(), ResponseCode.BAD_REQUEST.getDesc());
	}

	/**
	 * 400错误拦截
	 *
	 * @param ex 异常信息
	 * @return 返回前端异常信息
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ServerResponse request400(HttpMessageNotReadableException ex) {
		log.error("错误详情：" + ex.getMessage(), ex);
		return ServerResponse
				.createByErrorCodeMsg(ResponseCode.BAD_REQUEST.getCode(), ResponseCode.BAD_REQUEST.getDesc());
	}

	/**
	 * 405错误拦截
	 *
	 * @param ex 异常信息
	 * @return 返回前端异常信息
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	public ServerResponse request405(HttpRequestMethodNotSupportedException ex) {
		log.error("错误详情：" + ex.getMessage(), ex);
		return ServerResponse
				.createByErrorCodeMsg(ResponseCode.METHOD_NOT_ALLOWED.getCode(),
						ResponseCode.METHOD_NOT_ALLOWED.getDesc());
	}

	/**
	 * 500错误拦截
	 *
	 * @param ex 异常信息
	 * @return 返回前端异常信息
	 */
	@ExceptionHandler({ConversionNotSupportedException.class, HttpMessageNotWritableException.class})
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ServerResponse request500(RuntimeException ex) {
		log.error("错误详情：" + ex.getMessage(), ex);
		return ServerResponse
				.createByErrorCodeMsg(ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
						ResponseCode.INTERNAL_SERVER_ERROR.getDesc());
	}

	/**
	 * 类型转换异常   已经测试 可以拦截
	 *
	 * @param ex 异常信息
	 * @return 返回前端异常信息
	 */
	@ExceptionHandler(ClassCastException.class)
	public ServerResponse classCastExceptionHandler(ClassCastException ex) {
		log.error("错误详情：" + ex.getMessage(), ex);
		return ServerResponse
				.createByErrorCodeMsg(ResponseCode.CLASS_CAST.getCode(), ResponseCode.CLASS_CAST.getDesc());
	}

	/**
	 * 未知方法异常
	 *
	 * @param ex 异常信息
	 * @return 返回前端异常信息
	 */
	@ExceptionHandler(NoSuchMethodException.class)
	public ServerResponse noSuchMethodExceptionHandler(NoSuchMethodException ex) {
		log.error("错误详情：" + ex.getMessage(), ex);
		return ServerResponse
				.createByErrorCodeMsg(ResponseCode.NO_SUCH_METHOD.getCode(), ResponseCode.NO_SUCH_METHOD.getDesc());
	}

	/**
	 * IO异常 需要抛出到Controller层可捕获到
	 *
	 * @param ex 异常信息
	 * @return 返回前端异常信息
	 */
	@ExceptionHandler(IOException.class)
	public ServerResponse iOExceptionHandler(IOException ex) {
		log.error("错误详情：" + ex.getMessage(), ex);
		return ServerResponse
				.createByErrorCodeMsg(ResponseCode.IO_EXCEPTION.getCode(), ResponseCode.IO_EXCEPTION.getDesc());
	}

	/**
	 * 空指针异常  已测试  可以拦截
	 *
	 * @param ex 异常信息
	 * @return 返回前端异常信息
	 */
	@ExceptionHandler(NullPointerException.class)
	public ServerResponse nullPointerExceptionHandler(NullPointerException ex) {
		log.error("错误详情：" + ex.getMessage(), ex);
		return ServerResponse
				.createByErrorCodeMsg(ResponseCode.NULL_POINTER.getCode(), ResponseCode.NULL_POINTER.getDesc());
	}

	/**
	 * 数组越界异常拦截   已测试
	 *
	 * @param ex 异常信息
	 * @return 返回前端异常信息
	 */
	@ExceptionHandler(IndexOutOfBoundsException.class)
	public ServerResponse indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException ex) {
		log.warn("错误详情：" + ex.getMessage(), ex);
		return ServerResponse
				.createByErrorCodeMsg(ResponseCode.INDEX_OUT_BOUNDS.getCode(), ResponseCode.INDEX_OUT_BOUNDS.getDesc());
	}

	/**
	 * 自定义异常信息拦截
	 *
	 * @param ex 异常信息
	 * @return 返回前端异常信息
	 */
	@ExceptionHandler(MyException.class)
	public ServerResponse myCustomizeException(MyException ex) {
		log.warn("错误详情：" + ex);
		return ServerResponse
				.createByErrorCodeMsg(ResponseCode.CUSTOMIZE_EXCEPTION.getCode(),
						ResponseCode.CUSTOMIZE_EXCEPTION.getDesc());
	}
}
