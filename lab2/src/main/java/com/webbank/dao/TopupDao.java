package com.webbank.dao;

import com.webbank.model.Payment;
import com.webbank.model.Topup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface TopupDao extends JpaRepository<Topup, Integer> {
    @Query("select t from Topup t where t.cardNumber = ?1 and t.date between ?2 and ?3")
    List<Topup> getTopupByDate(int cardNumber, Date dateFrom, Date dateTo);
}
