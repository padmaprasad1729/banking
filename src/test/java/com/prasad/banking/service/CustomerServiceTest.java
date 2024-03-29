package com.prasad.banking.service;

import com.prasad.banking.exception.CustomerNotFoundException;
import com.prasad.banking.mapper.CustomerMapper;
import com.prasad.banking.model.*;
import com.prasad.banking.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepo;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerService customerService;

    @Test
    public void testCreateCustomer() {
        CustomerDTO customerDTO = getCustomerDTO();
        Customer customer = new Customer();
        when(customerMapper.dtoToDao(customerDTO)).thenReturn(customer);
        when(customerMapper.daoToDTO(customer)).thenReturn(customerDTO);
        when(customerRepo.save(any())).thenReturn(customer);

        CustomerDTO result = customerService.createCustomer(customerDTO);

        assertNotNull(result);
        assertEquals(customerDTO, result);
    }

    @Test
    public void testGetCustomer() {
        Customer customer = getCustomer();
        when(customerRepo.findByCustomerId(anyString())).thenReturn(Optional.of(customer));

        Customer result = customerService.getCustomer("customerId");

        assertNotNull(result);
        assertEquals(customer, result);
    }

    @Test
    public void testGetCustomer_CustomerNotFound() {
        when(customerRepo.findByCustomerId(anyString())).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomer("customerId"));
    }

    @Test
    public void testGetAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        customers.add(getCustomer());
        when(customerRepo.findAll()).thenReturn(customers);

        List<Customer> result = customerService.getAllCustomers();

        assertNotNull(result);
        assertEquals(customers, result);
    }

    @Test
    public void testUpdateCustomer() {
        Customer customer = getCustomer();
        CustomerDTO customerDTO = getCustomerDTO();
        when(customerRepo.findByCustomerId(anyString())).thenReturn(Optional.of(customer));
        when(customerRepo.save(any())).thenReturn(customer);

        Customer result = customerService.updateCustomer("customerId", customerDTO);

        assertNotNull(result);
        assertEquals(customer, result);
    }


    @Test
    public void testDeleteCustomer() {
        Customer customer = getCustomer();
        when(customerRepo.findByCustomerId(anyString())).thenReturn(Optional.of(customer));
        when(customerRepo.save(any())).thenReturn(customer);

        // Testing
        assertDoesNotThrow(() -> customerService.deleteCustomer("customerId"));
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
}