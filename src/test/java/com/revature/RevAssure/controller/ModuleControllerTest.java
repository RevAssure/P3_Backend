package com.revature.RevAssure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.RevAssure.dto.ModuleDTO;
import com.revature.RevAssure.model.*;
import com.revature.RevAssure.service.ModuleService;
import com.revature.RevAssure.service.RevUserDetailsService;
import com.revature.RevAssure.service.RevUserService;
import com.revature.RevAssure.util.JwtUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ModuleController.class)
class ModuleControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ModuleController moduleControllerTest;

    @MockBean
    private ModuleService mockModuleService;

    @MockBean
    private RevUserDetailsService mockRevUserDetailsService;

    @MockBean
    private JwtUtil mockJwtUtil;

    @MockBean
    private RevUserService mockRevUserService;

    private Module mockModule;

    private ModuleDTO mockModuleDTO;

    private List<Module> mockModuleList;

    private RevUser mockTrainer;

    private RevUser mockAssociate;

    private Topic mockTopic;

    private TechnologyCategory mockTechnologyCategory;

    @BeforeEach
    void setUp()
    {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        mockModule = new Module();
        mockModule.setId(1);
        mockModuleDTO = new ModuleDTO();
        mockModuleDTO.setId(1);
        mockModule.setName("test");
        mockModuleDTO.setName("test");
        mockModule.setDescription("test");
        mockModule.setDescription("test");

        mockTrainer = new RevUser();
        mockTrainer.setTrainer(true);
        mockModule.setTrainer(mockTrainer);

        mockTopic = new Topic();
        List<Topic> mockTopics = new ArrayList<>();
        mockTopics.add(mockTopic);
        mockModule.setTopics(mockTopics);
        List<Integer> mockTopicsIds = new ArrayList<>();
        mockTopicsIds.add(1);
        mockModuleDTO.setTopics(mockTopicsIds);

        mockTechnologyCategory = new TechnologyCategory();
        mockModule.setTechnologyCategory(mockTechnologyCategory);
        mockModuleDTO.setTechnologyCategory(1);

        mockModuleList = new ArrayList<>();
        mockModuleList.add(mockModule);

        mockAssociate = new RevUser();
    }

    @AfterEach
    void tearDown() {
    }

    @WithMockUser
    @Test
    void createModuleReturnsModuleJSON() throws Exception
    {
        mockModule.setId(0);
        when(mockJwtUtil.extractUser(mockRevUserService)).thenReturn(mockTrainer);
        when(mockModuleService.saveNewModule(mockModule)).thenReturn(mockModule);

        mockMvc.perform(post("/module")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mockModuleDTO)))
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").isNotEmpty())
//                .andExpect(module("$", mockModule))
                .andReturn();
    }

    @WithMockUser
    @Test
    void createModuleReturns403WhenUserIsNotTrainer() throws Exception
    {
        when(mockJwtUtil.extractUser(mockRevUserService)).thenReturn(mockAssociate);

        mockMvc.perform(post("/module")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mockModuleDTO)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    void createModuleReturns403WhenNoUser() throws Exception
    {
        mockMvc.perform(post("/module")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mockModuleDTO)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @WithMockUser
    @Test
    void getAllModulesReturnsModuleListJSON() throws Exception
    {
        when(mockModuleService.findAllModules()).thenReturn(mockModuleList);

        mockMvc.perform(get("/module")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(module("$[0]", mockModule))
                .andReturn();
    }

    @Test
    void getAllModuleReturns403WhenNoUser() throws Exception
    {
        mockMvc.perform(get("/module")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @WithMockUser
    @Test
    void updateModulesReturnModuleJSON() throws Exception
    {
        when(mockJwtUtil.extractUser(mockRevUserService)).thenReturn(mockTrainer);
        when(mockModuleService.saveExistingModule(mockModule)).thenReturn(mockModule);

        mockMvc.perform(put("/module")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mockModuleDTO)))
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").isNotEmpty())
//                .andExpect(module("$", mockModule))
                .andReturn();
    }

    @WithMockUser
    @Test
    void updateModulesReturns403WhenUserIsNotTrainer() throws Exception
    {
        when(mockJwtUtil.extractUser(mockRevUserService)).thenReturn(mockAssociate);

        mockMvc.perform(put("/module")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mockModuleDTO)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    void updateModulesReturns403WhenNoUser() throws Exception
    {
        mockMvc.perform(put("/module")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mockModuleDTO)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @WithMockUser
    @Test
    void deleteModulesReturnsOK() throws Exception
    {
        when(mockJwtUtil.extractUser(mockRevUserService)).thenReturn(mockTrainer);

        mockMvc.perform(delete("/module/1"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @WithMockUser
    @Test
    void deleteModulesReturns403WhenUserIsNotTrainer() throws Exception
    {
        when(mockJwtUtil.extractUser(mockRevUserService)).thenReturn(mockAssociate);

        mockMvc.perform(delete("/module/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mockModuleDTO)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    void deleteModulesReturns403WhenNoUser() throws Exception
    {
        mockMvc.perform(delete("/module/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    public static ResultMatcher module(String prefix, Module module)
    {
        return ResultMatcher.matchAll(
                jsonPath(prefix + ".id").value(module.getId()),
                jsonPath(prefix + ".name").value(module.getName()),
                jsonPath(prefix + ".description").value(module.getDescription())
        );
    }
}