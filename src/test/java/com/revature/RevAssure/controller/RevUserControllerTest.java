package com.revature.RevAssure.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.RevAssure.dto.AuthenticationRequest;
import com.revature.RevAssure.dto.AuthenticationResponse;
import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.service.RevUserDetailsService;
import com.revature.RevAssure.service.RevUserService;
import com.revature.RevAssure.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = RevUserController.class)
class RevUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private RevUserController revUserControllerTest;

    @MockBean
    private RevUserService mockRevUserService;

    @MockBean
    private RevUserDetailsService mockRevUserDetailsService;

    @MockBean
    private JwtUtil mockJwtUtil;


    private RevUser revUser;
    private ResponseEntity responseEntity;
    private AuthenticationResponse authenticationResponse;
    private AuthenticationRequest authenticationRequest;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        revUser = new RevUser();
        revUser.setId(1);
        revUser.setFirstName("test0");
        revUser.setLastName("test1");
        revUser.setUsername("tester");
        revUser.setPassword("password");
        revUser.setTrainer(true);

        responseEntity = ResponseEntity.ok("ok");

        authenticationResponse = new AuthenticationResponse("jwt");
        authenticationRequest = new AuthenticationRequest("tester", "password");
    }

    @Test
    @Disabled
    @DisplayName("Should return a RevUser JSON in Response Body when making POST request to /revuser/register")
    void createUserPass() throws Exception {
        when(mockRevUserService.saveNewRevUser(revUser)).thenReturn(revUser);
        mockMvc.perform(post("/revuser/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(revUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andReturn();
    }

    @Test
    @Disabled
    @DisplayName("Should return JWT token in a Response Body when making POST request to /revuser/authenticate")
    void createAuthenticationTokenPass() throws Exception {
        when(mockRevUserService.authenticate(authenticationRequest)).thenReturn(responseEntity);
        mockMvc.perform(post("/revuser/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(authenticationRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andReturn();
    }

    @WithMockUser
    @Test
    @DisplayName("Should return RevUser object when making GET request to endpoint /revuser")
    void getUserPass() throws Exception {
        when(mockJwtUtil.extractUser(mockRevUserService)).thenReturn(revUser);
        mockMvc.perform(get("/revuser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(revUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andReturn();
    }

    @Test
    @DisplayName("Should return Forbidden Http Status when making GET request to endpoint /revuser")
    void getUserFail() throws Exception {
        mockMvc.perform(get("/revuser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(revUser)))
                .andExpect(status().isForbidden());
    }
}