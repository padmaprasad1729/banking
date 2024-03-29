package com.prasad.banking.mapper;

import com.prasad.banking.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    @Mappings({
            @Mapping(target = "address", source = "address"),
            @Mapping(target = "mobileNumber", source = "mobileNumber")
    })
    Customer dtoToDao(CustomerDTO createCustomerDTO);

    @Mappings({
            @Mapping(target = "address", source = "address"),
            @Mapping(target = "mobileNumber", source = "mobileNumber")
    })
    CustomerDTO daoToDTO(Customer customer);

    MobileNumber dtoToDao(MobileNumberDTO mobileNumberDTO);

    @Mappings({
            @Mapping(target = "countryCode", source = "countryCode"),
            @Mapping(target = "number", source = "number")
    })
    MobileNumberDTO daoToDTO(MobileNumber mobileNumber);

    Address dtoToDao(AddressDTO addressDTO);

    @Mappings({
            @Mapping(target = "houseNumber", source = "houseNumber"),
            @Mapping(target = "addition", source = "addition"),
            @Mapping(target = "streetName", source = "streetName"),
            @Mapping(target = "city", source = "city"),
            @Mapping(target = "pinCode", source = "pinCode"),
            @Mapping(target = "region", source = "region"),
            @Mapping(target = "country", source = "country")
    })
    AddressDTO daoToDTO(Address address);
}