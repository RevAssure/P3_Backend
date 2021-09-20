package com.revature.RevAssure.controller;

import com.revature.RevAssure.dto.EventDTO;
import com.revature.RevAssure.model.Event;
import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.service.EventService;
import com.revature.RevAssure.service.RevUserService;
import com.revature.RevAssure.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
     * @return : The event that was inserted and persisted into the database or null if user is not a trainer
     */
    @PostMapping
    public Event createEvent(@RequestBody EventDTO eventdto){
        RevUser revUser = JwtUtil.extractUser(revUserService);
        if(revUser.isTrainer()){
            return eventService.createEvent(eventdto.convertToEntity());
        }
        else{
            return null;
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
     * @return : The event that is to be inserted and persisted into the database
     */
    @PutMapping
    public Event updateEvent(@RequestBody EventDTO eventdto){;
        RevUser revUser = JwtUtil.extractUser(revUserService);
        if(revUser.isTrainer()){
            return eventService.updateEvent(eventdto.convertToEntity());
        }
        else{
            return null;
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
