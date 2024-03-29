package com.prasad.banking.service;

import com.prasad.banking.exception.BankingSystemBadRequestException;
import com.prasad.banking.exception.InsufficientBalanceException;
import com.prasad.banking.mapper.TransactionMapper;
import com.prasad.banking.model.AccountDTO;
import com.prasad.banking.model.Transaction;
import com.prasad.banking.model.TransactionDTO;
import com.prasad.banking.repository.TransactionRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.prasad.banking.conf.BankingConstants.TRANSACTION_LIMIT;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepo;

    private final TransactionMapper transactionMapper;

    private final AccountService accountService;

    public TransactionService(TransactionRepository transactionRepo, TransactionMapper transactionMapper, @Lazy AccountService accountService) {
        this.transactionRepo = transactionRepo;
        this.transactionMapper = transactionMapper;
        this.accountService = accountService;
    }

    public Transaction createTransaction(Transaction transaction) {

        JwtService.verifyCustomer("createTransaction", transaction.getCustomerId());
        AccountDTO account = accountService.getAccount(transaction.getCustomerId(), transaction.getAccountNumber());

        if (transaction.getAmount().compareTo(TRANSACTION_LIMIT) >= 0 ) {
            throw new BankingSystemBadRequestException("Transaction limit is " + TRANSACTION_LIMIT);
        }

        BigDecimal totalAmount = switch (transaction.getType()) {
            case DEBIT -> subtractOrThrow(account.totalAmount(), transaction.getAmount());
            case CREDIT -> sumOrThrow(account.totalAmount(), transaction.getAmount());
        };

        Transaction savedTransaction = transactionRepo.save(transaction);
        accountService.updateTotalAmount(transaction.getCustomerId(), transaction.getAccountNumber(), totalAmount);

        return savedTransaction;
    }

    public TransactionDTO getTransaction(String accountNumber, String transactionId) {
        Transaction transaction = transactionRepo.findByAccountNumberAndTransactionId(accountNumber, transactionId);
        return transactionMapper.daoToDTO(transaction);
    }

    public List<TransactionDTO> getAllTransactions(String accountNumber) {
        List<Transaction> transactions = transactionRepo.findAllByAccountNumber(accountNumber);
        return transactions.stream().map(transactionMapper::daoToDTO).toList();
    }

    BigDecimal subtractOrThrow(BigDecimal total, BigDecimal amount) {
        if (total.subtract(amount).compareTo(BigDecimal.ZERO) < 0 ) {
            throw new InsufficientBalanceException("Not possible to deduct " + amount + " amount. Total balance = " + total);
        }
        return total.subtract(amount);
    }


    BigDecimal sumOrThrow(BigDecimal total, BigDecimal amount) {
        return total.add(amount);
    }
}
