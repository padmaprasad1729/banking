package com.prasad.banking.controller;

import com.prasad.banking.conf.BankingConstants;
import com.prasad.banking.model.AccountDTO;
import com.prasad.banking.model.AccountType;
import com.prasad.banking.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(BankingConstants.API_VERSION_V1)
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/accounts")
    public ResponseEntity<AccountDTO> createAccount(@RequestParam String accountType, @RequestParam String customerId, @RequestParam BigDecimal initialAmount) {
        AccountDTO account = accountService.createAccount(AccountType.valueOf(accountType), customerId, initialAmount);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable String accountId, @RequestParam String customerId) {
        AccountDTO account = accountService.getAccount(customerId, accountId);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<AccountDTO>> getAllAccounts(@RequestParam String customerId) {
        List<AccountDTO> accounts = accountService.getAllAccounts(customerId);
        return ResponseEntity.ok(accounts);
    }

    @PutMapping("/accounts/{accountNumber}/accountStatus")
    public ResponseEntity<AccountDTO> updateAccountStatus(@PathVariable String accountNumber, @RequestParam String customerId, @RequestParam String accountStatus) {
        AccountDTO account = accountService.updateAccountStatus(customerId, accountNumber, accountStatus);
        return ResponseEntity.ok(account);
    }

    @PutMapping("/accounts/{accountNumber}/totalAmount")
    public ResponseEntity<AccountDTO> updateTotalAmount(@PathVariable String accountNumber, @RequestParam String customerId, @RequestParam BigDecimal totalAmount) {
        AccountDTO account = accountService.updateTotalAmount(customerId, accountNumber, totalAmount);
        return ResponseEntity.ok(account);
    }

    @PutMapping("/accounts/{accountNumber}/transactionLimit")
    public ResponseEntity<AccountDTO> updateTransactionLimit(@PathVariable String accountNumber, @RequestParam String customerId, @RequestParam BigDecimal transactionLimit) {
        AccountDTO account = accountService.updateTransactionLimit(customerId, accountNumber, transactionLimit);
        return ResponseEntity.ok(account);
    }

    @DeleteMapping("/accounts/{accountNumber}")
    public ResponseEntity<String> deleteAccount(@PathVariable String accountNumber, @RequestParam String customerId) {
        accountService.deleteAccount(customerId, accountNumber);
        return ResponseEntity.ok("DELETED");
    }

}
