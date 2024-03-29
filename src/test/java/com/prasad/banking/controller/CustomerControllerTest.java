package com.prasad.banking.controller;

import com.prasad.banking.model.*;
import com.prasad.banking.service.AccountService;
import com.prasad.banking.service.CustomerService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private CustomerController customerController;

    @Test
    public void testCreateCustomer() {
        CustomerDTO createCustomerDTO = getCustomerDTO();
        when(customerService.createCustomer(any())).thenReturn(createCustomerDTO);

        AccountDTO accountDTO = getAccountDTO();
        when(accountService.createAccount(any(), anyString(), any(BigDecimal.class))).thenReturn(accountDTO);

        ResponseEntity<CustomerResponseDTO> response = customerController.createCustomer(createCustomerDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    public void testGetCustomer() {
        Customer expectedCustomer = getCustomer();
        when(customerService.getCustomer(anyString())).thenReturn(expectedCustomer);

        ResponseEntity<Customer> response = customerController.getCustomer("customerId");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedCustomer, response.getBody());
    }

    @Test
    public void testGetAllCustomers() {
        List<Customer> expectedCustomers = new ArrayList<>(); // Set expected list of customers
        when(customerService.getAllCustomers()).thenReturn(expectedCustomers);

        ResponseEntity<List<Customer>> response = customerController.getAllCustomers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedCustomers, response.getBody());
    }

    @Test
    public void testUpdateCustomer() {
        CustomerDTO createCustomerDTO = getCustomerDTO();
        Customer expectedCustomer = getCustomer();
        when(customerService.updateCustomer(anyString(), any())).thenReturn(expectedCustomer);

        ResponseEntity<Customer> response = customerController.updateCustomer("customerId", createCustomerDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedCustomer, response.getBody());
    }

    @Test
    public void testDeleteCustomer() {
        List<AccountDTO> accounts = new ArrayList<>();

        when(accountService.getAllAccounts(anyString())).thenReturn(accounts);

        ResponseEntity<String> response = customerController.deleteCustomer("customerId");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("deleted", response.getBody());
    }

    private Customer getCustomer() {
        Address address = Address.builder().build();
        MobileNumber mobileNumber = MobileNumber.builder().build();
        return new Customer(1, 1000L, "customerId", "Tom", "Cruise", address, mobileNumber, "emailAddress", CustomerStatus.ACTIVE);
    }

    private CustomerDTO getCustomerDTO() {
        AddressDTO addressDTO = new AddressDTO("11", "1", "streetName", "hilversum", "1111 AA", "Noord Holland", "Netherlands");
        return new CustomerDTO("customerId", "Tom", "Cruise", addressDTO, new MobileNumberDTO("+01", "9823123123"), "emailAddress", CustomerStatus.ACTIVE);
    }

    private AccountDTO getAccountDTO() {
        return new AccountDTO("accountNumber3", AccountType.CURRENT_ACCOUNT, BigDecimal.TEN, TRANSACTION_LIMIT);
    }
}