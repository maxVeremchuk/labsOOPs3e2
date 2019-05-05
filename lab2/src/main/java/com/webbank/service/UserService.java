package com.webbank.service;

import com.webbank.dao.UserDao;
import com.webbank.dao.UserDetailsDaoImpl;
import com.webbank.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService implements IUserService {
	@Autowired
	private UserDao dao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public User findById(int id) {
		return dao.findById(id);
	}


	public void saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		dao.save(user);
	}


	public void updateUser(User user) {
		User entity = dao.findById(user.getId());
		if(entity!=null){
			if(!user.getPassword().equals(entity.getPassword())){
				entity.setPassword(passwordEncoder.encode(user.getPassword()));
			}
			entity.setUsername(user.getUsername());
			entity.setName(user.getName());
			entity.setEnabled(user.isEnabled());
		}
	}


	public void deleteUserById(int id) {
		dao.deleteById(id);
	}

	public List<User> findAllUsers() {
		return dao.findAllUsers();
	}

	@Override
	public boolean isUserNameUnique(String username) {
		User user = dao.findByUsername(username);
		return (user == null);
	}

	@Override
	public User findByUsername(String username) {
		return dao.findByUsername(username);
	}

}
