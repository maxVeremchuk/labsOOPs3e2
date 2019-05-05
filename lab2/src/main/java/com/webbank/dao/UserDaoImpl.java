package com.webbank.dao;

import com.webbank.model.User;
import org.hibernate.Hibernate;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@Transactional
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao{

    public User findById(int id) {
        User user = getByKey(id);
        if(user!=null){
            Hibernate.initialize(user.getUserProfiles());
        }
        return user;
    }

    @Override
    public User findByUsername(String username) {
        CriteriaBuilder cb = createEntityCriteria();
        CriteriaQuery<User> cr = cb.createQuery(User.class);
        Root<User> root = cr.from(User.class);
        cr.where(cb.equal(root.get("username"), username));

        TypedQuery<User> query = getSession().createQuery(cr);
        if(query.getResultList().size() == 0){
            return null;
        }
        User user = query.getResultList().get(0);
        /*if(user!=null){
            Hibernate.initialize(user.getUserProfiles());
        }*/
        return query.getResultList().get(0);
    }

    /*public User findBySSO(String sso) {
        CriteriaBuilder cb = createEntityCriteria();
        CriteriaQuery<User> cr = cb.createQuery(User.class);
        Root<User> root = cr.from(User.class);
        cr.select(root).where
        crit.add(Restrictions.eq("ssoId", sso));
        User user = (User)crit.uniqueResult();
        if(user!=null){
            Hibernate.initialize(user.getUserProfiles());
        }
        return user;
    }*/

    public List<User> findAllUsers() {
        CriteriaBuilder cb = createEntityCriteria();
        CriteriaQuery<User> cr = cb.createQuery(User.class);
        Root<User> root = cr.from(User.class);
        cr.orderBy(cb.asc(root.get("username")));

        TypedQuery<User> query = getSession().createQuery(cr);
        return query.getResultList();

    }

    public void save(User user) {
        persist(user);
    }

    public void deleteById(int id) {
        CriteriaBuilder cb = createEntityCriteria();
        CriteriaDelete<User> cr = cb.createCriteriaDelete(User.class);
        Root<User> root = cr.from(User.class);
        cr.where(cb.equal(root.get("id"), id));
        Transaction transaction =  getSession().beginTransaction();
        getSession().createQuery(cr);
        transaction.commit();
        //delete(user);
    }
}
