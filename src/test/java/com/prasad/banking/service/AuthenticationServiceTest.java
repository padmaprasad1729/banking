package com.prasad.banking.service;

import com.prasad.banking.model.BankUser;
import com.prasad.banking.model.JwtAuthenticationResponse;
import com.prasad.banking.model.Role;
import com.prasad.banking.repository.BankUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private BankUserRepository bankUserRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    public void testSignUp() {
        BankUser user = getBankUser();
        when(bankUserRepository.findByCustomerId(anyString())).thenReturn(Optional.empty());
        when(bankUserRepository.save(any())).thenReturn(user);
        when(jwtService.generateToken(any())).thenReturn("token");

        JwtAuthenticationResponse response = authenticationService.signUp("username", "password", Role.CUSTOMER);

        assertNotNull(response);
        assertEquals("token", response.getToken());
    }


    @Test
    public void testSignUp_UserExists() {
        when(bankUserRepository.findByCustomerId(anyString())).thenReturn(Optional.of(getBankUser()));

        assertThrows(IllegalArgumentException.class, () -> authenticationService.signUp("username", "password", Role.CUSTOMER));
    }

    @Test
    public void testSignIn() {
        BankUser user = getBankUser();
        when(bankUserRepository.findByCustomerId(anyString())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(any())).thenReturn("token");

        JwtAuthenticationResponse response = authenticationService.signIn("username", "password", Role.CUSTOMER);

        assertNotNull(response);
        assertEquals("token", response.getToken());
    }

    @Test
    public void testSignIn_InvalidCredentials() {
        when(bankUserRepository.findByCustomerId(anyString())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> authenticationService.signIn("username", "password", Role.CUSTOMER));
    }

    private BankUser getBankUser() {
        return BankUser.builder()
                .customerId("customerId")
                .password("password")
                .role(Role.CUSTOMER)
                .build();
    }
}