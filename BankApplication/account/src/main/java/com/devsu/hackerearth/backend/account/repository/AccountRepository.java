package com.devsu.hackerearth.backend.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

import com.devsu.hackerearth.backend.account.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByClientIdAndDeletedFalse(Long clienteId);

    Optional<Account> findByNumberAndDeletedFalse(String number);

    boolean existsByNumberAndDeletedFalse(String number);

    List<Account> findByDeletedFalse();

    long countByDeletedFalse();

}
