package com.revature.RevAssure.controller;

import com.revature.RevAssure.model.Curriculum;
import com.revature.RevAssure.model.Event;
import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.service.CurriculumService;
import com.revature.RevAssure.service.RevUserDetailsService;
import com.revature.RevAssure.service.RevUserService;
import com.revature.RevAssure.util.JwtUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
//@AutoConfigureMockMvc
@WebMvcTest(controllers = CurriculumController.class)
class CurriculumControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private CurriculumController curriculumControllerTest;

    @MockBean
    private CurriculumService mockCurriculumService;

    @MockBean
    private RevUserDetailsService mockRevUserDetailsService;

    @MockBean
    private JwtUtil mockJwtUtil;

    @MockBean
    private RevUserService mockRevUserService;

    private Curriculum mockCurriculum;

    private List<Curriculum> mockCurriculumList;

    private RevUser mockTrainer;

    private RevUser mockAssociate;

    private Event mockEvent;

    @BeforeEach
    void setUp()
    {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

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

    @WithMockUser
    @Test
    void createCurriculum() {
    }

    @WithMockUser
    @Test
    void getAllCurriculaByCurrentUserId() throws Exception
    {
        when(mockJwtUtil.extractUser(mockRevUserService)).thenReturn(mockTrainer);
        when(mockCurriculumService.getAllCurriculaByTrainer(mockTrainer)).thenReturn(mockCurriculumList);

        mockMvc.perform(get("/curriculum")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[0].id").value(1))
                .andReturn();
    }

    @WithMockUser
    @Test
    void getAssignedCurriculaByCurrentUserId() {
    }

    @WithMockUser
    @Test
    void updateCurriculum() {
    }
}