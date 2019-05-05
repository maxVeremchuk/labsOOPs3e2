package com.webbank.service;

import com.webbank.dao.*;
import com.webbank.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessServiceImpl implements BusinessService {
    @Autowired
    AccountDao accountDao;
    @Autowired
    BankDao bankDao;
    @Autowired
    PaymentDao paymentDao;
    @Autowired
    CardDao cardDao;
    @Override
    public User getUser(String username, String password) {
        return null;
    }

    @Override
    public List<Card> getUsersCards(User user) {
        return cardDao.getUsersCards(user);
    }

    @Override
    public List<Payment> getPayments(User user) {
        return paymentDao.getPayments(user);
    }

    @Override
    public int addPayment(Payment payment) {
         paymentDao.save(payment);
         return payment.getId();
    }

    @Override
    public Account getAccount(Card card) {
        return accountDao.getAccount(card);
    }

    @Override
    public Card getCard(int cardNum) {
        return cardDao.getCard(cardNum);
    }

    @Override
    public void updateAccount(Account account) {
        accountDao.update(account);
    }

    @Override
    public List<Account> getAccounts(List<Card> cards) {
        return accountDao.getAccounts(cards);
    }

    @Override
    public void blockAccount(Account account, boolean isBlocked) {
        account.setIsBlocked(isBlocked);
        updateAccount(account);
    }

    @Override
    public List<Card> getBlockedCards(List<Integer> accounts) {
        return cardDao.getBlockedCards(accounts);
    }

    @Override
    public Bank getBank(User user) {
        return bankDao.getBank(user);
    }
}
