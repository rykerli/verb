package com.red.verb.auth.model;

import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.red.verb.valid.group.UserEditValidGroup;
import com.red.verb.valid.group.UserLoginValidGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * User
 * <pre>
 *  Version         Date            Author          Description
 * ------------------------------------------------------------
 *  1.0.0           2019/05/25     red        -
 * </pre>
 *
 * @author redli
 * @version 1.0.0 2019-05-25 13:56
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@TableName("user")
public class User implements Serializable {
	private static final Long serializable = 1L;

	/**
	 * ID
	 */
	@Id
	@GeneratedValue(generator = "JDBC")
	private Integer id;

	/**
	 * 帐号
	 */
	@NotNull(message = "帐号不能为空", groups = {UserLoginValidGroup.class, UserEditValidGroup.class })
	private String account;

	/**
	 * 密码
	 */
	@NotNull(message = "密码不能为空", groups = { UserLoginValidGroup.class, UserEditValidGroup.class })
	private String password;

	/**
	 * 昵称
	 */
	@NotNull(message = "用户名不能为空", groups = { UserEditValidGroup.class })
	private String username;

	/**
	 * 注册时间
	 */
	@Column(name = "reg_time")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date regTime;
}
