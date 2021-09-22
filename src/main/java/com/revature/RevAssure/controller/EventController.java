package com.revature.RevAssure.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.RevAssure.dto.EventDTO;
import com.revature.RevAssure.model.Event;
import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.service.EventService;
import com.revature.RevAssure.service.RevUserService;
import com.revature.RevAssure.util.JwtUtil;
import org.postgresql.core.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/event")
public class EventController {
    private static final Logger log = LoggerFactory.getLogger(ConnectionFactory.class);

    @Autowired
    private final EventService eventService;

    @Autowired
    private final RevUserService revUserService;

    public EventController(EventService eventService, RevUserService revUserService){
        this.eventService = eventService;
        this.revUserService = revUserService;
    }

    // Create

    /**
     * Endpoint available for creating an event
     * @param eventdto : The event that is to be inserted and persisted into the database
     * @return : The event that was inserted and persisted into the database or sets bad status if user is not a trainer
     */
    @PostMapping
    public ResponseEntity<String> createEvent(@RequestBody EventDTO eventdto) throws JsonProcessingException {
        RevUser revUser = JwtUtil.extractUser(revUserService);
        if(revUser.isTrainer()){
            log.info("Trainer is creating a new event");
            return ResponseEntity.ok().body(
                    new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(
                            eventService.createEvent(
                                    eventdto.convertToEntity())));
        }
        else{
            log.warn("Associate is attempting to create an event");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    // Read

    /**
     * Endpoint available for getting all events with the same curriculum
     * @param curriculum_id : The curriculum id that is corresponding to all desired events
     * @return : The list of events that is to be sent back to the front end
     */
    @GetMapping("/{curriculum_id}")
    public List<Event> getAllEventsByCurriculumId(@PathVariable int curriculum_id){
        log.info("All events that share the given curriculum id is being retrieved.");
        return eventService.getAllEventsByCurriculumId(curriculum_id);
    }

    // TODO: get all events tied to user

    // Update

    /**
     * Endpoint available for updating an existing event
     * @param eventdto : The event that is to be updated and persisted into the database
     * @return : The event that is to be updated and persisted into the database or sets bad status if user is not a trainer
     */
    @PutMapping
    public ResponseEntity<String> updateEvent(@RequestBody EventDTO eventdto) throws JsonProcessingException {;
        RevUser revUser = JwtUtil.extractUser(revUserService);
        if(revUser.isTrainer()){
            log.info("Trainer is updating one of their events");
            return ResponseEntity.ok().body(
                    new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(
                            eventService.updateEvent(
                                    eventdto.convertToEntity())));
        }
        else{
            log.warn("Associate is attempting to update an event");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    // Delete

    /**
     * Endpoint available for deleting an existing event
     * @param eventId : Event id of event that is to be deleted
     * @return ResponseEntity that will return status code
     */
    @DeleteMapping("/{eventId}")
    public ResponseEntity<String> deleteEvent(@PathVariable int eventId){
        RevUser revUser = JwtUtil.extractUser(revUserService);
        if(revUser.isTrainer()){
            log.info("Trainer is deleting one of their events");
            eventService.deleteEvent(eventId);
            return ResponseEntity.ok().build();
        }
        else{
            log.warn("Associate is attempting to delete an event");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
