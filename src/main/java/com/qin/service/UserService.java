package com.qin.service;

import com.qin.dao.UserDao;
import com.qin.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User getUserById(Integer id){
        return userDao.getUserById(id);
    }

    public User getUserByName(String name){
        return userDao.getUserByName(name);
    }

    public int insertUser(User user){
        return userDao.insertUser(user);
    }
}
