package com.revature.RevAssure.service;

import com.revature.RevAssure.model.Curriculum;
import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.model.Topic;
import com.revature.RevAssure.repository.RevUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RevUserDetailsServiceTest {

    static RevUser revTest = new RevUser();
    static List<Topic> topics;
    static List<Curriculum> curricula;

    private RevUserDetailsService service;

    @Mock
    private RevUserRepository repository;

    @BeforeEach
    public void setUp(){
        service = new RevUserDetailsService(repository);

        revTest.setId(1);
        revTest.setUsername("bambam1");
        revTest.setPassword("clubber");
        revTest.setFirstName("bam");
        revTest.setLastName("flintstone");
        revTest.setTrainer(true);
        revTest.setTopics(topics);
        revTest.setCurricula(curricula);
    }

    @Test
    void loadUserByUsername() {
        when(repository.findByUsername(revTest.getUsername())).thenReturn(java.util.Optional.of(revTest));
        assertEquals(revTest.getUsername(), service.loadUserByUsername("bambam1").getUsername());
    }
}