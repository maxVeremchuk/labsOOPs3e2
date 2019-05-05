package com.webbank.dao;

import com.webbank.model.User;

import java.util.List;

public interface UserDao {
    User findById(int id);

    User findByUsername(String username);

    void save(User user);

    void deleteById(int id);

    List<User> findAllUsers();
}
