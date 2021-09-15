package com.revature.RevAssure.controller;

import com.revature.RevAssure.dto.AuthenticationRequest;
import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.service.RevUserService;
import com.revature.RevAssure.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/revuser")
public class RevUserController {

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
        return revUserService.saveNewRevUser(revUser);
    }

    // Read
    /**
     * @param authReq
     * @return ResponseEntity<ok> or ResponseEntity<forbidden>
     * @throws Exception
     */
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authReq) throws Exception {
        ResponseEntity<?> responseEntity = revUserService.authenticate(authReq);
        return revUserService.authenticate(authReq);
    }

    /**
     * Retrieve RevUser object based on username that is passed in with JWT
     * @return RevUser
     */
    @GetMapping
    public RevUser getUser() {
        return extractUser();
    }

    // Update -- not in MVP

    // Delete -- not in MVP

    private RevUser extractUser(){
        String username = JwtUtil.extractUsername();
         return revUserService.getRevUserByUsername(username);
    }


}
