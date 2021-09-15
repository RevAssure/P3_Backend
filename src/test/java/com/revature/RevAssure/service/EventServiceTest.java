package com.revature.RevAssure.service;

import com.revature.RevAssure.model.Curriculum;
import com.revature.RevAssure.model.Event;
import com.revature.RevAssure.model.Topic;
import com.revature.RevAssure.repository.CurriculumRepository;
import com.revature.RevAssure.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

class EventServiceTest {
    private CurriculumRepository curriculumRepository;
    private EventRepository eventRepository;
    private EventService eventService;

    private Event event;
    private Curriculum curriculum;
    private Topic topic;

    private List<Event> events;

    @BeforeEach
    void setUp() {
        eventRepository = Mockito.mock(EventRepository.class);
        curriculumRepository = Mockito.mock(CurriculumRepository.class);
        eventService = new EventService(eventRepository, curriculumRepository);

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
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        Event ev = eventService.createEvent(event);

        assertEquals(event,ev);
    }

    @Test
    void getAllEventsByCurriculumSuccessfullyTest() {
        when(eventRepository.findByCurriculum(any(Curriculum.class))).thenReturn(Optional.of(events));
        when(curriculumRepository.getById(anyInt())).thenReturn(curriculum);

        List<Event> evs = eventService.getAllEventsByCurriculumId(1);

        assertEquals(events,evs);
    }

    @Test
    void getAllEventsByCurriculumButNoEventsTest() {
        when(eventRepository.findByCurriculum(any(Curriculum.class))).thenReturn(Optional.empty());
        when(curriculumRepository.getById(anyInt())).thenReturn(curriculum);

        List<Event> evs = eventService.getAllEventsByCurriculumId(1);

        assertEquals(new ArrayList<>(),evs);
    }

    @Test
    void updateEventSuccessfullyTest() {
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        Event ev = eventService.updateEvent(event);

        assertEquals(event,ev);
    }

    @Test
    void deleteEventSuccessfullyTest() {
        when(eventRepository.getById(anyInt())).thenReturn(event);

        eventService.deleteEvent(1);
    }
}