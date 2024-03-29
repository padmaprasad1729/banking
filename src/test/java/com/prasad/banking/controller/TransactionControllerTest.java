package com.prasad.banking.controller;

import com.prasad.banking.model.Transaction;
import com.prasad.banking.model.TransactionDTO;
import com.prasad.banking.model.TransactionType;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    @Test
    public void createTransaction_test() {
        Transaction transaction = getTransaction();
        when(transactionService.createTransaction(any())).thenReturn(transaction);

        ResponseEntity<Transaction> response = transactionController.createTransaction(transaction);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transaction, response.getBody());
    }


    @Test
    public void getTransaction_test() {
        TransactionDTO expectedTransactionDTO = getTransactionDTO();
        when(transactionService.getTransaction(anyString(), anyString())).thenReturn(expectedTransactionDTO);

        ResponseEntity<TransactionDTO> response = transactionController.getTransaction("accountNumber", "transactionId");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedTransactionDTO, response.getBody());
    }

    @Test
    public void getAllTransaction_test() {
        List<TransactionDTO> expectedTransactionDTOs = new ArrayList<>(); // Set expected list of transaction DTOs
        when(transactionService.getAllTransactions(anyString())).thenReturn(expectedTransactionDTOs);

        ResponseEntity<List<TransactionDTO>> response = transactionController.getAllTransaction("accountNumber");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedTransactionDTOs, response.getBody());
    }


    private Transaction getTransaction() {
        return new Transaction("customerId","accountNumber", TransactionType.DEBIT, BigDecimal.TEN, "", "", "");
    }

    private TransactionDTO getTransactionDTO() {
        return new TransactionDTO(LocalDateTime.now(), "transactionId", "customerId","accountNumber", TransactionType.DEBIT, BigDecimal.TEN, "", "", "");
    }
}