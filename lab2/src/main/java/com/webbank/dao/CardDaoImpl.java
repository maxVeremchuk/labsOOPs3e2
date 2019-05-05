package com.webbank.dao;

import com.webbank.model.Account;
import com.webbank.model.Card;
import com.webbank.model.User;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class CardDaoImpl extends AbstractDao<Integer, Card> implements CardDao{
    @Override
    public Card getCard(int cardNum) {
        return getByKey(cardNum);
    }

    @Override
    public List<Card> getUsersCards(User user) {
        CriteriaBuilder cb = createEntityCriteria();
        CriteriaQuery<Card> cr = cb.createQuery(Card.class);
        Root<Card> root = cr.from(Card.class);
        cr.where(cb.equal(root.get("clientId"), user.getId()));

        TypedQuery<Card> query = getSession().createQuery(cr);
        return query.getResultList();
    }

    @Override
    public List<Card> getBlockedCards(List<Integer> accounts) {
        String hql = "select card from Card card join Account account on card.accountId = account.id where account.isBlocked = 1";
        Query query = getSession().createQuery(hql);
        List<Card> list = query.list();
        return list;
    }

    @Override
    public void save(Card card) {
        persist(card);
    }
}
