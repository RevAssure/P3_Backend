package com.revature.RevAssure.service;

import com.revature.RevAssure.repository.CurriculumRepository;
import com.revature.RevAssure.model.Event;
import com.revature.RevAssure.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {

    private EventRepository eventRepository;
    private CurriculumRepository curriculumRepository;

    @Autowired
    public EventService(EventRepository eventRepository, CurriculumRepository curriculumRepository){
        this.eventRepository = eventRepository;
        this.curriculumRepository = curriculumRepository;
    }

    /**
     * Saves a new event into the database given an Event object
     * @param event : The event object to be persisted into the database.
     * @return The event object that was persisted into the database.
     */
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    /**
     * Gets a list of events from the database that all have the same curriculum
     * @param id : The curriculum id
     * @return Either a list of all the events for that curriculum, or an empty ArrayList if there were no events for that curriculum.
     */
    public List<Event> getAllEventsByCurriculumId(int id) {
        try {
            return eventRepository.findByCurriculum(curriculumRepository.getById(id))
                    .orElseThrow(RuntimeException::new);
        } catch(RuntimeException e){
            return new ArrayList<Event>();
        }

    }

    /**
     * Updates an event into the database.
     * @param event : The event object to be persisted into the database
     * @return The event object that was persisted into the database
     */
    public Event updateEvent(Event event) {
        return eventRepository.save(event);
    }

    /**
     * Deletes an event from the database given the event id
     * @param id : The event id of the event that is to be deleted
     */
    public void deleteEvent(int id) {
        eventRepository.delete(eventRepository.getById(id));
    }
}
