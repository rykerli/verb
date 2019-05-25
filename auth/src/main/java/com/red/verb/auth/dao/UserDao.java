package com.red.verb.auth.dao;

import com.red.verb.commom.base.BaseDao;
import com.red.verb.model.User;
import org.springframework.stereotype.Repository;

/**
 * UserDao
 * <pre>
 *  Version         Date            Author          Description
 * ------------------------------------------------------------
 *  1.0.0           2019/05/25     red        -
 * </pre>
 *
 * @author redli
 * @version 1.0.0 2019-05-25 15:07
 * @since 1.0.0
 */
@Repository
public interface UserDao extends BaseDao<User> {
}
