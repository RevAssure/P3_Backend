package com.revature.RevAssure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.RevAssure.dto.CurriculumDTO;
import com.revature.RevAssure.model.Curriculum;
import com.revature.RevAssure.model.Event;
import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.service.CurriculumService;
import com.revature.RevAssure.service.RevUserDetailsService;
import com.revature.RevAssure.service.RevUserService;
import com.revature.RevAssure.util.JwtUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    private CurriculumDTO mockCurriculumDTO;

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
        mockCurriculumDTO = new CurriculumDTO();
        mockCurriculum.setId(1);
        mockCurriculumDTO.setId(1);
        mockCurriculum.setName("test");
        mockCurriculumDTO.setName("test");

        mockTrainer = new RevUser();
        mockTrainer.setTrainer(true);
        mockCurriculum.setTrainer(mockTrainer);

        mockEvent = new Event();
        List<Event> mockEvents = new ArrayList<>();
        mockEvents.add(mockEvent);
        mockCurriculum.setEvents(mockEvents);
        List<Integer> mockEventsIds = new ArrayList<>();
        mockEventsIds.add(1);
        mockCurriculumDTO.setEvents(mockEventsIds);

        mockAssociate = new RevUser();
        List<RevUser> mockAssociates = new ArrayList<>();
        mockAssociates.add(mockAssociate);
        mockCurriculum.setRevUsers(mockAssociates);
        List<Integer> mockAssociatesIds = new ArrayList<>();
        mockAssociatesIds.add(1);
        mockCurriculumDTO.setEvents(mockAssociatesIds);

        mockCurriculumList = new ArrayList<>();
        mockCurriculumList.add(mockCurriculum);
    }

    @AfterEach
    void tearDown() {
    }

    /**
     * checks post request works correctly
     * @throws Exception
     */
    @WithMockUser
    @Test
    void createCurriculumReturnsCurriculumJSON() throws Exception
    {
        mockCurriculumDTO.setId(0);
        when(mockJwtUtil.extractUser(mockRevUserService)).thenReturn(mockTrainer);
        when(mockCurriculumService.saveCurriculum(mockCurriculum)).thenReturn(mockCurriculum);

        mockMvc.perform(post("/curriculum")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mockCurriculumDTO)))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$").isNotEmpty())
                //.andExpect(curriculum("$", mockCurriculum))
                .andReturn();
    }

    /**
     * tests 403 returned when user is not trainer
     * @throws Exception
     */
    @WithMockUser
    @Test
    void createCurriculumReturns403WhenUserIsNotTrainer() throws Exception
    {
        when(mockJwtUtil.extractUser(mockRevUserService)).thenReturn(mockAssociate);

        mockMvc.perform(post("/curriculum")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mockCurriculumDTO)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").doesNotExist());
    }

    /**
     * tests 403 returned when no user authenticated
     * @throws Exception
     */
    @Test
    public void createCurriculumReturns403WhenNoUser() throws Exception
    {
        mockMvc.perform(post("/curriculum")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mockCurriculumDTO)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").doesNotExist());
    }

    /**
     * checks get request works correctly
     * @throws Exception
     */
    @WithMockUser
    @Test
    void getAllCurriculaByTrainerIdReturnsCurriculumListJSON() throws Exception
    {
        when(mockJwtUtil.extractUser(mockRevUserService)).thenReturn(mockTrainer);
        when(mockCurriculumService.getAllCurriculaByTrainer(mockTrainer)).thenReturn(mockCurriculumList);

        mockMvc.perform(get("/curriculum")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(curriculum("$[0]", mockCurriculum))
                .andReturn();
    }

    /**
     * tests 403 returned when no user authenticated
     * @throws Exception
     */
    @Test
    public void getAllCurriculaByTrainerIdReturns403WhenNoUser() throws Exception
    {
        mockMvc.perform(get("/curriculum")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mockCurriculumDTO)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").doesNotExist());
    }

    /**
     * checks get "/assigned" request works correctly
     * @throws Exception
     */
    @WithMockUser
    @Test
    void getAssignedCurriculaByAssociateIdReturnsCurriculumListJSON() throws Exception
    {
        when(mockJwtUtil.extractUser(mockRevUserService)).thenReturn(mockAssociate);
        when(mockCurriculumService.getAllCurriculaByUser(mockAssociate)).thenReturn(mockCurriculumList);

        mockMvc.perform(get("/curriculum/assigned")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(curriculum("$[0]", mockCurriculum))
                .andReturn();
    }

    /**
     * tests 403 returned when no user authenticated
     * @throws Exception
     */
    @Test
    public void getAssignedCurriculaByAssociateIdReturns403WhenNoUser() throws Exception
    {
        mockMvc.perform(get("/curriculum/assigned")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mockCurriculumDTO)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").doesNotExist());
    }

    /**
     * checks put request works correctly
     * @throws Exception
     */
    @WithMockUser
    @Test
    void updateCurriculumReturnsCurriculumJSON() throws Exception
    {
        when(mockJwtUtil.extractUser(mockRevUserService)).thenReturn(mockTrainer);
        when(mockCurriculumService.saveCurriculum(mockCurriculum)).thenReturn(mockCurriculum);

        mockMvc.perform(put("/curriculum")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mockCurriculumDTO)))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$").isNotEmpty())
                //.andExpect(curriculum("$", mockCurriculum))
                .andReturn();
    }

    /**
     * tests 403 returned when user is not trainer
     * @throws Exception
     */
    @WithMockUser
    @Test
    void updateCurriculumReturns403WhenUserIsNotTrainer() throws Exception
    {
        when(mockJwtUtil.extractUser(mockRevUserService)).thenReturn(mockAssociate);

        mockMvc.perform(put("/curriculum")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mockCurriculumDTO)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").doesNotExist());
    }

    /**
     * tests 403 returned when no user authenticated
     * @throws Exception
     */
    @Test
    public void updateCurriculumReturns403WhenNoUser() throws Exception
    {
        mockMvc.perform(put("/curriculum")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mockCurriculumDTO)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").doesNotExist());
    }

    /**
     * matches the JSON body to the expected object
     * @param prefix the index of the object in JSON
     *               just "$" if not a list, "$[{index number}]" if is a list
     * @param curriculum the expected result entity
     * @return ResultMatcher that matches every line in the JSON object to their respective attribute in the entity
     */
    public static ResultMatcher curriculum(String prefix, Curriculum curriculum)
    {
        return ResultMatcher.matchAll(
                jsonPath(prefix + ".id").value(curriculum.getId()),
                jsonPath(prefix + ".name").value(curriculum.getName())
        );
    }
}
