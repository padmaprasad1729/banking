package com.prasad.banking.service;

import com.prasad.banking.repository.BankUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankUserService{

    private final BankUserRepository userRepository;

    public UserDetailsService userDetailsService() {
        return customerId -> userRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new UsernameNotFoundException("Customer not found for customerId: " + customerId));
    }
}