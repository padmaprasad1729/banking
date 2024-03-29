package com.prasad.banking.controller;

import com.prasad.banking.model.*;
import com.prasad.banking.service.AccountService;
import com.prasad.banking.service.CustomerService;
import com.prasad.banking.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.prasad.banking.conf.BankingConstants.TRANSACTION_LIMIT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DashboardControllerTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private AccountService accountService;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private DashboardController dashboardController;

    @Test
    public void dashboardApi_test() {
        String customerId = "customerId";
        Customer customer = getCustomer();
        when(customerService.getCustomer(customerId)).thenReturn(customer);

        List<AccountDTO> accounts = new ArrayList<>();
        accounts.add(getAccountDTO());
        when(accountService.getAllAccounts(customerId)).thenReturn(accounts);

        Map<AccountDTO, List<TransactionDTO>> accountsAndTransactions = new HashMap<>();

        List<TransactionDTO> transactionDTOS = new ArrayList<>();
        transactionDTOS.add(getTransactionDTO());
        when(transactionService.getAllTransactions(anyString())).thenReturn(transactionDTOS);

        ResponseEntity<DashboardResponseDTO> response = dashboardController.dashboardApi(customerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(response.getBody().accountsAndTransactions().size(), accounts.size());
    }

    private Customer getCustomer() {
        Address address = Address.builder().build();
        MobileNumber mobileNumber = MobileNumber.builder().build();
        return new Customer(1, 1000L, "customerId", "Tom", "Cruise", address, mobileNumber, "emailAddress", CustomerStatus.ACTIVE);
    }


    private AccountDTO getAccountDTO() {
        return new AccountDTO("accountNumber3", AccountType.CURRENT_ACCOUNT, BigDecimal.TEN, TRANSACTION_LIMIT);
    }

    private TransactionDTO getTransactionDTO() {
        return new TransactionDTO(LocalDateTime.now(), "transactionId", "customerId","accountNumber", TransactionType.DEBIT, BigDecimal.TEN, "", "", "");
    }
}