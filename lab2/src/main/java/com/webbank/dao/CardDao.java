package com.webbank.dao;

import com.webbank.model.Card;
import com.webbank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CardDao extends JpaRepository<Card, Integer> {
    @Query("SELECT c FROM  Card c where c.cardNumber=?1")
    Card getCard(int cardNum);
    @Query("SELECT c FROM User u JOIN Card c ON c.clientId=u.id")
    List<Card> getUsersCards(User user);
    @Query("select card from Card card join Account account on card.accountId = account.id where account.isBlocked = 1")
    List<Card> getBlockedCards(List<Integer> accounts);

}
