package com.webbank.dao;

import com.webbank.model.Bank;
import com.webbank.model.User;

public interface BankDao {
    void update(Bank bank);
    void save(Bank bank);
    void delete(Bank bank);
    Bank getBank(User user);
}
