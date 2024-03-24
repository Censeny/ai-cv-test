package com.ksy.service.impl;

import com.ksy.dao.UserDao;
import com.ksy.domain.User;
import com.ksy.service.LoginService;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RedissonClient redissonClient;

    @Override
    public List<User> findUserlist() {
        List<User> userList = userDao.findList();
        redissonClient.getBucket("findUserlist").set(userList.toString());
        return userList;
    }
}
