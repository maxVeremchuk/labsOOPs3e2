package com.webbank.dao;

import com.webbank.model.Payment;
import com.webbank.model.User;

import java.util.List;

public interface PaymentDao {
    void save(Payment payment);
    List<Payment> getPayments(User user);
}
