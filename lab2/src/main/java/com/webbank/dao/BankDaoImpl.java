package com.webbank.dao;

import com.webbank.model.Account;
import com.webbank.model.Bank;
import com.webbank.model.Card;
import com.webbank.model.User;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class BankDaoImpl extends AbstractDao<Integer, Bank> implements BankDao {

    @Override
    public void save(Bank bank) {
        persist(bank);
    }

    @Override
    public Bank getBank(User user) {
       // String hql = "FROM Bank_admins B WHERE B.user_id = :id";
        String hql = "select bank.id from Bank bank join bank.admins admins where admins.id = :id";
        Query query = getSession().createQuery(hql);
        query.setParameter("id", user.getId());
        List<Integer> list = query.list();
        return getByKey(list.get(0));
    }
}
