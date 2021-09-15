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
    @PostMapping
    public Event createEvent(@RequestBody Event event){
        RevUser revUser = extractUser();
        // eventService.saveEvent(event);
        return null;
    }

    // Read

    @GetMapping("/{curriculum_id}")
    public List<Event> getAllEventsByCurriculumId(@PathVariable int curriculum_id){
        RevUser revUser = extractUser();

        // eventService.getAllEventById(curriculum_id);
        return null;
    }

    // Update

    @PutMapping
    public Event updateEvent(@RequestBody Event event){
        RevUser revUser = extractUser();
        // eventService.saveEvent(event);
        return null;
    }

    // Delete

    @DeleteMapping
    public Event deleteEvent(@RequestBody int eventId){
        RevUser revUser = extractUser();
        // eventService.deleteEvent(eventId);
        return null;
    }


    private RevUser extractUser(){
        String username = JwtUtil.extractUsername();
        // return revUserService.getRevUserByUsername(username);
        return null;
    }

}
