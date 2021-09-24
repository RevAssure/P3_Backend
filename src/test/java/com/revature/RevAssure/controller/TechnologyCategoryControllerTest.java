package com.revature.RevAssure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.model.TechnologyCategory;
import com.revature.RevAssure.service.RevUserDetailsService;
import com.revature.RevAssure.service.RevUserService;
import com.revature.RevAssure.service.TechnologyCategoryService;
import com.revature.RevAssure.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = TechnologyCategoryController.class)
class TechnologyCategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private TechnologyCategoryController technologyCategoryControllerTest;

    @MockBean
    private TechnologyCategoryService technologyCategoryService;

    @MockBean
    private JwtUtil mockJwtUtil;

    @MockBean
    private RevUserDetailsService mockRevUserDetailsService;

    @MockBean
    private RevUserService mockRevUserService;

    private TechnologyCategory technologyCategory;

    private List<TechnologyCategory> technologyCategoryList;

    private RevUser mockUser;

    private RevUser mockTrainer;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        technologyCategory = new TechnologyCategory();
        technologyCategory.setId(1);
        technologyCategory.setName("Java");

        technologyCategoryList = new ArrayList<>();
        technologyCategoryList.add(technologyCategory);

        mockUser = new RevUser();

        mockTrainer = new RevUser();
        mockTrainer.setTrainer(true);

    }

    /**
     * return a list of all technology category.
     * HTTP Status ok
     */
    @WithMockUser
    @Test
    public void getAListOfTechnologyCategoriesTest() throws Exception {
        when(mockJwtUtil.extractUser(mockRevUserService)).thenReturn(mockUser);
        when(technologyCategoryService.getAll()).thenReturn(technologyCategoryList);


        mockMvc.perform(get("/technology_category")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(technologyCategory("$[0]", technologyCategory))
            .andReturn();
    }


    /**
     * Cannot get technology categories because User is not logged in
     * Http Status forbidden
     */
    @Test
    public void getTechnologyBut403NoUserTest() throws Exception{
        mockMvc.perform(get("/technology_category")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").doesNotExist());
    }

    /**
     * Adds technology category to database.
     * HTTP Status ok
     */
    @WithMockUser
    @Test
    public void AddTechnologyCategoryToTheDatabaseTest() throws Exception{
        when(mockJwtUtil.extractUser(mockRevUserService)).thenReturn(mockTrainer);
        when(technologyCategoryService.create(technologyCategory)).thenReturn(technologyCategory);

        mockMvc.perform(post("/technology_category")
                .content(new ObjectMapper().writeValueAsString(technologyCategory))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //.andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                //.andExpect(technologyCategory("$", technologyCategory))
                .andReturn();

    }

    /**
     * Cannot add technology category because RevUser is a non-trainer
     * Http Status forbidden
     */
    @WithMockUser
    @Test
    public void createTechnologyCategoryButForbiddenBecauseUserIsNotTrainerTest() throws Exception{
        when(mockJwtUtil.extractUser(mockRevUserService)).thenReturn(mockUser);

        mockMvc.perform(post("/technology_category")
                .content(new ObjectMapper().writeValueAsString(technologyCategory))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    /**
     * Cannot add technology category because RevUser is a not logged in
     * Http Status forbidden
     */
    @Test
    public void createTechnologyCategoryButForbiddenBecauseUserIsNotLoggedIn403ForbiddenTest() throws Exception{
        mockMvc.perform(post("/technology_category")
                .content(new ObjectMapper().writeValueAsString(technologyCategory))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }


    /**
     * matches the JSON body to the expected object
     * @param prefix the index of the object in JSON
     *               just "$" if not a list, "$[{index number}]" if is a list
     * @param technologyCategory the expected result entity
     * @return ResultMatcher that matches every line in the JSON object to their respective attribute in the entity
     */
    public static ResultMatcher technologyCategory(String prefix, TechnologyCategory technologyCategory)
    {
        return ResultMatcher.matchAll(
                jsonPath(prefix + ".id").value(technologyCategory.getId()),
                jsonPath(prefix + ".name").value(technologyCategory.getName())
        );
    }

// TODO: Find a way to break ObjectMapper to cause InternalServerError for post method....
//    @WithMockUser
//    @Test
//    public void createTechnologyCategoryButInternalServiceErrorBecauseJSONStringIsIncorrect() throws Exception{
//        when(mockJwtUtil.extractUser(mockRevUserService)).thenReturn(mockTrainer);
//        when(technologyCategoryService.create(technologyCategory)).thenReturn(new TechnologyCategory());
//
//        mockMvc.perform(post("/technology_category"))
//                .andExpect(status().isInternalServerError())
//                .andReturn();
//
//    }

}