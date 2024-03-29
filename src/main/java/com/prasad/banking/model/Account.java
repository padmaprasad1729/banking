package com.prasad.banking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@Entity
@Table(name = "Account")
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Long uniqueNumber;

    private String accountNumber;

    private String customerId;

    private AccountType accountType;

    private AccountStatus accountStatus;

    private BigDecimal totalAmount;

    private BigDecimal transactionLimit;

}
