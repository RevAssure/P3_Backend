package com.revature.RevAssure.controller;

import com.revature.RevAssure.model.Curriculum;
import com.revature.RevAssure.model.Event;
import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.service.CurriculumService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = CurriculumController.class)
class CurriculumControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CurriculumController curriculumControllerTest;

    @MockBean
    private CurriculumService mockCurriculumService;

    private Curriculum mockCurriculum;

    private List<Curriculum> mockCurriculumList;

    private RevUser mockTrainer;

    private RevUser mockAssociate;

    private Event mockEvent;

    @BeforeEach
    void setUp()
    {
        mockCurriculum = new Curriculum();
        mockCurriculum.setId(1);
        mockCurriculum.setName("test");

        mockTrainer = new RevUser();
        mockCurriculum.setTrainer(mockTrainer);

        mockEvent = new Event();
        List<Event> mockEvents = new ArrayList<>();
        mockEvents.add(mockEvent);
        mockCurriculum.setEvents(mockEvents);

        mockAssociate = new RevUser();
        List<RevUser> mockAssociates = new ArrayList<>();
        mockAssociates.add(mockAssociate);
        mockCurriculum.setRevUsers(mockAssociates);

        mockCurriculumList = new ArrayList<>();
        mockCurriculumList.add(mockCurriculum);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createCurriculum() {
    }

    @Test
    void getAllCurriculaByCurrentUserId() throws Exception
    {
        when(mockCurriculumService.getAllCurriculaByTrainer(mockTrainer)).thenReturn(mockCurriculumList);

        mockMvc.perform(get("/curriculum")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[0]").value(mockCurriculum))
                .andReturn();
    }

    @Test
    void getAssignedCurriculaByCurrentUserId() {
    }

    @Test
    void updateCurriculum() {
    }
}