package com.qin.dao;

import com.qin.entity.User;

import java.util.List;

public interface UserDao {
    User getUserById(Integer id);

    User getUserByName(String name);

    List<User> getUsers();

    int insertUser(User user);
}
