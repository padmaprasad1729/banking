package com.prasad.banking.service;

import com.prasad.banking.exception.InsufficientBalanceException;
import com.prasad.banking.mapper.TransactionMapper;
import com.prasad.banking.model.*;
import com.prasad.banking.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.prasad.banking.conf.BankingConstants.TRANSACTION_LIMIT;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepo;

    @Mock
    private TransactionMapper transactionMapper;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    public void testCreateTransaction_Debit() {
        Transaction transaction = getTransaction(TransactionType.DEBIT);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        Object bankUser = BankUser.builder().customerId("customerId").build();
        when(authentication.getPrincipal()).thenReturn(bankUser);

        when(accountService.getAccount(anyString(), anyString())).thenReturn(getAccountDTO());
        when(transactionRepo.save(any())).thenReturn(transaction);

        Transaction result = transactionService.createTransaction(transaction);

        assertNotNull(result);
        assertEquals(transaction, result);
    }

    @Test
    public void testCreateTransaction_Credit() {
        Transaction transaction = getTransaction(TransactionType.CREDIT);
        when(accountService.getAccount(anyString(), anyString())).thenReturn(getAccountDTO());
        when(transactionRepo.save(any())).thenReturn(transaction);

        Transaction result = transactionService.createTransaction(transaction);

        assertNotNull(result);
        assertEquals(transaction, result);
    }

    @Test
    public void testGetTransaction() {
        Transaction transaction = getTransaction(TransactionType.CREDIT);
        when(transactionRepo.findByAccountNumberAndTransactionId(anyString(), anyString())).thenReturn(transaction);
        when(transactionMapper.daoToDTO(any())).thenReturn(getTransactionDTO());

        TransactionDTO result = transactionService.getTransaction("accountNumber", "transactionId");

        assertNotNull(result);
    }

    @Test
    public void testGetAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(getTransaction(TransactionType.CREDIT));
        when(transactionRepo.findAllByAccountNumber(anyString())).thenReturn(transactions);

        List<TransactionDTO> result = transactionService.getAllTransactions("accountNumber");

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    public void testSubtractOrThrow() {
        assertDoesNotThrow(() -> transactionService.subtractOrThrow(BigDecimal.TEN, BigDecimal.ONE));
        assertThrows(InsufficientBalanceException.class, () -> transactionService.subtractOrThrow(BigDecimal.ONE, BigDecimal.TEN));
    }

    @Test
    public void testSumOrThrow() {
        BigDecimal result = transactionService.sumOrThrow(BigDecimal.ONE, BigDecimal.TEN);

        assertEquals(BigDecimal.valueOf(11), result);
    }


    private AccountDTO getAccountDTO() {
        return new AccountDTO("accountNumber", AccountType.CURRENT_ACCOUNT, BigDecimal.TEN, TRANSACTION_LIMIT);
    }

    private Transaction getTransaction(TransactionType transactionType) {
        return new Transaction("customerId","accountNumber", transactionType, BigDecimal.TEN, "", "", "");
    }


    private TransactionDTO getTransactionDTO() {
        return new TransactionDTO(LocalDateTime.now(), "transactionId", "customerId","accountNumber", TransactionType.DEBIT, BigDecimal.TEN, "", "", "");
    }
}