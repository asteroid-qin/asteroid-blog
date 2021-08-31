package com.qin.dao;

import com.qin.entity.User;

import java.util.List;

public interface UserDao {
    User getUserById(Integer id);

    List<User> getUsers();
}
