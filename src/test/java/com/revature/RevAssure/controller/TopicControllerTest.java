package com.revature.RevAssure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.RevAssure.dto.TopicDTO;
import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.model.TechnologyCategory;
import com.revature.RevAssure.model.Topic;
import com.revature.RevAssure.service.RevUserDetailsService;
import com.revature.RevAssure.service.RevUserService;
import com.revature.RevAssure.service.TopicService;
import com.revature.RevAssure.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TopicController.class)
class TopicControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private TopicController topicControllerTest;

    @MockBean
    private TopicService topicService;

    @MockBean
    private JwtUtil mockJwtUtil;

    @MockBean
    private RevUserDetailsService mockRevUserDetailsService;

    @MockBean
    private RevUserService mockRevUserService;

    private Topic topic;

    private TopicDTO topicDTO;

    private List<Topic> topicList;

    private RevUser mockUser;

    private RevUser mockTrainer;

    private TechnologyCategory mockTechnologyCategory;


    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        mockUser = new RevUser();
        mockTrainer = new RevUser();
        mockTrainer.setTrainer(true);
        mockTechnologyCategory = new TechnologyCategory();
        mockTechnologyCategory.setId(1);

        topic = new Topic();
        topic.setId(1);
        topic.setGithubRepo("git repo");
        topic.setLectureNotes("notes");
        topic.setEstimatedDuration(1);
        topic.setTitle("title");
        topic.setDescription("description");
        topic.setTechnologyCategory(mockTechnologyCategory);

        topicDTO = new TopicDTO();
        topicDTO.setId(1);
        topicDTO.setGithubRepo("git repo");
        topicDTO.setLectureNotes("notes");
        topicDTO.setEstimatedDuration(1);
        topicDTO.setTitle("title");
        topicDTO.setDescription("description");
        topicDTO.setTechnologyCategory(1);

        topicList = new ArrayList<>();
        topicList.add(topic);
    }

    /**
     * Adds topic to database.
     * HTTP Status ok
     */
    @WithMockUser
    @Test
    public void  createTopicTest() throws Exception{
        topicDTO.setId(0);
        when(mockJwtUtil.extractUser(mockRevUserService)).thenReturn(mockTrainer);
        when(topicService.createTopic(topic)).thenReturn(topic);

        mockMvc.perform(post("/topic")
        .content(new ObjectMapper().writeValueAsString(topicDTO))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        //.andExpect(jsonPath("$").exists())
        //.andExpect(topic("$",topic))
        .andReturn();
    }

    @WithMockUser
    @Test
    void createTopicReturns461WhenEntityExistsException() throws Exception
    {
        when(mockJwtUtil.extractUser(mockRevUserService)).thenReturn(mockTrainer);
        when(topicService.createTopic(any())).thenThrow(new EntityExistsException());

        mockMvc.perform(post("/topic")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(topicDTO)))
                .andExpect(status().is(461))
                .andExpect(jsonPath("$").doesNotExist());
    }

    /**
     * Cannot create topic. User is not logged in.
     * HTTP Status forbidden
     */
    @Test
    public void  createTopicTestButUserIsNotLogged403ForbiddenTest() throws Exception{
        mockMvc.perform(post("/topic")
                .content(new ObjectMapper().writeValueAsString(topicDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    /**
     * Cannot Create topic because non-trainers is trying to create topic
     * HTTP Status Forbidden
     */
    @WithMockUser
    @Test
    public void cannotCreateTopicBecauseATrainerIsNeededToCreateATopicTest() throws Exception{
        when(mockJwtUtil.extractUser(mockRevUserService)).thenReturn(mockUser);

        mockMvc.perform(post("/topic")
                .content(new ObjectMapper().writeValueAsString(topicDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }




    /**
     * Returns all trainer by trainer id.
     * HTTP Status ok
     */
    @WithMockUser
    @Test
    public void getAListOfTopicsByTrainerIdTest() throws Exception {
        when(mockJwtUtil.extractUser(mockRevUserService)).thenReturn(mockTrainer);
        when(topicService.getByTrainer(mockTrainer)).thenReturn(topicList);

        mockMvc.perform(get("/topic")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$").isNotEmpty())
                //.andExpect(topic("$[0]",topic))
                .andReturn();
    }

    /**
     * Cannot get topic by trainerId test
     * HTTP Status forbidden
     */
    @Test
    public void getAListOfTopicsByTrainerIdButNotLoggedIn403ForbiddenTest() throws Exception {
        mockMvc.perform(get("/topic")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    /**
     * Cannot return list because RevUser is not trainer
     * HTTP Status forbidden
     */
    @WithMockUser
    @Test
    public void getAListOfTopicsByTrainerIdButARevAssociateRequestsItTest() throws Exception {
        when(mockJwtUtil.extractUser(mockRevUserService)).thenReturn(mockUser);
        when(topicService.getByTrainer(mockTrainer)).thenReturn(topicList);

        mockMvc.perform(get("/topic")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    /**
     * Get all topics
     * Http Status ok
     */
    @WithMockUser
    @Test
    public void getAllTopicTest() throws Exception {
        when(topicService.getAll()).thenReturn(topicList);
        mockMvc.perform(get("/topic/all")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(topic("$[0]",topic))
                .andReturn();
    }

    /**
     * Get topic by Topic Id
     * Http Status ok
     */
    @WithMockUser
    @Test
    public void getTopicByTopicIdTest() throws Exception {
        when(topicService.getById(any(Integer.class))).thenReturn(topic);
        mockMvc.perform(get("/topic/1")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(topic("$",topic))
                .andReturn();
    }

    /**
     * Returns a list of topics by module Id
     * Http Status OK
     */
    @WithMockUser
    @Test
    public void getTopicByModuleIdTest() throws Exception{
        when(topicService.getAllTopicsByModuleId(1)).thenReturn(topicList);
        mockMvc.perform(get("/topic/module/1")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(topic("$[0]",topic))
                .andReturn();
    }

    /**
     * Update a topic
     * Http Status Ok
     */
    @WithMockUser
    @Test
    public void updateTopicTest() throws Exception{
        when(mockJwtUtil.extractUser(mockRevUserService)).thenReturn(mockTrainer);
        when(topicService.updateTopic(topic)).thenReturn(topic);

        mockMvc.perform(put("/topic")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(topicDTO)))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$").isNotEmpty())
                //.andExpect(topic("$",topic))
                .andReturn();

    }

    @WithMockUser
    @Test
    void updateTopicReturns462WhenEntityExistsException() throws Exception
    {
        when(mockJwtUtil.extractUser(mockRevUserService)).thenReturn(mockTrainer);
        when(topicService.updateTopic(any())).thenThrow(new EntityNotFoundException());

        mockMvc.perform(put("/topic")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(topicDTO)))
                .andExpect(status().is(462))
                .andExpect(jsonPath("$").doesNotExist());
    }

    /**
     * Cannot update topic. User is not logged in
     * Http Status forbidden
     */
    @Test
    public void updateTopicButNotLoggedIn403ForbiddenTest() throws Exception{
        mockMvc.perform(put("/topic")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(topicDTO)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    /**
     * Cannot Update Topic. User is not a trainer
     * Http Status Forbidden
     */
    @WithMockUser
    @Test
    public void updateTopicTestButUserIsNotATrainer() throws Exception{
        when(mockJwtUtil.extractUser(mockRevUserService)).thenReturn(mockUser);

        mockMvc.perform(put("/topic")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(topicDTO)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();

    }



    /**
     * Delete a topic by topicId
     * Http status ok
     */
    @WithMockUser
    @Test
    public void deleteTopicByIdFromPathVariableTest() throws Exception {
        when(mockJwtUtil.extractUser(mockRevUserService)).thenReturn(mockTrainer);
        doNothing().when(topicService).deleteTopic(any(Integer.class));
        mockMvc.perform(delete("/topic/1")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

    }

    /**
     * Cannot delete topic by topicId. User is not logged in/
     * Http status forbidden
     */
    @Test
    public void deleteTopicByIdButUserIsNotLoggedIn403ForbiddenTest() throws Exception {
        mockMvc.perform(delete("/topic/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andReturn();
    }


    /**
     * Cannot delete topic by topicId. RevUser is not a Trainer
     * Http status forbidden
     */
    @WithMockUser
    @Test
    public void deleteTopicByIdButRevUserIsNotATrainerTest() throws Exception {
        when(mockJwtUtil.extractUser(mockRevUserService)).thenReturn(mockUser);
        mockMvc.perform(delete("/topic/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();

    }

    /**
     * test deleteTopic throws DataIntegrityViolation
     * Should throw when user tries to delete a topic that is being referenced by an event
     */
    @WithMockUser
    @Test
    public void deleteTopicByIdReturns470WhenDataIntegrityViolation() throws Exception
    {
        when(mockJwtUtil.extractUser(mockRevUserService)).thenReturn(mockTrainer);
        doThrow(new DataIntegrityViolationException("")).when(topicService).deleteTopic(1);

        mockMvc.perform(delete("/topic/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(470))
                .andExpect(jsonPath("$").value("Cannot delete topic - There are events that reference this topic"))
                .andReturn();

    }

    /**
     * matches the JSON body to the expected object
     * @param prefix the index of the object in JSON
     *               just "$" if not a list, "$[{index number}]" if is a list
     * @param topic the expected result entity
     * @return ResultMatcher that matches every line in the JSON object to their respective attribute in the entity
     */
    public static ResultMatcher topic(String prefix, Topic topic)
    {
        return ResultMatcher.matchAll(
                jsonPath(prefix + ".id").value(topic.getId()),
                jsonPath(prefix + ".title").value(topic.getTitle()),
                jsonPath(prefix + ".description").value(topic.getDescription()),
                jsonPath(prefix + ".estimatedDuration").value(topic.getEstimatedDuration()),
                jsonPath(prefix + ".lectureNotes").value(topic.getLectureNotes()),
                jsonPath(prefix + ".githubRepo").value(topic.getGithubRepo())
        );
    }


//    /**
//     * TODO: Find a way to break ObjectMapper to cause InternalServerError For posting Topic....
//     */
//    @WithMockUser
//    @Test
//    public void cannotCreateTopicBecauseObjectMapperCannotParseString() throws Exception{
//        when(mockJwtUtil.extractUser(mockRevUserService)).thenReturn(mockTrainer);
//        when(topicService.saveTopic(any(Topic.class))).thenReturn(topic);
//
//        mockMvc.perform(post("/topic")
//                .content(new ObjectMapper().writeValueAsString(topicDTO))
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isForbidden())
//                .andReturn();
//    }

    // TODO: Find a way to break ObjectMapper to cause InternalServerError for put method....
//    @WithMockUser
//    @Test
//    public void UpdateTopicTestButObjectMapperThrowsJsonParseException() throws Exception{
//        when(mockJwtUtil.extractUser(mockRevUserService)).thenReturn(mockTrainer);
//        when(topicService.saveTopic(any(Topic.class))).thenReturn(topic);
//
//        mockMvc.perform(put("/topic")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(topicDTO)))
//                .andExpect(status().isInternalServerError())
//                .andReturn();
//
//    }

}
