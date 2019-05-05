package com.webbank.service;




import com.webbank.model.UserProfile;

import java.util.List;

public interface UserProfileService {

    UserProfile findById(int id);

    UserProfile findByType(String type);

    List<UserProfile> findAll();

   // void save(UserProfile userProfile);

}
