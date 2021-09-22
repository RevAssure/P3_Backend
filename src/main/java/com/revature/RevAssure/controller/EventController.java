package com.revature.RevAssure.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.RevAssure.dto.EventDTO;
import com.revature.RevAssure.model.Event;
import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.service.EventService;
import com.revature.RevAssure.service.RevUserService;
import com.revature.RevAssure.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private final EventService eventService;

    @Autowired
    private final RevUserService revUserService;

    public EventController(EventService eventService, RevUserService revUserService){
        this.eventService = eventService;
        this.revUserService = revUserService;
    }

    // Create

    // TODO: Consider DTOs for parameter value

    /**
     * Endpoint available for creating an event
     * @param eventdto : The event that is to be inserted and persisted into the database
     * @return : The event that was inserted and persisted into the database or sets bad status if user is not a trainer
     */
    @PostMapping
    public ResponseEntity<String> createEvent(@RequestBody EventDTO eventdto) throws JsonProcessingException {
        RevUser revUser = JwtUtil.extractUser(revUserService);
        if(revUser.isTrainer()){
            Event event = eventService.createEvent(eventdto.convertToEntity());
            String str = new ObjectMapper()
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(event);
            return ResponseEntity.ok().body(str);
        }
        else{
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
        return eventService.getAllEventsByCurriculumId(curriculum_id);
    }

    // TODO: get all events tied to user

    // Update

    /**
     * Endpoint available for updating an existing event
     * @param eventdto : The event that is to be updated and persisted into the database
     * @return : The event that is to be inserted and persisted into the database or sets bad status if user is not a trainer
     */
    @PutMapping
    public ResponseEntity<String> updateEvent(@RequestBody EventDTO eventdto) throws JsonProcessingException {;
        RevUser revUser = JwtUtil.extractUser(revUserService);
        if(revUser.isTrainer()){
            Event event = eventService.updateEvent(eventdto.convertToEntity());
            String str = new ObjectMapper()
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(event);
            return ResponseEntity.ok().body(str);
        }
        else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    // Delete

    /**
     * Endpoint available for deleting an existing event
     * @param eventId : Event id of event that is to be deleted
     */
    @DeleteMapping("/{eventId}")
    public void deleteEvent(@PathVariable int eventId){
        RevUser revUser = JwtUtil.extractUser(revUserService);
        if(revUser.isTrainer()){eventService.deleteEvent(eventId);}
    }
}
