package com.prasad.banking.model;

import java.util.List;

public record CustomerResponseDTO(CustomerDTO customer, List<AccountDTO> accounts) {
}
