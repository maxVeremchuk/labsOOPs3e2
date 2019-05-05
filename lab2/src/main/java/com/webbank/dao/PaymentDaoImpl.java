package com.webbank.dao;

import com.webbank.model.Card;
import com.webbank.model.Payment;
import com.webbank.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@Transactional
public class PaymentDaoImpl extends AbstractDao<Integer, Payment> implements PaymentDao {
    @Override
    public void save(Payment payment) {
        persist(payment);
    }

    @Override
    public List<Payment> getPayments(User user) {
        CriteriaBuilder cb = createEntityCriteria();
        CriteriaQuery<Payment> cr = cb.createQuery(Payment.class);
        Root<Payment> root = cr.from(Payment.class);
        cr.where(cb.equal(root.get("clientId"), user.getId()));
        TypedQuery<Payment> query = getSession().createQuery(cr);
        return query.getResultList();
    }
}
