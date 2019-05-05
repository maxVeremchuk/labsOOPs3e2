package com.webbank.dao;

import com.webbank.model.UserProfile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository("userProfileDao")
@Transactional
public class UserProfileDaoImpl extends AbstractDao<Integer, UserProfile>implements UserProfileDao  {
    @Override
    public List<UserProfile> findAll() {
        return null;
    }

     @Override
    public UserProfile findByType(String type) {
        CriteriaBuilder cb = createEntityCriteria();
        CriteriaQuery<UserProfile> cr = cb.createQuery(UserProfile.class);
        Root<UserProfile> root = cr.from(UserProfile.class);
        cr.where(cb.equal(root.get("type"), type));
        TypedQuery<UserProfile> query = getSession().createQuery(cr);
        return query.getResultList().get(0);
    }

   //@Override
   /*public void save(UserProfile userProfile) {
        persist(userProfile);
    }*/

    @Override
    public UserProfile findById(int id) {
        return getByKey(id);
    }
}
