package com.hy.service.Impl;

import com.hy.dao.UserDao;
import com.hy.entity.User;
import com.hy.service.UserService;
import com.hy.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.Enumeration;

/**
 * @author hy
 * @date 2020/9/6 22:07
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public User checkUser(String username, String password) {
        User user=userDao.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }

    @Override
    public User getUserInfo(User user) {
        return userDao.getOne(user.getId());
    }
}
