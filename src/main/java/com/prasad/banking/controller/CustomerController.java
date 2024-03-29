package com.prasad.banking.controller;

import com.prasad.banking.conf.BankingConstants;
import com.prasad.banking.model.*;
import com.prasad.banking.service.AccountService;
import com.prasad.banking.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(BankingConstants.API_VERSION_V1)
public class CustomerController {

    private final CustomerService customerService;

    private final AccountService accountService;

    public CustomerController(CustomerService customerService, AccountService accountService) {
        this.customerService = customerService;
        this.accountService = accountService;
    }

    @PostMapping("/customers")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<CustomerResponseDTO> createCustomer(@RequestBody CustomerDTO createCustomerDTO) {
        CustomerDTO customer = customerService.createCustomer(createCustomerDTO);
        AccountDTO account = accountService.createAccount(AccountType.SAVINGS, customer.getCustomerId(), BigDecimal.ZERO);
        return ResponseEntity.ok().body(new CustomerResponseDTO(customer, List.of(account)));
    }

    @GetMapping("/customers/{customerId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Customer> getCustomer(@PathVariable String customerId) {
        return ResponseEntity.ok(customerService.getCustomer(customerId));
    }

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @PutMapping("/customers/{customerId}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable String customerId, @RequestBody CustomerDTO createCustomerDTO) {
        return ResponseEntity.ok(customerService.updateCustomer(customerId, createCustomerDTO));
    }

    @DeleteMapping("/customers/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable String customerId) {
        List<AccountDTO> accounts = accountService.getAllAccounts(customerId);

        accounts.forEach(account -> accountService.deleteAccount(customerId, account.accountNumber()));

        customerService.deleteCustomer(customerId);

        return ResponseEntity.ok("deleted");
    }
}
