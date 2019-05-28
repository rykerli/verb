package com.red.verb.auth.repository;

import com.red.verb.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * <pre>
 *  Version         Date            Author          Description
 * ------------------------------------------------------------
 *  1.0.0           2019/05/28     red        -
 * </pre>
 *
 * @author redli
 * @version 1.0.0 2019-05-28 11:25
 * @since 1.0.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {
	@Query("from User u where u.username=:username")
	User findByUserName(@Param("username") String username);
}
