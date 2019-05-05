package com.webbank.dao;

import com.webbank.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class UserDetailsDaoImpl implements UserDetailsDao {

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public User findUserByUsername(String username) {
        //return sessionFactory.getCurrentSession().get(User.class, username);
        User user = new User();
        user.setUsername(username);
        return user;
    }
    public User getActiveUser(String username) {
        User activeUserInfo = new User();
        short enabled = 1;
        List<?> users = sessionFactory.getCurrentSession()
                .createQuery("from User where username=? and enabled=?")
                .setParameter(0, username)
                .setParameter(1, enabled)
                .list();

        if(!users.isEmpty()) {
            activeUserInfo = (User)users.get(0);
        }
        return activeUserInfo;
    }
}