package com.revature.RevAssure.controller;

import com.revature.RevAssure.dto.AuthenticationRequest;
import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.service.RevUserService;
import com.revature.RevAssure.util.JwtUtil;
import org.postgresql.core.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/revuser")
public class RevUserController {
    private static final Logger log = LoggerFactory.getLogger(ConnectionFactory.class);

    @Autowired
    private final RevUserService revUserService;

    public RevUserController(RevUserService revUserService) {
        this.revUserService = revUserService;
    }

    // Create
    /**
     * Create a RevUser with attributes matching the revUser input
     * @param revUser input
     * @return RevUser
     */
    @PostMapping("/register")
    public RevUser createUser(@RequestBody RevUser revUser) {
        log.info("Creating a new user");
        return revUserService.saveNewRevUser(revUser);
    }

    // Read
    /**
     * @param authReq
     * @return ResponseEntity<ok> or ResponseEntity<forbidden>
     * @throws Exception
     */
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authReq) {
        log.info("Attempting to authenticate user");
        return revUserService.authenticate(authReq);
    }

    /**
     * Retrieve RevUser object based on username that is passed in with JWT
     * @return RevUser
     */
    @GetMapping
    public RevUser getUser() {
        log.info("Getting current user's information");
        return JwtUtil.extractUser(revUserService);
    }

    // Update -- not in MVP

    // Delete -- not in MVP

}
