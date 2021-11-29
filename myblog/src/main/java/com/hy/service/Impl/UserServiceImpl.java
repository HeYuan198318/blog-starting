package com.hy.service.Impl;

import com.hy.dao.UserDao;
import com.hy.entity.User;
import com.hy.service.UserService;
import com.hy.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author :C3006248
 * @Description:用户业务层接口实现类
 * @create : 2020/11/25 17:15
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User checkUser(String username, String password) {
//        User user = userDao.findByUsernameAndPassword(username, MD5Utils.code(password));
        User user = userDao.findByUsernameAndPassword(username, password);
        return user;
    }
}