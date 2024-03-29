package com.prasad.banking.mapper;

import com.prasad.banking.model.Account;
import com.prasad.banking.model.AccountDTO;
import com.prasad.banking.model.Customer;
import com.prasad.banking.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    Account dtoToDao(AccountDTO accountDTO);
    AccountDTO daoToDTO(Account account);
}
