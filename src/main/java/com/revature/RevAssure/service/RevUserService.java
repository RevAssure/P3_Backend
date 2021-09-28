package com.revature.RevAssure.service;

import com.revature.RevAssure.dto.AuthenticationRequest;
import com.revature.RevAssure.dto.AuthenticationResponse;
import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.repository.RevUserRepository;
import com.revature.RevAssure.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RevUserService {
    private static final Logger log = LoggerFactory.getLogger(RevUserService.class);

    private final RevUserRepository revUserRepository;


    PasswordEncoder passwordEncoder;


    private final AuthenticationManager authenticationManager;


    private final RevUserDetailsService revUserDetailsService;


    private final JwtUtil jwtTokenUtil;

    @Autowired
    public RevUserService(RevUserRepository revUserRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, RevUserDetailsService revUserDetailsService, JwtUtil jwtUtil) {
        this.revUserRepository = revUserRepository;
        this.authenticationManager = authenticationManager;
        this.revUserDetailsService = revUserDetailsService;
        this.jwtTokenUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Method to persist a new user, coming through the /revuser/register post request
     * @param revUser the RevUser to save
     * @return RevUser
     */
    public RevUser saveNewRevUser(RevUser revUser) {
        log.info("saving User");
        revUser.setPassword(passwordEncoder.encode(revUser.getPassword()));
        return revUserRepository.save(revUser);
    }

    /**
     * Method to authenticate if a user has entered valid log in credentials
     * @param authReq The request body which contains user's credentials to authenticate
     * @return ResponseEntity with 200 status and a JWT in the body, or a 403 without body
     */
    public ResponseEntity<?> authenticate(AuthenticationRequest authReq) {
        log.info("verify user");
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authReq.getUsername(), authReq.getPassword()));
            final UserDetails userDetails = revUserDetailsService.loadUserByUsername(authReq.getUsername());
            final String jwt = jwtTokenUtil.generateToken(userDetails);
            AuthenticationResponse authResp = new AuthenticationResponse(jwt);
            return ResponseEntity.ok(authResp);
        }
        catch (Exception e) {
            log.error("Credentials not recognized during authentication",e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * Method to find a RevUser with just the username
     * @param username the username used to find the RevUser
     * @return RevUser
     */
    public RevUser getRevUserByUsername(String username) {
        log.info("get user information by username");
        try {
            return revUserRepository.findByUsername(username).orElseThrow(RuntimeException::new);
        } catch (RuntimeException e){
            log.error("No user found for given username",e);
            return null;
        }
    }
}
