package com.prasad.banking.controller;

import com.prasad.banking.model.JwtAuthenticationResponse;
import com.prasad.banking.model.SignInRequest;
import com.prasad.banking.model.SignUpRequest;
import com.prasad.banking.service.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @Test
    public void signUp_test() {
        JwtAuthenticationResponse expectedResponse = new JwtAuthenticationResponse("token");
        when(authenticationService.signUp(anyString(), anyString(), any())).thenReturn(expectedResponse);

        SignUpRequest request = new SignUpRequest("customerId", "password");
        ResponseEntity<JwtAuthenticationResponse> response = authenticationController.signUp(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void signIn_test() {
        JwtAuthenticationResponse expectedResponse = new JwtAuthenticationResponse("token");
        when(authenticationService.signIn(anyString(), anyString(), any())).thenReturn(expectedResponse);

        SignInRequest request = new SignInRequest("customerId", "password");
        ResponseEntity<JwtAuthenticationResponse> response = authenticationController.signIn(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void signUpForEmployee_test() {
        JwtAuthenticationResponse expectedResponse = new JwtAuthenticationResponse("token");
        when(authenticationService.signUp(anyString(), anyString(), any())).thenReturn(expectedResponse);

        SignUpRequest request = new SignUpRequest("customerId", "password");
        ResponseEntity<JwtAuthenticationResponse> response = authenticationController.signUpForEmployee(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void signInForEmployee_test() {
        JwtAuthenticationResponse expectedResponse = new JwtAuthenticationResponse("token");
        when(authenticationService.signIn(anyString(), anyString(), any())).thenReturn(expectedResponse);

        SignInRequest request = new SignInRequest("customerId", "password");
        ResponseEntity<JwtAuthenticationResponse> response = authenticationController.signInForEmployee(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }
}