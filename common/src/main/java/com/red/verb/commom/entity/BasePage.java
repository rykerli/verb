package com.red.verb.commom.entity;

import lombok.Data;

/**
 * page 实体类
 * <pre>
 *  Version         Date            Author          Description
 * ------------------------------------------------------------
 *  1.0.0           2019/05/24     red        -
 * </pre>
 *
 * @author redli
 * @version 1.0.0 2019-05-24 16:09
 * @since 1.0.0
 */
@Data
public class BasePage {
	/**
	 * 当前每页显示数
	 */
	private Integer size;

	/**
	 * 当前页数
	 */
	private Integer current;

	/**
	 * 总条数
	 */
	private long total;
}
