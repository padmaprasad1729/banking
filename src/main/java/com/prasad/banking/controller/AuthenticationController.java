package com.prasad.banking.controller;

import com.prasad.banking.model.*;
import com.prasad.banking.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signUp(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signUp(request.getCustomerId(), request.getPassword(), Role.CUSTOMER));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody SignInRequest request) {
        return ResponseEntity.ok(authenticationService.signIn(request.getCustomerId(), request.getPassword(), Role.CUSTOMER));
    }

    @PostMapping("/signup-for-employee")
    public ResponseEntity<JwtAuthenticationResponse> signUpForEmployee(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signUp(request.getCustomerId(), request.getPassword(), Role.EMPLOYEE));
    }

    @PostMapping("/signin-for-employee")
    public ResponseEntity<JwtAuthenticationResponse> signInForEmployee(@RequestBody SignInRequest request) {
        return ResponseEntity.ok(authenticationService.signIn(request.getCustomerId(), request.getPassword(), Role.EMPLOYEE));
    }
}