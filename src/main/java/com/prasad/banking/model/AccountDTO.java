package com.prasad.banking.model;

import java.math.BigDecimal;

public record AccountDTO(String accountNumber, AccountType accountType, BigDecimal totalAmount, BigDecimal transactionLimit) {
}
