package com.prasad.banking.repository;

import com.prasad.banking.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT MAX(c.uniqueNumber) FROM Customer c")
    Long findMaxUniqueNumber();

    @Query("SELECT c FROM Customer c WHERE c.customerId= :customerId")
    Optional<Customer> findByCustomerId(@Param("customerId") String customerId);
}
