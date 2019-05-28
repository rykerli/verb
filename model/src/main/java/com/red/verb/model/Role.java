package com.red.verb.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Role
 * <pre>
 *  Version         Date            Author          Description
 * ------------------------------------------------------------
 *  1.0.0           2019/05/25     red        -
 * </pre>
 *
 * @author redli
 * @version 1.0.0 2019-05-25 14:40
 * @since 1.0.0
 */
@Data

@Table(name = "role")
public class Role {
	private static final long serialVersionUID = 6382925944937625109L;

	/**
	 * ID
	 */
	@Id
	@GeneratedValue(generator = "JDBC")
	private Integer id;

	/**
	 * 角色名称
	 */
	private String name;

}
