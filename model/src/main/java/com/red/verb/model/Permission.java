package com.red.verb.model;

import lombok.Data;

import javax.persistence.*;

import java.io.Serializable;

/**
 * Permission
 * <pre>
 *  Version         Date            Author          Description
 * ------------------------------------------------------------
 *  1.0.0           2019/05/26     red        -
 * </pre>
 *
 * @author redli
 * @version 1.0.0 2019-05-26 11:11
 * @since 1.0.0
 */
@Table(name = "permission")
@Data
public class Permission implements Serializable {
	private static final long serialVersionUID = -8834983208597107688L;

	/**
	 * ID
	 */
	@Id
	@GeneratedValue(generator = "JDBC")
	private Integer id;

	/**
	 * 资源名称
	 */
	private String name;

	/**
	 * 权限代码字符串
	 */
	@Column(name = "per_code")
	private String perCode;

}
