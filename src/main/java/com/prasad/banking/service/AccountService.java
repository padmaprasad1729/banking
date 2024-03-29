package com.prasad.banking.service;

import com.prasad.banking.exception.AccountNotFoundException;
import com.prasad.banking.exception.BankingSystemBadRequestException;
import com.prasad.banking.exception.BankingSystemFailureException;
import com.prasad.banking.mapper.AccountMapper;
import com.prasad.banking.model.*;
import com.prasad.banking.repository.AccountRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.prasad.banking.conf.BankingConstants.INITIAL_CUSTOMER_ID_SUFFIX;
import static com.prasad.banking.conf.BankingConstants.TRANSACTION_LIMIT;

@Service
public class AccountService {

    private final AccountRepository accountRepo;

    private final AccountMapper accountMapper;

    private final CustomerService customerService;

    private final TransactionService transactionService;

    @Value("${banking.country.code}")
    private String bankingCountryCode;

    @Value("${banking.bank.number}")
    private String bankingBankNumber;

    @Value("${banking.bank.shortname}")
    private String bankingBankShortname;

    public AccountService(AccountRepository accountRepo, AccountMapper accountMapper, CustomerService customerService, TransactionService transactionService) {
        this.accountRepo = accountRepo;
        this.accountMapper = accountMapper;
        this.customerService = customerService;
        this.transactionService = transactionService;
    }

    public AccountDTO createAccount(AccountType accountType, String customerId, BigDecimal initialAmount) {
        Customer customer = customerService.getCustomer(customerId);
        Long uniqueNumber = getUniqueNumber();
        String accountNumber = createUniqueAccountNumber(uniqueNumber);
        Account account = Account.builder()
                .uniqueNumber(uniqueNumber)
                .accountNumber(accountNumber)
                .customerId(customer.getCustomerId())
                .accountType(accountType)
                .accountStatus(AccountStatus.CREATED)
                .totalAmount(initialAmount)
                .transactionLimit(TRANSACTION_LIMIT)
                .build();
        Account newAccount = accountRepo.save(account);

        if (initialAmount.compareTo(BigDecimal.ZERO) > 0) {
            Transaction transaction = new Transaction(customerId, accountNumber, TransactionType.DEBIT, initialAmount, accountNumber, accountNumber, "initial amount added");
            Transaction created = transactionService.createTransaction(transaction);
        }

        return accountMapper.daoToDTO(newAccount);
    }

    public AccountDTO getAccount(String customerId, String accountNumber) {
        if (StringUtils.isAnyBlank(customerId, accountNumber)) {
            throw new BankingSystemBadRequestException("customerId: " + customerId + " ,accountNumber: " + accountNumber);
        }
        Account account = accountRepo.findByCustomerIdAndAccountId(customerId, accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account Not found for the customer: " + customerId + " and accountNumber: " + accountNumber));
        return accountMapper.daoToDTO(account);
    }

    public List<AccountDTO> getAllAccounts(String customerId) {
        if (StringUtils.isBlank(customerId)) {
            throw new BankingSystemBadRequestException("customerId: " + customerId);
        }
        List<Account> accounts = accountRepo.findAllByCustomerId(customerId);
        return accounts.stream().map(accountMapper::daoToDTO).toList();
    }

    public AccountDTO updateAccountStatus(String customerId, String accountNumber, String accountStatus) {
        AccountStatus status;
        try {
            status = AccountStatus.valueOf(accountStatus);
        } catch (IllegalArgumentException e) {
            throw new BankingSystemBadRequestException("accountStatus: " + accountStatus, e);
        }
        AccountDTO accountDTO = getAccount(customerId, accountNumber);
        Account account = accountMapper.dtoToDao(accountDTO);

        account.setAccountStatus(status);

        Account updatedAccount = accountRepo.save(account);
        return accountMapper.daoToDTO(updatedAccount);
    }

    public AccountDTO updateTotalAmount(String customerId, String accountNumber, BigDecimal totalAmount) {
        AccountDTO accountDTO = getAccount(customerId, accountNumber);
        Account account = accountMapper.dtoToDao(accountDTO);
        account.setTotalAmount(totalAmount);
        Account updatedAccount = accountRepo.save(account);
        return accountMapper.daoToDTO(updatedAccount);
    }


    public AccountDTO updateTransactionLimit(String customerId, String accountNumber, BigDecimal transactionLimit) {
        AccountDTO accountDTO = getAccount(customerId, accountNumber);
        Account account = accountMapper.dtoToDao(accountDTO);

        if (transactionLimit.compareTo(BigDecimal.ZERO) <= 0 || transactionLimit.compareTo(TRANSACTION_LIMIT) >= 0) {
            throw new BankingSystemBadRequestException("Transaction Limit should be between 0 and " + TRANSACTION_LIMIT);
        }

        account.setTransactionLimit(transactionLimit);

        Account updatedAccount = accountRepo.save(account);
        return accountMapper.daoToDTO(updatedAccount);
    }

    public void deleteAccount(String customerId, String accountNumber) {
        AccountDTO accountDTO = getAccount(customerId, accountNumber);

        Account account = accountMapper.dtoToDao(accountDTO);

        if (account.getAccountStatus() == AccountStatus.DELETED) {
            throw new BankingSystemFailureException("Account already deleted");
        }

        if (account.getTotalAmount().longValue() > 0) {
            throw new BankingSystemFailureException("Cannot delete NON-ZERO balance account");
        }

        account.setAccountStatus(AccountStatus.DELETED);

        accountRepo.save(account);
    }


    public Long getUniqueNumber() {
        return accountRepo.findMaxUniqueNumber() == null ? INITIAL_CUSTOMER_ID_SUFFIX + 1 : accountRepo.findMaxUniqueNumber() + 1;
    }

    private String createUniqueAccountNumber(Long uniqueNumber) {
        return bankingCountryCode + bankingBankNumber + bankingBankShortname + uniqueNumber;
    }

}
