package com.revature.RevAssure.controller;

import com.revature.RevAssure.dto.AuthenticationRequest;
import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.service.RevUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Controller
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
    public @ResponseBody
    RevUser createUser(@RequestBody RevUser revUser) {
        return revUserService.saveNewRevUser(revUser);
    }

    // Read
    /**
     * TODO: isTrainer needs to be added to response body
     * @param authReq
     * @return
     * @throws Exception
     */
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authReq) throws Exception {
        return revUserService.authenticate(authReq);
    }

    // Update -- not in MVP

    // Delete -- not in MVP




}
