package com.revature.RevAssure.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.revature.RevAssure.dto.AuthenticationRequest;
import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.service.RevUserService;
import com.revature.RevAssure.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/revuser")
public class RevUserController {
    private static final Logger log = LoggerFactory.getLogger(RevUserController.class);

    @Autowired
    private final RevUserService revUserService;
    private final ObjectMapper objectMapper;

    public RevUserController(RevUserService revUserService, ObjectMapper objectMapper) {
        this.revUserService = revUserService;
        this.objectMapper = objectMapper;
    }

    // Create
    /**
     * Create a RevUser with attributes matching the revUser input
     * @param revUser input
     * @return RevUser
     */
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody RevUser revUser) throws JsonProcessingException {
        log.info("Create new user");
        try {
            return new ResponseEntity<RevUser>(revUserService.saveNewRevUser(revUser), HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            ObjectNode errorMessage = objectMapper.createObjectNode();
            errorMessage.put("Message", String.format("Username %s already exists", revUser.getUsername()));
            String body = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(errorMessage);
            return new ResponseEntity<String>(body, HttpStatus.CONFLICT);
        }
    }

    // Read
    /**
     * @param authReq The username and password
     * @return ResponseEntity<ok> or ResponseEntity<forbidden>
     */
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authReq) {
        log.info("Authenticate user");
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
