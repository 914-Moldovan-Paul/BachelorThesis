package com.thesis.project.service;

import com.thesis.project.dto.CredentialsDto;
import com.thesis.project.dto.SignUpDto;
import com.thesis.project.dto.UserDto;
import com.thesis.project.exception.AppException;
import com.thesis.project.model.User;
import com.thesis.project.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.CharBuffer;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoginSuccess() {
        CredentialsDto credentials = new CredentialsDto();
        credentials.setUsername("user");
        credentials.setPassword("password".toCharArray());

        User user = new User();
        user.setUsername("user");
        user.setPassword("encodedPassword");

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(CharBuffer.wrap(credentials.getPassword()), user.getPassword())).thenReturn(true);

        UserDto result = userService.login(credentials);
        assertNotNull(result);
    }

    @Test
    public void testLoginFailure() {
        CredentialsDto credentials = new CredentialsDto();
        credentials.setUsername("user");
        credentials.setPassword("password".toCharArray());

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        AppException exception = assertThrows(AppException.class, () -> userService.login(credentials));
        assertEquals("Unknown username and password combination", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    public void testRegisterSuccess() {
        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setUsername("newUser");
        signUpDto.setPassword("password".toCharArray());

        User user = new User();
        user.setUsername(signUpDto.getUsername());
        user.setPassword("encodedPassword");

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(any(CharBuffer.class))).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto result = userService.register(signUpDto);
        assertNotNull(result);
        assertEquals(signUpDto.getUsername(), result.getUsername());
    }


    @Test
    public void testRegisterFailure() {
        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setUsername("existingUser");
        signUpDto.setPassword("password".toCharArray());

        User existingUser = new User();
        existingUser.setUsername("existingUser");

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(existingUser));

        AppException exception = assertThrows(AppException.class, () -> userService.register(signUpDto));
        assertEquals("Login already exists", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }
}
