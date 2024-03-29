package com.prasad.banking.controller;

import com.prasad.banking.conf.BankingConstants;
import com.prasad.banking.mapper.TransactionMapper;
import com.prasad.banking.model.Transaction;
import com.prasad.banking.model.TransactionDTO;
import com.prasad.banking.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(BankingConstants.API_VERSION_V1)
public class TransactionController {

    private final TransactionService transactionService;

    private final TransactionMapper transactionMapper;

    public TransactionController(TransactionService transactionService, TransactionMapper transactionMapper) {
        this.transactionService = transactionService;
        this.transactionMapper = transactionMapper;
    }

    @PostMapping("/transactions")
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionDTO transactionDTO) {
        return ResponseEntity.ok(transactionService.createTransaction(transactionMapper.dtoToDao(transactionDTO)));
    }

    @GetMapping("/transactions/{transactionId}")
    public ResponseEntity<TransactionDTO> getTransaction(@RequestParam String accountNumber, @PathVariable String transactionId) {
        return ResponseEntity.ok(transactionService.getTransaction(accountNumber, transactionId));
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionDTO>> getAllTransaction(@RequestParam String accountNumber) {
        return ResponseEntity.ok(transactionService.getAllTransactions(accountNumber));
    }
}
