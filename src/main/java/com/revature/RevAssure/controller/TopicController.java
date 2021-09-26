package com.revature.RevAssure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.revature.RevAssure.dto.TopicDTO;
import com.revature.RevAssure.model.Event;
import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.model.Topic;
import com.revature.RevAssure.service.EventService;
import com.revature.RevAssure.service.RevUserService;
import com.revature.RevAssure.service.TopicService;
import com.revature.RevAssure.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * Controller layer for Topic objects
 */
@CrossOrigin
@RestController
@RequestMapping("/topic")
public class TopicController{
    private static final Logger log = LoggerFactory.getLogger(TopicController.class);

    private final TopicService topicService;
    private final RevUserService revUserService;
    private final EventService eventService;
    private final ObjectMapper objectMapper;

    /**
     * Constructor for TopicController
     *
     * @param topicService is a TopicService object
     * @param revUserService is a RevUserService object
     */
    @Autowired
    public TopicController(TopicService topicService, RevUserService revUserService, EventService eventService, ObjectMapper objectMapper){
        this.topicService = topicService;
        this.revUserService = revUserService;
        this.eventService = eventService;
        this.objectMapper = objectMapper;
    }

    // Create
    /**
     * Create operation for Topic objects
     * revUser retrieves the username from the current JWT
     * @return the topic that is saved or sets bad status if user is not a trainer
     */
    @PostMapping
    public ResponseEntity<String> createTopic(@RequestBody TopicDTO topicdto) {
        RevUser revUser = JwtUtil.extractUser(revUserService);
        try {
            if (revUser.isTrainer()) {
                log.info("Trainer is creating a new topic");
                return ResponseEntity.ok().body(
                        new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(
                                topicService.saveTopic(
                                        topicdto.convertToEntity(revUser))));
            } else {
                log.warn("Associate is attempting to create a new topic");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        } catch(Exception e){
            log.error("Topic failed to be mapped as a JSON string",e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // Read

    /**
     * Read operation for Topic objects created by requesting trainer
     * revUser retrieves the user from the current JWT
     * @return A list of revUsers as strings and status ok. Forbidden if non-trainer requests
     */
    @GetMapping
    public ResponseEntity<String> getTopicsByTrainerId(){
        RevUser revUser = JwtUtil.extractUser(revUserService);
        try {
            if (revUser.isTrainer()) {
                log.info("Getting all topics owned by this trainer");


                return ResponseEntity.ok().body(new ObjectMapper().writerWithDefaultPrettyPrinter().
                        writeValueAsString(topicService.getByTrainer(revUser)));
            } else {
                log.warn("Associate is attempting to get all topics they own, but they don't own any");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        } catch (Exception e) {
            log.error("Topic failed to be mapped as a JSON string",e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Read operation for all Topic objects
     * @return A list of all the topics
     */
    @GetMapping("/all")
    public List<Topic> getAllTopics(){
        log.info("Getting all topics");
        return topicService.getAll();

    }

    /**
     * Read operation for Topic objects by their ID
     * revUser retrieves the username from the current JWT
     * @return The topic with the corresponding ID
     */
    @GetMapping("/{topicId}")
    public Topic getTopicById(@PathVariable int topicId){
        log.info("Getting a specific topic by its id");
        return topicService.getById(topicId);

    }
    /**
     * Read operation for Topic objects by their module ID
     * revUser retrieves the username from the current JWT
     * @return A list of topics with the same module ID
     */
    @GetMapping("/module/{moduleId}")
    public List<Topic> getTopicsByModuleId(@PathVariable int moduleId){
        log.info("Getting all topics in a module");
        return topicService.getAllTopicsByModuleId(moduleId);
    }

    // Update

    /**
     * Update operation for Topic objects
     * revUser retrieves the username from the current JWT
     * @return The updated topic or sets bad status if user is not a trainer
     */
    // TODO: Make sure when a trainer is updating a topic that is not owned by them
    //  they are creating a new topic instead of modifying a previous one
    @PutMapping
    public ResponseEntity<String> updateTopic(@RequestBody TopicDTO topicdto) {
        RevUser revUser = JwtUtil.extractUser(revUserService);
        try {
            log.info("Trainer is updating a topic they own");
            if (revUser.isTrainer()) {
                return ResponseEntity.ok().body(
                        new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(
                                topicService.saveTopic(
                                        topicdto.convertToEntity(revUser))));
            } else {
                log.warn("Associate attempted to update a topic");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        } catch (Exception e) {
            log.error("Topic failed to be mapped as a JSON string",e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // Delete

    /**
     * Delete operation for Topic objects
     * revUser retrieves the username from the current JWT
     */
    @DeleteMapping("/{topicId}")
    public ResponseEntity<String> deleteTopic(@PathVariable int topicId) throws IOException {
        RevUser revUser = JwtUtil.extractUser(revUserService);
        if(revUser.isTrainer()) {
            if (revUser.getTopics().contains(topicService.getById(topicId))) {
                log.info("Trainer is deleting a topic they own");
                try {
                    topicService.deleteTopic(topicId);
                    return ResponseEntity.ok().build();
                } catch (DataIntegrityViolationException exception) {
                    List<Event> events = eventService.getAllEventsByTopicId(topicId);

                    ObjectNode errorMessage = objectMapper.createObjectNode();
                    errorMessage.put("Message", String.format( "Cannot delete Topic (%d) due to Events referencing it", topicId));
                    ArrayNode eventsNode = errorMessage.putArray("Events");
                    for (Event event: events) {
                        eventsNode.add(event.getId());
                    }
                    return ResponseEntity.status(470).body(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(errorMessage));
                }
            } else {
                log.warn(String.format("%s attempted to delete Topic (%d) not owned by them.", revUser.getUsername(), topicId));
                ObjectNode errorMessage = objectMapper.createObjectNode();
                errorMessage.put("Message", String.format("%s cannot delete Topic (%d), they are not the owner", revUser.getUsername(), topicId));
                return ResponseEntity.status(401).body(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(errorMessage));
            }
        } else { // you could catch this condition by checking ownership also, but having this broken up (isTrainer) is more informative.
            log.warn(String.format("%s attempted to delete a Topic, Associates cannot delete Topics.", revUser.getUsername()));
            ObjectNode errorMessage = objectMapper.createObjectNode();
            errorMessage.put("Message", String.format("%s cannot delete Topics as an Associate", revUser.getUsername(), topicId));
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(errorMessage));
        }
    }
}


