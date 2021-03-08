package com.hy.service;

import com.hy.entity.User;

/**
 * @author :C3006248
 * @Description:用户业务层接口
 * @create : 2020/11/25 17:16
 */
public interface UserService {

//    核对用户名和密码
    User checkUser(String username, String password);

}