package com.webbank.dao;

import com.webbank.model.User;

public interface UserDetailsDao {
  User findUserByUsername(String username);
}
