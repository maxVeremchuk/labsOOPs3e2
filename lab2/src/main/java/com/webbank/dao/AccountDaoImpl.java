package com.webbank.dao;

import com.webbank.model.Account;
import com.webbank.model.Card;
import com.webbank.model.Payment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class AccountDaoImpl extends AbstractDao<Integer, Account> implements AccountDao{
    @Override
    public Account getAccount(Card card) {
        int cardId = card.getAccountId();
        return getByKey(cardId);
    }
    @Override
    public void save(Account account) {
        persist(account);
    }

    @Override
    public List<Account> getAccounts(List<Card> cards) {
        CriteriaBuilder cb = createEntityCriteria();
        CriteriaQuery<Account> cr = cb.createQuery(Account.class);
        Root<Account> root = cr.from(Account.class);
        List<Account> accounts = new ArrayList<>();
        for(Card card : cards) {
            cr.where(cb.equal(root.get("id"), card.getAccountId()));
            TypedQuery<Account> query = getSession().createQuery(cr);
            accounts.add(query.getResultList().get(0));
        }
        return accounts;
    }
}
