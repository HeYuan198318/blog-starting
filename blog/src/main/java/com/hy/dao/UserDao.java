package com.hy.dao;

import com.hy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author hy
 * @date 2020/9/6 22:10
 */
@Repository
public interface UserDao extends JpaRepository<User,Long> {

    User findByUsernameAndPassword(String username, String password);
}
