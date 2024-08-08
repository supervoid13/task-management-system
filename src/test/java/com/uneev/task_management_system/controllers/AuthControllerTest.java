package com.uneev.task_management_system.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uneev.task_management_system.dto.UserLoginDto;
import com.uneev.task_management_system.dto.UserRegistrationDto;
import com.uneev.task_management_system.services.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthControllerImpl authController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;


    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void shouldLoginAndReturnStatus200_Ok() throws Exception {
        UserLoginDto userLoginDto = new UserLoginDto("test@gmail.com", "qwerty123");
        String userJson = objectMapper.writeValueAsString(userLoginDto);

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson)
        ).andExpect(status().isOk());
    }

    @Test
    public void shouldNotLoginAndReturnStatus400BadRequest() throws Exception {
        UserLoginDto userLoginDto = new UserLoginDto("test@gmail.com123", "qwerty123");
        String userJson = objectMapper.writeValueAsString(userLoginDto);

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldRegisterAndReturnStatus201Created() throws Exception {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto(
                "test@gmail.com",
                "qwerty123",
                "Kik",
                "Butovski"
        );
        String userJson = objectMapper.writeValueAsString(userRegistrationDto);

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson)
        ).andExpect(status().isCreated());
    }

    @Test
    public void shouldNotRegisterAndReturnStatus400BadRequest() throws Exception {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto(
                "someinv@alid$$$mail@@gmail.com123123",
                "qwerty123",
                "Kik",
                "Butovski"
        );
        String userJson = objectMapper.writeValueAsString(userRegistrationDto);

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson)
        ).andExpect(status().isBadRequest());
    }
}
