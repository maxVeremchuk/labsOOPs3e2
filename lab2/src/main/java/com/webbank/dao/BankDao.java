package com.webbank.dao;

import com.webbank.model.Bank;
import com.webbank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface BankDao extends JpaRepository<Bank, Integer> {

    @Query("select bank from Bank bank join bank.admins admins where admins.id = ?1")
    Bank getBank(int id);
}
