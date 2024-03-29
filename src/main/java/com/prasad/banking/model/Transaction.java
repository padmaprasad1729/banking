package com.prasad.banking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "Transaction")
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    public Transaction(String customerId, String accountNumber, TransactionType type, BigDecimal amount, String madeBy, String madeTo, String message) {
        this.dateTime = LocalDateTime.now();
        this.transactionId = UUID.randomUUID().toString();
        this.customerId = customerId;
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.madeBy = madeBy;
        this.madeTo = madeTo;
        this.message = message;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String transactionId;

    private String customerId;

    private String accountNumber;

    private TransactionType type;

    private BigDecimal amount;

    private LocalDateTime dateTime;

    private String madeBy;

    private String madeTo;

    private String message;

}
