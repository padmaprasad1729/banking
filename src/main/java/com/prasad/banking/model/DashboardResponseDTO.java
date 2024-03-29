package com.prasad.banking.model;

import java.util.List;
import java.util.Map;

public record DashboardResponseDTO(Customer customer, Map<AccountDTO, List<TransactionDTO>> accountsAndTransactions) {
}
