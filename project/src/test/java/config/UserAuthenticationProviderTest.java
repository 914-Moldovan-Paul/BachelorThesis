package com.thesis.project.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.thesis.project.dto.UserDto;
import com.thesis.project.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Base64;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserAuthenticationProviderTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserAuthenticationProvider userAuthenticationProvider;

    private String secretKey;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        secretKey = Base64.getEncoder().encodeToString("secret key".getBytes());
        ReflectionTestUtils.setField(userAuthenticationProvider, "secretKey", "secret key");
        userAuthenticationProvider.init();
    }

    @Test
    void testCreateToken() {
        String login = "testUser";
        String token = userAuthenticationProvider.createToken(login);

        assertNotNull(token);

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        DecodedJWT decodedJWT = JWT.require(algorithm).build().verify(token);

        assertEquals(login, decodedJWT.getSubject());
        assertTrue(decodedJWT.getExpiresAt().after(new Date()));
    }

    @Test
    void testValidateToken() {
        String login = "testUser";
        UserDto userDto = new UserDto();
        userDto.setUsername(login);

        String token = userAuthenticationProvider.createToken(login);

        when(userService.findByUsername(anyString())).thenReturn(userDto);

        Authentication authentication = userAuthenticationProvider.validateToken(token);

        assertNotNull(authentication);
        assertEquals(login, ((UserDto) authentication.getPrincipal()).getUsername());
        assertTrue(authentication.getAuthorities().isEmpty());
    }
}
