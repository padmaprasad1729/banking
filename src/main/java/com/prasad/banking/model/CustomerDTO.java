package com.prasad.banking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    private String customerId;

    private String firstName;

    private String lastName;

    private AddressDTO address;

    private MobileNumberDTO mobileNumber;

    private String emailAddress;

    private CustomerStatus status;
}
