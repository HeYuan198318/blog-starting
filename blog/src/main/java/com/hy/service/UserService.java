package com.hy.service;

import com.hy.entity.User;

/**
 * @author hy
 * @date 2020/9/6 22:02
 */
public interface UserService {

    User checkUser(String username,String password);
}
