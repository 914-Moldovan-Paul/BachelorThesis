package com.thesis.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thesis.project.config.UserAuthenticationProvider;
import com.thesis.project.dto.CredentialsDto;
import com.thesis.project.dto.SignUpDto;
import com.thesis.project.dto.UserDto;
import com.thesis.project.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private UserAuthenticationProvider userAuthenticationProvider;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin() throws Exception {
        CredentialsDto credentialsDto = new CredentialsDto("testuser", "password".toCharArray());
        UserDto userDto = new UserDto(1L, "testuser", "token", "Test", "User");

        when(userService.login(ArgumentMatchers.any(CredentialsDto.class))).thenReturn(userDto);
        when(userAuthenticationProvider.createToken("testuser")).thenReturn("token");

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(credentialsDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(userDto)));
    }

    @Test
    void testRegister() throws Exception {
        SignUpDto signUpDto = new SignUpDto("Test", "User", "testuser", "password".toCharArray());
        UserDto userDto = new UserDto(1L, "testuser", "token", "Test", "User");

        when(userService.register(ArgumentMatchers.any(SignUpDto.class))).thenReturn(userDto);
        when(userAuthenticationProvider.createToken("testuser")).thenReturn("token");

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(userDto)))
                .andExpect(header().string("Location", "/users/1"));
    }
}
