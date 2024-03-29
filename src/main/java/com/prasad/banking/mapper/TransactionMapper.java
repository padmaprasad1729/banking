package com.prasad.banking.mapper;

import com.prasad.banking.model.Transaction;
import com.prasad.banking.model.TransactionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    Transaction dtoToDao(TransactionDTO transactionDTO);

    TransactionDTO daoToDTO(Transaction transaction);
}
