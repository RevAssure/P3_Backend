package com.revature.RevAssure.service;

import com.revature.RevAssure.model.Event;
import com.revature.RevAssure.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    private static final Logger log = LoggerFactory.getLogger(EventService.class);
    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository){
        this.eventRepository = eventRepository;
    }

    /**
     * Saves a new event into the database given an Event object
     * @param event : The event object to be persisted into the database.
     * @return The event object that was persisted into the database.
     */
    public Event createEvent(Event event) {
        log.info("creating event");
        return eventRepository.save(event);
    }

    /**
     * Gets a list of events from the database that all have the same curriculum
     * @param id : The curriculum id
     * @return Either a list of all the events for that curriculum, or an empty ArrayList if there were no events for that curriculum.
     */
    public List<Event> getAllEventsByCurriculumId(int id) {
        log.info("Get all events in specific curriculum");
        return eventRepository.findByCurriculumId(id);
    }

    /**
     * Updates an event into the database.
     * @param event : The event object to be persisted into the database
     * @return The event object that was persisted into the database
     */
    public Event updateEvent(Event event) {
        log.info("update event by event");
        return eventRepository.save(event);
    }

    /**
     * Deletes an event from the database given the event id
     * @param id : The event id of the event that is to be deleted
     */
    public void deleteEvent(int id) {
        log.info("delete event by id");
        eventRepository.delete(eventRepository.getById(id));
    }
}
