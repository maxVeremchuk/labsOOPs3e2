package com.webbank.dao;

import com.webbank.model.Card;
import com.webbank.model.User;

import java.util.List;

public interface CardDao {
    Card getCard(int cardNum);
    List<Card> getUsersCards(User user);
    List<Card> getBlockedCards(List<Integer> accounts);
    void save(Card card);
    void update(Card card);
    void delete(Card card);
}
