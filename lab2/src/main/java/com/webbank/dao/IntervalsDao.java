package com.webbank.dao;

import com.webbank.model.Intervals;
import com.webbank.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface IntervalsDao extends CrudRepository<Intervals, Integer> {
    @Query("select i from Intervals i where ?1 between i.dateFrom and i.leftDate and ?2 between i.rightDate and i.dateTo")
    List<Intervals> getIntervals(Date dateFrom, Date dateTo);
}
