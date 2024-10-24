package br.dev.ferreiras.challenge.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.mockito.Mockito.*;

import br.dev.ferreiras.challenge.dto.AccessToken;
import br.dev.ferreiras.challenge.dto.LoginRequestDto;
import br.dev.ferreiras.challenge.entity.User;
import br.dev.ferreiras.challenge.service.AccessTokenService;
import br.dev.ferreiras.challenge.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;


@ExtendWith(MockitoExtension.class)
 class TokenControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private AccessTokenService tokenService;

    @InjectMocks
    private TokenController tokenController;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mockMvc = standaloneSetup(tokenController).build();
    }

    @Test
    void login_Successful() throws Exception {
        LoginRequestDto loginRequest = new LoginRequestDto("user", "password");
        User mockUser = new User("user", bCryptPasswordEncoder.encode("password"));
        AccessToken accessToken = new AccessToken("token123", 3600L);

        when(userService.getUsername("user")).thenReturn(Optional.of(mockUser));
        when(bCryptPasswordEncoder.matches("password", mockUser.getPassword())).thenReturn(true);
        when(tokenService.generateToken("user")).thenReturn(accessToken);

        mockMvc.perform(post("/api/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").value("token123"))
                .andExpect(jsonPath("$.expiresIn").value(3600));
    }

    @Test
    void login_Failed() throws Exception {
        LoginRequestDto loginRequest = new LoginRequestDto("user", "wrongPassword");

        when(userService.getUsername("user")).thenReturn(Optional.of(new User("user", "encodedPassword")));
        when(bCryptPasswordEncoder.matches("wrongPassword", "encodedPassword")).thenReturn(false);

        mockMvc.perform(post("/api/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }
}