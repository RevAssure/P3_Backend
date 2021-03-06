package com.revature.RevAssure.service;

import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.repository.RevUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Service layer for RevUserDetails
 */
@Service
public class RevUserDetailsService implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(RevUserDetailsService.class);
    /**
     * Repository layer for RevUser object
     */
    @Autowired
    private final RevUserRepository revUserRepository;

    /**
     * Constructor for RevUserDetailsService
     * @param revUserRepository RevUserRepository object
     */
    @Autowired
    public RevUserDetailsService(RevUserRepository revUserRepository) {
        this.revUserRepository = revUserRepository;
    }

    /**
     * Overridden UserDetails for Spring Security
     * @param s username of RevUser
     * @return UserDetails object for RevUser
     * @throws UsernameNotFoundException To indicate invalid log in credentials
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.info("Load user information");
        RevUser revUser = revUserRepository.findByUsername(s).orElseThrow(RuntimeException::new);
        String username = revUser.getUsername();
        String password = revUser.getPassword();
        return new User(username, password, new ArrayList<>()); // ArrayList because we aren't dealing with Authorities
    }



}