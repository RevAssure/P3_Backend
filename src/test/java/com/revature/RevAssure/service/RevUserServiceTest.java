package com.revature.RevAssure.service;

import com.revature.RevAssure.dto.AuthenticationRequest;
import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.repository.RevUserRepository;
import com.revature.RevAssure.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

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
    private JwtUtil jwtUtil;

    @Mock
    private PasswordEncoder passwordEncoder;

    private BadCredentialsException e;
    private UsernamePasswordAuthenticationToken UPToken;
    private RevUser revUser = new RevUser();


    @BeforeEach
    public void setUp() {
        revUserService = new RevUserService(revUserRepository, passwordEncoder, authenticationManager, revUserDetailsService, jwtUtil);

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
        assertEquals(1, revUserService.getRevUserByUsername(revUser.getUsername()).getId());
    }

    @Test
    void getRevUserByUsernameTestNoUserFoundException() {
        when(revUserRepository.findByUsername(revUser.getUsername())).thenReturn(Optional.empty());
        assertEquals(null, revUserService.getRevUserByUsername("test"));
    }

    @Test
    void saveNewRevUserTest() {
        when(passwordEncoder.encode(revUser.getPassword())).thenReturn(revUser.getPassword());
        when(revUserRepository.save(revUser)).thenReturn(revUser);
        assertEquals(1, revUserService.saveNewRevUser(revUser).getId());
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
        assertEquals(401, revUserService.authenticate(authenticationRequest).getStatusCodeValue());
    }
}
