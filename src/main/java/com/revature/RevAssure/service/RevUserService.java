package com.revature.RevAssure.service;

import com.revature.RevAssure.dto.AuthenticationRequest;
import com.revature.RevAssure.dto.AuthenticationResponse;
import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.repository.RevUserRepository;
import com.revature.RevAssure.util.JwtUtil;
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

    public RevUser saveNewRevUser(RevUser revUser) {
        revUser.setPassword(passwordEncoder.encode(revUser.getPassword()));
        return revUserRepository.save(revUser);
    }

    public ResponseEntity<?> authenticate(AuthenticationRequest authReq) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authReq.getUsername(), authReq.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            e.printStackTrace();
            throw new BadCredentialsException("Invalid Credentials", e);
        }
        final UserDetails userDetails = revUserDetailsService.loadUserByUsername(authReq.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        AuthenticationResponse authResp = new AuthenticationResponse(jwt);
        return ResponseEntity.ok(authResp);
    }

    public RevUser getRevUserByUsername(String username) {
        return revUserRepository.findByUsername(username).orElseThrow(RuntimeException::new);
    }
}
