package com.revature.RevAssure.service;

import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.repository.RevUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Service layer for TraderDetails
 */
@Service
public class RevUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    /**
     * Repository layer for Trader object
     */
    @Autowired
    private final RevUserRepository revUserRepository;

    /**
     * Constructor for TraderDetailsService
     * @param revUserRepository TraderRepository object
     */
    @Autowired
    public RevUserDetailsService(RevUserRepository revUserRepository) {
        this.revUserRepository = revUserRepository;
    }

    /**
     * Overridden UserDetails for Spring Security
     * @param s username of Trader
     * @return UserDetails object for Trader
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        RevUser revUser = revUserRepository.findByUsername(s).orElseThrow(RuntimeException::new);
        String username = revUser.getUsername();
        String password = revUser.getPassword();
        return new User(username, password, new ArrayList<>()); // ArrayList because we aren't dealing with Authorities
    }

}