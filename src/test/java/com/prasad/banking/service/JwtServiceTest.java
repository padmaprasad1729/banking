//package com.prasad.banking.service;
//
//import io.jsonwebtoken.Claims;
//import org.junit.BeforeClass;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.test.util.ReflectionTestUtils;
//
//import java.security.Key;
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class JwtServiceTest {
//
//    @Mock
//    private Claims claims;
//
//    @Mock
//    private Key signingKey;
//
//    @InjectMocks
//    private static JwtService jwtService;
//
//    @BeforeClass
//    public static void beforeClass() {
//        ReflectionTestUtils.setField(jwtService, "token.signing.key", "413F4428472B4B6250655368566D5970337336763979244226452948404D6351");
//    }
//
//    @Test
//    public void testExtractUserName() {
//        ReflectionTestUtils.setField(jwtService, "jwtSigningKey", "413F4428472B4B6250655368566D5970337336763979244226452948404D6351");
//        when(claims.getSubject()).thenReturn("username");
//        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbXBsb3llZTEiLCJpYXQiOjE3MTE2NTMxNjEsImV4cCI6MTcxMTY1MzIyMX0.Dizia6Kj0jksfWQKxJK-S24R7oPsTmZfBpxcleWqVY8";
//
//        String result = jwtService.extractUserName(token);
//
//        assertEquals("username", result);
//    }
//
//    @Test
//    public void testGenerateToken() {
//        UserDetails userDetails = new User("username", "password", null);
//        Map<String, Object> extraClaims = new HashMap<>();
//        extraClaims.put("key", "value");
//
//        String result = jwtService.generateToken(extraClaims, userDetails);
//
//        assertNotNull(result);
//    }
//
//    @Test
//    public void testIsTokenValid() {
//        UserDetails userDetails = new User("username", "password", null);
//        String token = jwtService.generateToken(new HashMap<>(), userDetails);
//
//        boolean result = jwtService.isTokenValid(token, userDetails);
//
//        assertTrue(result);
//    }
//
//    @Test
//    public void testIsTokenValid_TokenExpired() {
//        UserDetails userDetails = new User("username", "password", null);
//        String token = jwtService.generateToken(new HashMap<>(), userDetails);
//        jwtService.extractExpiration(token);
//
//        boolean result = jwtService.isTokenValid(token, userDetails);
//
//        assertFalse(result);
//    }
//
//    @Test
//    public void testIsTokenValid_InvalidUsername() {
//        UserDetails userDetails = new User("username", "password", null);
//        String token = jwtService.generateToken(new HashMap<>(), userDetails);
//        when(claims.getSubject()).thenReturn("invalidUsername");
//
//        boolean result = jwtService.isTokenValid(token, userDetails);
//
//        assertFalse(result);
//    }
//}