package com.revature.RevAssure.controller;

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
     *
     * @param event
     * @return
     */
    @PostMapping
    public Event createEvent(@RequestBody Event event){
        JwtUtil.extractUser(revUserService);
        return eventService.createEvent(event);
    }

    // Read

    /**
     *
     * @param curriculum_id
     * @return
     */
    @GetMapping("/{curriculum_id}")
    public List<Event> getAllEventsByCurriculumId(@PathVariable int curriculum_id){
        JwtUtil.extractUser(revUserService);
        return eventService.getAllEventsByCurriculumId(curriculum_id);
    }

    // Update

    /**
     *
     * @param event
     * @return
     */
    @PutMapping
    public Event updateEvent(@RequestBody Event event){
        JwtUtil.extractUser(revUserService);
        return eventService.updateEvent(event);
    }

    // Delete

    /**
     * 
     * @param eventId
     */
    @DeleteMapping("/{eventId}")
    public void deleteEvent(@PathVariable int eventId){
        JwtUtil.extractUser(revUserService);
        eventService.deleteEvent(eventId);
    }
}
