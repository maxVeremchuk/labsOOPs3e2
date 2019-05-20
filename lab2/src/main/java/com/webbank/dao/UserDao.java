package com.webbank.dao;

import com.webbank.model.Payment;
import com.webbank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u where u.username = ?1")
    User findByUsername(String username);
}
