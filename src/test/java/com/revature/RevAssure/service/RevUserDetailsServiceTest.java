package com.revature.RevAssure.service;

import com.revature.RevAssure.model.Curriculum;
import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.model.Topic;
import com.revature.RevAssure.repository.RevUserRepository;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class RevUserDetailsServiceTest {

    static RevUser revTest = new RevUser();
    static List<Topic> topics;
    static List<Curriculum> curricula;

    @BeforeEach
    public void init(){
        revTest.setId(1);
        revTest.setUsername("bambam1");
        revTest.setPassword("clubber");
        revTest.setFirstName("bam");
        revTest.setLastName("flintstone");
        revTest.setTrainer(true);
        revTest.setTopics(topics);
        revTest.setCurricula(curricula);
    }

    @Autowired
    private RevUserDetailsService service;

    @MockBean
    private RevUserRepository repository;

    @Test
    void loadUserByUsername() {
        when(repository.findByUsername("bambam1")).thenReturn(Optional.of(revTest));
        assertEquals(revTest.getUsername(), service.loadUserByUsername("bambam1").getUsername());
    }
}