package com.revature.RevAssure.service;

import com.revature.RevAssure.dto.AuthenticationRequest;
import com.revature.RevAssure.dto.AuthenticationResponse;
import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.repository.RevUserRepository;
import com.revature.RevAssure.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RevUserServiceTest {

    private RevUserService revUserService;

    @Mock
    private RevUserRepository revUserRepository;

    @Mock
    private UserDetails userDetails;

    @Mock
    private RevUserDetailsService revUserDetailsService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private AuthenticationRequest authenticationRequest;

    @Mock
    private AuthenticationResponse authenticationResponse;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private ResponseEntity responseEntity;

    @Mock
    private Authentication authentication;

    static BadCredentialsException e;
    static UsernamePasswordAuthenticationToken UPToken;
    static RevUser revUser = new RevUser();


    @BeforeEach
    public void setUp() {
        revUserService = new RevUserService(revUserRepository);

        revUser.setId(1);
        revUser.setUsername("test");
        revUser.setPassword("password");
        revUser.setFirstName("fname");
        revUser.setLastName("lname");
        revUser.setTrainer(true);

        authenticationRequest.setUsername("username");
        authenticationRequest.setPassword("password");
        UPToken = new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword());
    }

    @Test
    void getRevUserByUsernameTest() {
        when(revUserRepository.findByUsername(revUser.getUsername())).thenReturn(java.util.Optional.of(revUser));
        Assertions.assertEquals(1, revUserService.getRevUserByUsername(revUser.getUsername()).getId());
    }

    @Test
    void saveNewRevUserTest() {
        when(revUserRepository.save(revUser)).thenReturn(revUser);
        Assertions.assertEquals(1, revUserService.saveNewRevUser(revUser).getId());
    }

    @Test
    void authenticateTest() throws Exception {
        when(authenticationManager.authenticate(UPToken)).thenReturn(UPToken);
        when(revUserDetailsService.loadUserByUsername(authenticationRequest.getUsername())).thenReturn(userDetails);
        when(jwtUtil.generateToken(userDetails)).thenReturn("ok");
        assertEquals(200, revUserService.authenticate(authenticationRequest).getStatusCodeValue());
    }

    @Test
    void authenticateBadCredentialsTest() throws Exception {
        when(authenticationManager.authenticate(UPToken)).thenThrow(BadCredentialsException.class);
        assertThrows(BadCredentialsException.class, () -> {revUserService.authenticate(authenticationRequest);});
    }
}