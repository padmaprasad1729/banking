package com.prasad.banking.controller;

import com.prasad.banking.model.AccountDTO;
import com.prasad.banking.model.AccountType;
import com.prasad.banking.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.prasad.banking.conf.BankingConstants.TRANSACTION_LIMIT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {

    @Mock
    private AccountService accountService;
    @InjectMocks
    private AccountController accountController;

    @Test
    public void createAccount_test() {
        AccountType accountType = AccountType.CURRENT_ACCOUNT;
        String customerId = "customerId";
        BigDecimal initialAmount = BigDecimal.TEN;
        AccountDTO expectedResponse = getAccountDTO("accountNumber1", accountType, initialAmount, TRANSACTION_LIMIT);

        when(accountService.createAccount(accountType, customerId, initialAmount)).thenReturn(expectedResponse);

        ResponseEntity<AccountDTO> responseEntity = accountController.createAccount(accountType, customerId, initialAmount);

        assertEquals(expectedResponse, responseEntity.getBody());
        assertEquals(ResponseEntity.ok(expectedResponse), responseEntity);
        verify(accountService, times(1)).createAccount(accountType, customerId, initialAmount);

    }

    @Test
    public void testGetAccount_test() {
        AccountType accountType = AccountType.CURRENT_ACCOUNT;
        String customerId = "customerId";
        BigDecimal initialAmount = BigDecimal.TEN;
        AccountDTO expectedResponse = getAccountDTO("accountNumber2", accountType, initialAmount, TRANSACTION_LIMIT);

        when(accountService.getAccount(anyString(), anyString())).thenReturn(expectedResponse);

        ResponseEntity<AccountDTO> response = accountController.getAccount("accountId", "customerId");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void testGetAllAccounts_test() {
        AccountType accountType = AccountType.CURRENT_ACCOUNT;
        String customerId = "customerId";
        BigDecimal initialAmount = BigDecimal.TEN;
        AccountDTO accountDTO = getAccountDTO("accountNumber3", accountType, initialAmount, TRANSACTION_LIMIT.add(BigDecimal.ONE));

        List<AccountDTO> expectedResponse = new ArrayList<>();
        expectedResponse.add(accountDTO);

        when(accountService.getAllAccounts(anyString())).thenReturn(expectedResponse);

        ResponseEntity<List<AccountDTO>> response = accountController.getAllAccounts("customerId");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }


    @Test
    public void updateAccountStatus_test() {
        AccountType accountType = AccountType.CURRENT_ACCOUNT;
        String customerId = "customerId";
        BigDecimal initialAmount = BigDecimal.TEN;
        AccountDTO expectedResponse = getAccountDTO("accountNumber3", accountType, initialAmount, TRANSACTION_LIMIT);

        when(accountService.updateAccountStatus(anyString(), anyString(), anyString())).thenReturn(expectedResponse);

        ResponseEntity<AccountDTO> response = accountController.updateAccountStatus("accountId", "customerId", "active");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void updateTotalAmount_test() {
        AccountType accountType = AccountType.CURRENT_ACCOUNT;
        String customerId = "customerId";
        BigDecimal initialAmount = BigDecimal.TEN;
        AccountDTO expectedResponse = getAccountDTO("accountNumber3", accountType, initialAmount, TRANSACTION_LIMIT);

        when(accountService.updateTotalAmount(anyString(), anyString(), any(BigDecimal.class))).thenReturn(expectedResponse);

        ResponseEntity<AccountDTO> response = accountController.updateTotalAmount("accountId", "customerId", BigDecimal.valueOf(1000));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void updateTransactionLimit_test() {
        AccountType accountType = AccountType.CURRENT_ACCOUNT;
        String customerId = "customerId";
        BigDecimal initialAmount = BigDecimal.TEN;
        AccountDTO expectedResponse = getAccountDTO("accountNumber3", accountType, initialAmount, TRANSACTION_LIMIT);

        when(accountService.updateTransactionLimit(anyString(), anyString(), any(BigDecimal.class))).thenReturn(expectedResponse);

        ResponseEntity<AccountDTO> response = accountController.updateTransactionLimit("accountId", "customerId", BigDecimal.valueOf(500));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void deleteAccount_test() {
        ResponseEntity<String> response = accountController.deleteAccount("accountId", "customerId");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("DELETED", response.getBody());
    }

    private AccountDTO getAccountDTO(String accountNumber, AccountType accountType, BigDecimal totalAmount, BigDecimal transactionLimit) {
        return new AccountDTO(accountNumber, accountType, totalAmount, transactionLimit);
    }
}
