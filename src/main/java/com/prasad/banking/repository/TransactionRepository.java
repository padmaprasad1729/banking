package com.prasad.banking.repository;

import com.prasad.banking.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE t.accountNumber = :accountNumber and t.transactionId = :transactionId")
    Transaction findByAccountNumberAndTransactionId(@Param("accountNumber") String accountNumber, @Param("transactionId") String transactionId);

    @Query("SELECT t FROM Transaction t WHERE t.accountNumber = :accountNumber")
    List<Transaction> findAllByAccountNumber(@Param("accountNumber") String accountNumber);
}
