package com.webbank.dao;


import com.webbank.model.UserProfile;

import java.util.List;

public interface UserProfileDao {
    List<UserProfile> findAll();

    UserProfile findByType(String type);

   // void save(UserProfile userProfile);

    UserProfile findById(int id);
}
