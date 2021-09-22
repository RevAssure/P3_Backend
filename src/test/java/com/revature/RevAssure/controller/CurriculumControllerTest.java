package com.revature.RevAssure.controller;

import com.revature.RevAssure.service.CurriculumService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

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
    private CurriculumService curriculumService;

    @BeforeEach
    void setUp() {
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
        mockMvc.perform(get("/curriculum")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andReturn();
    }

    @Test
    void getAssignedCurriculaByCurrentUserId() {
    }

    @Test
    void updateCurriculum() {
    }
}