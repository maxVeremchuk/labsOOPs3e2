package com.webbank.dao;

import com.webbank.model.Payment;
import com.webbank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface PaymentDao extends JpaRepository<Payment, Integer> {
    @Query("select p from Payment p where p.clientId = ?1")
    List<Payment> getPayments(int id);

    @Query("select p from Payment p where p.cardNumber = ?1 and p.date between ?2 and ?3")
    List<Payment> getPaymentsByDate(int cardNumber, Date dateFrom, Date dateTo);
}
