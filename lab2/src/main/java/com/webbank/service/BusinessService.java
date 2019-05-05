package com.webbank.service;

import com.webbank.model.*;

import java.util.List;

public interface BusinessService {
    User getUser(String username, String password);
    List<Card> getUsersCards(User user);
    List<Payment> getPayments(User user);
    int addPayment(Payment payment);
    Account getAccount(Card card);
    Card getCard(int cardNum);
    void updateAccount(Account account);
    List<Account> getAccounts(List<Card> cards);
    void blockAccount(Account account, boolean isBlocked);
    List<Card> getBlockedCards(List<Integer> accounts);
   // int registrateUser(User user);
    Bank getBank(User user);
   // boolean checkIsUserExists(String login);
}
