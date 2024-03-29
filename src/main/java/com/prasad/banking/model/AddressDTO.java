package com.prasad.banking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

    private String houseNumber;
    private String addition;
    private String streetName;
    private String city;
    private String pinCode;
    private String region;
    private String country;
}
