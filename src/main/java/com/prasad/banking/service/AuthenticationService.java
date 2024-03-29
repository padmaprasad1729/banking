package com.prasad.banking.service;

import com.prasad.banking.model.JwtAuthenticationResponse;
import com.prasad.banking.model.Role;
import com.prasad.banking.model.BankUser;
import com.prasad.banking.repository.BankUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final BankUserRepository bankUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public JwtAuthenticationResponse signUp(String username, String password, Role role) {
        BankUser user = getUser(username, passwordEncoder.encode(password), role);
        if (bankUserRepository.findByCustomerId(user.getCustomerId()).isPresent()) {
            throw new IllegalArgumentException("User with customerId " + user.getCustomerId() + " already exists.");
        }
        bankUserRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }


    public JwtAuthenticationResponse signIn(String username, String password, Role role) {
        BankUser user = getUser(username, password, role);
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getCustomerId(), user.getPassword()));
        var customer = bankUserRepository.findByCustomerId(user.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid customerId or password."));
        var jwt = jwtService.generateToken(customer);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    private BankUser getUser(String customerId, String password, Role role) {
        return BankUser.builder()
                .role(role)
                .customerId(customerId)
                .password(password)
                .build();
    }
}