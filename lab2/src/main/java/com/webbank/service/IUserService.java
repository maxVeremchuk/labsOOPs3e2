package com.webbank.service;

import com.webbank.model.User;
import org.springframework.security.access.annotation.Secured;

import java.util.List;


public interface IUserService {
	User findById(int id);

	void saveUser(User user);

	void updateUser(User user);

	void deleteUserById(int id);

	List<User> findAllUsers();

	boolean isUserNameUnique(String username);

	User findByUsername(String username);
}
