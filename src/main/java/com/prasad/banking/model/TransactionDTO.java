package com.prasad.banking.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionDTO(LocalDateTime dateTime, String transactionId, String customerId, String accountNumber,
                             TransactionType type, BigDecimal amount, String madeBy, String madeTo, String message) {
}
