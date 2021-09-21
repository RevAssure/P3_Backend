package com.revature.RevAssure.service;

import com.revature.RevAssure.model.Curriculum;
import com.revature.RevAssure.model.Event;
import com.revature.RevAssure.model.Topic;
import com.revature.RevAssure.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class EventServiceTest {
    @MockBean
    private EventRepository eventRepository;
    @Autowired
    private EventService eventService;

    private Event event;
    private Curriculum curriculum;
    private Topic topic;

    private List<Event> events;

    @BeforeEach
    void setUp() {
        curriculum = new Curriculum();
        curriculum.setId(1);
        topic = new Topic();
        topic.setId(1);

        event = new Event();
        event.setId(1);
        event.setCurriculum(curriculum);
        event.setTopic(topic);
        event.setStartDatetime(60);

        events = new ArrayList<>();
        events.add(event);
    }

    @Test
    void createNewEventSuccessfullyTest() {
        when(eventRepository.save(event)).thenReturn(event);

        Event ev = eventService.createEvent(event);

        assertEquals(event,ev);
    }

    @Test
    void getAllEventsByCurriculumSuccessfullyTest() {
        when(eventRepository.findByCurriculumId(1)).thenReturn(events);

        List<Event> evs = eventService.getAllEventsByCurriculumId(1);

        assertEquals(events,evs);
    }

    @Test
    void getAllEventsByCurriculumButNoEventsTest() {
        when(eventRepository.findByCurriculumId(1)).thenReturn(new ArrayList<>());

        List<Event> evs = eventService.getAllEventsByCurriculumId(1);

        assertEquals(new ArrayList<>(),evs);
    }

    @Test
    void updateEventSuccessfullyTest() {
        when(eventRepository.save(event)).thenReturn(event);

        Event ev = eventService.updateEvent(event);

        assertEquals(event,ev);
    }

    @Test
    void deleteEventSuccessfullyTest() {
        when(eventRepository.getById(1)).thenReturn(event);

        eventService.deleteEvent(1);

        verify(eventRepository, times(1)).delete(event);
    }
}