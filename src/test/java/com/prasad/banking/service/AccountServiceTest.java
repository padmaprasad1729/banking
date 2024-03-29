package com.prasad.banking.service;

import com.prasad.banking.mapper.AccountMapper;
import com.prasad.banking.model.*;
import com.prasad.banking.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.prasad.banking.conf.BankingConstants.TRANSACTION_LIMIT;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepo;

    @Mock
    private AccountMapper accountMapper;

    @Mock
    private CustomerService customerService;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private AccountService accountService;

    @Test
    public void testCreateAccount() {
        Customer customer = getCustomer();
        when(customerService.getCustomer(anyString())).thenReturn(customer);

        Account account = new Account();
        when(accountRepo.save(any())).thenReturn(account);

        when(transactionService.createTransaction(any())).thenReturn(getTransaction());

        AccountDTO expectedAccountDTO = getAccountDTO();
        when(accountMapper.daoToDTO(any())).thenReturn(expectedAccountDTO);

        AccountDTO result = accountService.createAccount(AccountType.CURRENT_ACCOUNT, customer.getCustomerId(), BigDecimal.TEN);

        assertNotNull(result);
        assertEquals(expectedAccountDTO, result);
    }

    @Test
    public void testGetAccount() {
        Account account = getAccount();
        when(accountRepo.findByCustomerIdAndAccountId(anyString(), anyString())).thenReturn(Optional.of(account));

        AccountDTO expectedAccountDTO = getAccountDTO();
        when(accountMapper.daoToDTO(any())).thenReturn(expectedAccountDTO);

        AccountDTO result = accountService.getAccount(account.getCustomerId(), account.getAccountNumber());

        assertNotNull(result);
        assertEquals(expectedAccountDTO, result);
    }

    @Test
    public void testGetAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        accounts.add(getAccount());
        when(accountRepo.findAllByCustomerId(anyString())).thenReturn(accounts);

        List<AccountDTO> expectedAccountDTOs = new ArrayList<>();
        expectedAccountDTOs.add(getAccountDTO());
        when(accountMapper.daoToDTO(any())).thenReturn(getAccountDTO());

        List<AccountDTO> result = accountService.getAllAccounts("customerId");

        assertNotNull(result);
        assertEquals(expectedAccountDTOs.size(), result.size());
    }


    @Test
    public void testUpdateAccountStatus() {
        AccountDTO expectedAccountDTO = getAccountDTO();
        Account account = getAccount();
        when(accountMapper.dtoToDao(any())).thenReturn(account);
        when(accountMapper.daoToDTO(any())).thenReturn(expectedAccountDTO);
        when(accountRepo.save(any())).thenReturn(account);
        when(accountRepo.findByCustomerIdAndAccountId(anyString(), anyString())).thenReturn(Optional.of(account));

        AccountDTO result = accountService.updateAccountStatus("customerId", "accountNumber", "ACTIVE");

        assertNotNull(result);
        assertEquals(expectedAccountDTO, result);
    }

    @Test
    public void testUpdateTotalAmount() {
        AccountDTO expectedAccountDTO = getAccountDTO();
        Account account = getAccount();
        when(accountMapper.dtoToDao(any())).thenReturn(new Account());
        when(accountMapper.daoToDTO(any())).thenReturn(expectedAccountDTO);
        when(accountRepo.save(any())).thenReturn(new Account());
        when(accountRepo.findByCustomerIdAndAccountId(anyString(), anyString())).thenReturn(Optional.of(account));

        AccountDTO result = accountService.updateTotalAmount("customerId", "accountNumber", BigDecimal.TEN);

        assertNotNull(result);
        assertEquals(expectedAccountDTO, result);
    }


    @Test
    public void testUpdateTransactionLimit() {
        AccountDTO expectedAccountDTO = getAccountDTO();
        Account account = getAccount();
        when(accountMapper.dtoToDao(any())).thenReturn(account);
        when(accountMapper.daoToDTO(any())).thenReturn(expectedAccountDTO);
        when(accountRepo.save(any())).thenReturn(account);
        when(accountRepo.findByCustomerIdAndAccountId(anyString(), anyString())).thenReturn(Optional.of(account));

        AccountDTO result = accountService.updateTransactionLimit("customerId", "accountNumber", BigDecimal.TEN);

        assertNotNull(result);
        assertEquals(expectedAccountDTO, result);
    }

    @Test
    public void testDeleteAccount() {
        AccountDTO expectedAccountDTO = getAccountDTO();
        Account account = getAccount();
        when(accountMapper.dtoToDao(any())).thenReturn(account);
        when(accountMapper.daoToDTO(any())).thenReturn(expectedAccountDTO);
        when(accountRepo.save(any())).thenReturn(account);
        when(accountRepo.findByCustomerIdAndAccountId(anyString(), anyString())).thenReturn(Optional.of(account));

        accountService.updateTotalAmount("customerId", "accountNumber", BigDecimal.ZERO);
        assertDoesNotThrow(() -> accountService.deleteAccount("customerId", "accountNumber"));
    }


    private Customer getCustomer() {
        Customer customer = new Customer();
        customer.setCustomerId("customerId");
        return customer;
    }

    private Account getAccount() {
        Account account = new Account();
        account.setAccountNumber("accountNumber");
        account.setCustomerId("customerId");
        account.setTotalAmount(BigDecimal.TEN);
        account.setTransactionLimit(TRANSACTION_LIMIT);
        return account;
    }

    private AccountDTO getAccountDTO() {
        return new AccountDTO("accountNumber", AccountType.CURRENT_ACCOUNT, BigDecimal.TEN, TRANSACTION_LIMIT);
    }

    private Transaction getTransaction() {
        return new Transaction("customerId", "accountNumber", TransactionType.DEBIT, BigDecimal.TEN, "", "", "");
    }
}