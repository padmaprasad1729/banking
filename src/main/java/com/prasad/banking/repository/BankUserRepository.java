package com.prasad.banking.repository;

import com.prasad.banking.model.BankUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BankUserRepository extends JpaRepository<BankUser, Long> {
    @Query("SELECT b FROM BankUser b WHERE b.customerId= :customerId")
    Optional<BankUser> findByCustomerId(@Param("customerId") String customerId);
}
