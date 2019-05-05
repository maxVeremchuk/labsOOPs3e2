package com.webbank.dao;

import com.webbank.model.Account;
import com.webbank.model.Card;

import java.util.List;

public interface AccountDao {
    Account getAccount(Card card);
    void update(Account account);
    void save(Account account);
    void delete(Account account);
    List<Account> getAccounts(List<Card> cards);
}
