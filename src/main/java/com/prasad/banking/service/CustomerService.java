package com.prasad.banking.service;

import com.prasad.banking.exception.BankingSystemBadRequestException;
import com.prasad.banking.exception.CustomerNotFoundException;
import com.prasad.banking.mapper.CustomerMapper;
import com.prasad.banking.model.Customer;
import com.prasad.banking.model.CustomerDTO;
import com.prasad.banking.model.CustomerStatus;
import com.prasad.banking.repository.CustomerRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.prasad.banking.conf.BankingConstants.INITIAL_CUSTOMER_ID_PREFIX;
import static com.prasad.banking.conf.BankingConstants.INITIAL_CUSTOMER_ID_SUFFIX;

@Service
public class CustomerService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final CustomerRepository customerRepo;

    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepo, CustomerMapper customerMapper) {
        this.customerRepo = customerRepo;
        this.customerMapper = customerMapper;
    }

    public CustomerDTO createCustomer(CustomerDTO createCustomerDTO) {
        Customer customer = customerMapper.dtoToDao(createCustomerDTO);
        Long uniqueNumber = getUniqueNumber();
        String customerID = createUniqueCustomerID(uniqueNumber);
        customer.setUniqueNumber(uniqueNumber);
        customer.setCustomerId(customerID);
        customer.setStatus(CustomerStatus.ACTIVE);
        return customerMapper.daoToDTO(customerRepo.save(customer));
    }


    public Customer getCustomer(String customerId) {
        if (StringUtils.isBlank(customerId)) {
            throw new BankingSystemBadRequestException("customerId: " + customerId);
        }
        return customerRepo.findByCustomerId(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found for the provided customerId: " + customerId));
    }

    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }


    public Customer updateCustomer(String customerId, CustomerDTO customerDTO) {
        Customer customer = getCustomer(customerId);

        if (customer.getFirstName().equals(customerDTO.getFirstName())) {
            log.info("CustomerId: {}, firstName: {}, updated firstName: {}", customerId, customer.getFirstName(), customerDTO.getFirstName());
            customer.setFirstName(customerDTO.getFirstName());
        }

        if (customer.getLastName().equals(customerDTO.getLastName())) {
            log.info("CustomerId: {}, lastName: {}, updated lastName: {}", customerId, customer.getLastName(), customerDTO.getLastName());
            customer.setLastName(customerDTO.getLastName());
        }

        if (customer.getAddress().equals(customerDTO.getAddress())) {
            log.info("CustomerId: {}, address: {}, updated address: {}", customerId, customer.getAddress(), customerDTO.getAddress());
            customer.setAddress(customerMapper.dtoToDao(customerDTO.getAddress()));
        }

        if (customer.getMobileNumber().equals(customerDTO.getMobileNumber())) {
            log.info("CustomerId: {}, mobileNumber: {}, updated mobileNumber: {}", customerId, customer.getMobileNumber(), customerDTO.getMobileNumber());
            customer.setMobileNumber(customerMapper.dtoToDao(customerDTO.getMobileNumber()));
        }

        if (customer.getEmailAddress().equals(customerDTO.getEmailAddress())) {
            log.info("CustomerId: {}, emailAddress: {}, updated emailAddress: {}", customerId, customer.getEmailAddress(), customerDTO.getEmailAddress());
            customer.setEmailAddress(customerDTO.getEmailAddress());
        }

        return customerRepo.save(customer);
    }

    public void deleteCustomer(String customerId) {
        if (StringUtils.isBlank(customerId)) {
            throw new BankingSystemBadRequestException("customerId: " + customerId);
        }
        Customer customer = getCustomer(customerId);
        customer.setStatus(CustomerStatus.DELETED);
        customerRepo.save(customer);
    }

    private Long getUniqueNumber() {
        return customerRepo.findMaxUniqueNumber() == null ? INITIAL_CUSTOMER_ID_SUFFIX + 1 : customerRepo.findMaxUniqueNumber() + 1;
    }

    private String createUniqueCustomerID(Long uniqueNumber) {
        return INITIAL_CUSTOMER_ID_PREFIX + uniqueNumber;
    }
}
