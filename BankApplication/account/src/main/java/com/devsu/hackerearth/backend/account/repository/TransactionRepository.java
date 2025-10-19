package com.devsu.hackerearth.backend.account.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.devsu.hackerearth.backend.account.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM  Transaction t WHERE t.accountId IN :accountIds AND t.date BETWEEN :start AND :end AND t.deleted = false ORDER BY t.date")

    List<Transaction> findByAccountIdInAndDateBetweenAndDeletedFalse(
            @Param("accountIds") List<Long> accountIds,
            @Param("start") Date start,
            @Param("end") Date end);

    List<Transaction> findByAccountIdAndDeletedFalseOrderByDateDesc(Long accountId);

    List<Transaction> findFirstByAccountIdAndDeletedFalseOrderByDateDesc(Long accountId);

    List<Transaction> findByDeletedFalse();

    long countByDeletedFalse();

}
