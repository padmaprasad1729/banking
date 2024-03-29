package com.prasad.banking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class BankingSystemFailureException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BankingSystemFailureException(String message) {
        super(message);
    }

}