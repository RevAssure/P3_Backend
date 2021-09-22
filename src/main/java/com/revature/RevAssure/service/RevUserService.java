package com.revature.RevAssure.service;

import com.revature.RevAssure.dto.AuthenticationRequest;
import com.revature.RevAssure.dto.AuthenticationResponse;
import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.repository.RevUserRepository;
import com.revature.RevAssure.util.JwtUtil;
import org.postgresql.core.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RevUserService {
    private static final Logger log = LoggerFactory.getLogger(ConnectionFactory.class);
    @Autowired
    private final RevUserRepository revUserRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RevUserDetailsService revUserDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    public RevUserService(RevUserRepository revUserRepository) {
        this.revUserRepository = revUserRepository;
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
     * @return ResponseEntity with 200 status and a JWT in the body, or a 403 with no body
     * @throws Exception
     */
    public ResponseEntity<?> authenticate(AuthenticationRequest authReq) throws Exception {
        log.info("verify user");
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authReq.getUsername(), authReq.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            e.printStackTrace();
            log.debug("BadCredentials Exception");
            throw new BadCredentialsException("Invalid Credentials", e);
        }
        final UserDetails userDetails = revUserDetailsService.loadUserByUsername(authReq.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        AuthenticationResponse authResp = new AuthenticationResponse(jwt);
        return ResponseEntity.ok(authResp);
    }

    /**
     * Method to find a RevUser with just the username
     * @param username the username used to find the RevUser
     * @return RevUser
     */
    public RevUser getRevUserByUsername(String username) {
        log.info("get user information by username");
        log.warn("Might throw Runtime Exception");
        return revUserRepository.findByUsername(username).orElseThrow(RuntimeException::new);
    }
}
