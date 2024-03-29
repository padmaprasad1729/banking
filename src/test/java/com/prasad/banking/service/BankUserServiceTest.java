package com.prasad.banking.service;

import com.prasad.banking.model.BankUser;
import com.prasad.banking.model.Role;
import com.prasad.banking.repository.BankUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BankUserServiceTest {

    @Mock
    private BankUserRepository userRepository;

    @InjectMocks
    private BankUserService bankUserService;

    @Test
    public void testUserDetailsService_UserFound() {
        BankUser bankUser = getBankUser();
        when(userRepository.findByCustomerId(anyString())).thenReturn(Optional.of(bankUser));

        UserDetailsService userDetailsService = bankUserService.userDetailsService();
        UserDetails userDetails = userDetailsService.loadUserByUsername("customerId");

        assertNotNull(userDetails);
        assertEquals(bankUser.getCustomerId(), userDetails.getUsername());
    }

    @Test
    public void testUserDetailsService_UserNotFound() {
        when(userRepository.findByCustomerId(anyString())).thenReturn(Optional.empty());

        UserDetailsService userDetailsService = bankUserService.userDetailsService();
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("customerId"));
    }

    private BankUser getBankUser() {
        return BankUser.builder()
                .customerId("customerId")
                .password("password")
                .role(Role.CUSTOMER)
                .build();
    }
}