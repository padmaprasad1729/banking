package com.prasad.banking.controller;

import com.prasad.banking.conf.BankingConstants;
import com.prasad.banking.model.AccountDTO;
import com.prasad.banking.model.Customer;
import com.prasad.banking.model.DashboardResponseDTO;
import com.prasad.banking.model.TransactionDTO;
import com.prasad.banking.service.AccountService;
import com.prasad.banking.service.CustomerService;
import com.prasad.banking.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(BankingConstants.API_VERSION_V1)
public class DashboardController {

    private final CustomerService customerService;

    private final AccountService accountService;

    private final TransactionService transactionService;

    public DashboardController(CustomerService customerService, AccountService accountService, TransactionService transactionService) {
        this.customerService = customerService;
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @PostMapping("/dashboard-api")
    @PreAuthorize("hasRole('CUSTOMER') || hasRole('EMPLOYEE')")
    public ResponseEntity<DashboardResponseDTO> dashboardApi(@RequestParam String customerId) {
        Customer customer = customerService.getCustomer(customerId);
        List<AccountDTO> accounts = accountService.getAllAccounts(customerId);

        Map<AccountDTO, List<TransactionDTO>> accountsAndTransactions = new HashMap<>();
        accounts.forEach(account -> accountsAndTransactions.put(account, transactionService.getAllTransactions(account.accountNumber())));

        return ResponseEntity.ok(new DashboardResponseDTO(customer, accountsAndTransactions));
    }
}
