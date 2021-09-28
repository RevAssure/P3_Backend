package com.revature.RevAssure.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.revature.RevAssure.dto.EventDTO;
import com.revature.RevAssure.model.Curriculum;
import com.revature.RevAssure.model.Event;
import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.service.EventService;
import com.revature.RevAssure.service.RevUserDetailsService;
import com.revature.RevAssure.service.RevUserService;
import com.revature.RevAssure.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = EventController.class)
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private EventController eventControllerTest;

    @MockBean
    private EventService eventServiceMock;

    @MockBean
    private RevUserDetailsService revUserDetailsServiceMock;

    @MockBean
    private JwtUtil jwtUtilMock;

    @MockBean
    private RevUserService revUserServiceMock;

    private Event event;
    private EventDTO eventDTO;
    private List<Event> events;
    private RevUser trainer;
    private RevUser associate;
    private Curriculum curriculum;
    private ObjectMapper mapper;
    private ObjectWriter writer;
    private String output;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        curriculum = new Curriculum();
        curriculum.setId(1);
        curriculum.setName("test");

        event = new Event();
        event.setId(1);
        event.setStartDatetime(1);
        event.setCurriculum(curriculum);

        eventDTO = new EventDTO();
        eventDTO.setId(1);
        eventDTO.setStartDatetime(1);
        eventDTO.setCurriculum(1);

        trainer = new RevUser();
        trainer.setTrainer(true);

        associate = new RevUser();
        associate.setTrainer(false);

        events = new ArrayList<>();
        events.add(event);

        mapper = Mockito.mock(ObjectMapper.class);
        writer = Mockito.mock(ObjectWriter.class);

        output = "test";
    }

    @WithMockUser
    @Test
    void createEventSuccessfully() throws Exception {
        event.setId(0);
        when(jwtUtilMock.extractUser(revUserServiceMock)).thenReturn(trainer);
        when(eventServiceMock.createEvent(event)).thenReturn(event);

        mockMvc.perform(post("/event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(eventDTO)))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$").isNotEmpty())
                .andReturn();
    }

    @WithMockUser
    @Test
    void createEventNotTrainer403() throws Exception {
        when(jwtUtilMock.extractUser(revUserServiceMock)).thenReturn(associate);

        mockMvc.perform(post("/event")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(eventDTO)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    @Test
    void createEventNotLoggedIn403() throws Exception {
        mockMvc.perform(post("/event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(eventDTO)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();

    }

    @WithMockUser
    @Test
    void getAllEventsByCurriculumIdSuccessfully() throws Exception {
        when(eventServiceMock.getAllEventsByCurriculumId(1)).thenReturn(events);

        mockMvc.perform(get("/event/1")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(event("$[0]",event))
                .andReturn();
    }

    @Test
    void getAllEventsByCurriculumIdNotLoggedIn403() throws Exception {
        mockMvc.perform(get("/event/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @WithMockUser
    @Test
    void updateEventSucccessfully() throws Exception{
        when(jwtUtilMock.extractUser(revUserServiceMock)).thenReturn(trainer);
        when(eventServiceMock.updateEvent(event)).thenReturn(event);

        mockMvc.perform(put("/event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(eventDTO)))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$").isNotEmpty())
                .andReturn();
    }

    @WithMockUser
    @Test
    void updateEventNotTrainer403() throws Exception{
        when(jwtUtilMock.extractUser(revUserServiceMock)).thenReturn(associate);

        mockMvc.perform(put("/event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(eventDTO)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    @Test
    void updateEventNotLoggedIn403() throws Exception{
        mockMvc.perform(put("/event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(eventDTO)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    @WithMockUser
    @Test
    void deleteEventSuccessfully() throws Exception{
        when(jwtUtilMock.extractUser(revUserServiceMock)).thenReturn(trainer);
        doNothing().when(eventServiceMock).deleteEvent(1);

        mockMvc.perform(delete("/event/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @WithMockUser
    @Test
    void deleteEventNotTrainer403() throws Exception{
        when(jwtUtilMock.extractUser(revUserServiceMock)).thenReturn(associate);

        mockMvc.perform(delete("/event/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    void deleteEventNotLoggedIn403() throws Exception{
        mockMvc.perform(delete("/event/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andReturn();
    }


    public static ResultMatcher event(String prefix, Event event)
    {
        return ResultMatcher.matchAll(
                jsonPath(prefix + ".id").value(event.getId()),
                jsonPath(prefix + ".startDatetime").value(event.getStartDatetime())
        );
    }
}