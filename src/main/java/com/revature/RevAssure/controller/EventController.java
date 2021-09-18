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

//    @Autowired
//    private final RevUserService revUserService;

    public EventController(EventService eventService, RevUserService revUserService){
        this.eventService = eventService;
//        this.revUserService = revUserService;
    }


    // Create

    // TODO: Consider DTOs for parameter value

    /**
     * Endpoint available for creating an event
     * @param eventdto : The event that is to be inserted and persisted into the database
     * @return : The event that was inserted and persisted into the database
     */
    @PostMapping
    public Event createEvent(@RequestBody EventDTO eventdto){
//        JwtUtil.extractUser(revUserService);
        return eventService.createEvent(eventdto.convertToEntity());
    }

    // Read

    /**
     * Endpoint available for getting all events with the same curriculum
     * @param curriculum_id : The curriculum id that is corresponding to all desired events
     * @return : The list of events that is to be sent back to the front end
     */
    @GetMapping("/{curriculum_id}")
    public List<Event> getAllEventsByCurriculumId(@PathVariable int curriculum_id){
//        JwtUtil.extractUser(revUserService);
        return eventService.getAllEventsByCurriculumId(curriculum_id);
    }

    // Update

    /**
     * Endpoint available for updating an existing event
     * @param eventdto : The event that is to be updated and persisted into the database
     * @return : The event that is to be inserted and persisted into the database
     */
    @PutMapping
    public Event updateEvent(@RequestBody EventDTO eventdto){
//        JwtUtil.extractUser(revUserService);
        return eventService.updateEvent(eventdto.convertToEntity());
    }

    // Delete

    /**
     * Endpoint available for deleting an existing event
     * @param eventId : Event id of event that is to be deleted
     */
    @DeleteMapping("/{eventId}")
    public void deleteEvent(@PathVariable int eventId){
//        JwtUtil.extractUser(revUserService);
        eventService.deleteEvent(eventId);
    }
}
