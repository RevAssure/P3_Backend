package com.revature.RevAssure.service;

import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.repository.RevUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class RevUserDetailsServiceTest {

    static RevUser revTest = new RevUser(1, "bambam1", "clubber", "bam", "flintstone", false);

    @Autowired
    private RevUserDetailsService service;

    @MockBean
    private RevUserRepository repository;

    @Test
    void loadUserByUsername() {
        when(repository.findByUsername(revTest.getUsername())).thenReturn(java.util.Optional.ofNullable(revTest));
        assertEquals(revTest.getUsername(), service.loadUserByUsername("bambam1").getUsername());
    }
}