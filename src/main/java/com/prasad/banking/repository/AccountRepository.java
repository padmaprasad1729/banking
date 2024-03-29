package com.prasad.banking.repository;

import com.prasad.banking.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT MAX(a.uniqueNumber) FROM Account a")
    Long findMaxUniqueNumber();

    @Query("SELECT a FROM Account a WHERE a.customerId = :customerId and a.accountNumber = :accountNumber")
    Optional<Account> findByCustomerIdAndAccountId(@Param("customerId") String customerId, @Param("accountNumber") String accountNumber);

    @Query("SELECT a FROM Account a WHERE a.customerId = :customerId")
    List<Account> findAllByCustomerId(@Param("customerId") String customerId);
}
