package com.revature.RevAssure.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.RevAssure.dto.TopicDTO;
import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.model.Topic;
import com.revature.RevAssure.service.RevUserService;
import com.revature.RevAssure.service.TopicService;
import com.revature.RevAssure.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controller layer for Topic objects
 */
@CrossOrigin
@RestController
@RequestMapping("/topic")
public class TopicController{
    private final TopicService topicService;
    private final RevUserService revUserService;

    /**
     * Constructor for TopicController
     *
     * @param topicService is a TopicService object
     * @param revUserService is a RevUserService object
     */
    @Autowired
    public TopicController(TopicService topicService, RevUserService revUserService){
        this.topicService = topicService;
        this.revUserService = revUserService;
    }

    // Create
    /**
     * Create operation for Topic objects
     * revUser retrieves the username from the current JWT
     * @return the topic that is saved or sets bad status if user is not a trainer
     */
    @PostMapping
    public ResponseEntity<String> createTopic(@RequestBody TopicDTO topicdto) throws JsonProcessingException {
        RevUser revUser = JwtUtil.extractUser(revUserService);
        if(revUser.isTrainer()){
            Topic topic = topicService.saveTopic(topicdto.convertToEntity(revUser));
            String str = new ObjectMapper()
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(topic);
            return ResponseEntity.ok().body(str);
        }
        else{return ResponseEntity.status(HttpStatus.FORBIDDEN).build();}
    }

    // Read

    /**
     * Read operation for Topic objects created by requesting trainer
     * revUser retrieves the user from the current JWT
     * @return A list of topics with the same Trainer ID or null if user is not a trainer
     */
    @GetMapping
    public List<Topic> getTopicsByTrainerId(){
        RevUser revUser = JwtUtil.extractUser(revUserService);
        if(revUser.isTrainer()){
            return topicService.getByTrainer(revUser);
        }
        else{return null;}
    }

    /**
     * Read operation for all Topic objects
     * @return A list of all the topics
     */
    @GetMapping("/all")
    public List<Topic> getAllTopics(){
        return topicService.getAll();

    }

    /**
     * Read operation for Topic objects by their ID
     * revUser retrieves the username from the current JWT
     * @return The topic with the corresponding ID
     */
    @GetMapping("/{topicId}")
    public Topic getTopicById(@PathVariable int topicId){
        RevUser revUser = JwtUtil.extractUser(revUserService); //not sure if necessary
        return topicService.getById(topicId);

    }
    /**
     * Read operation for Topic objects by their module ID
     * revUser retrieves the username from the current JWT
     * @return A list of topics with the same module ID
     */
    @GetMapping("/module/{moduleId}")
    public List<Topic> getTopicsByModuleId(@PathVariable int moduleId){
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
    public ResponseEntity<String> updateTopic(@RequestBody TopicDTO topicdto) throws JsonProcessingException {
        RevUser revUser = JwtUtil.extractUser(revUserService);
        if(revUser.isTrainer()){
            Topic topic = topicService.saveTopic(topicdto.convertToEntity(revUser));
            String str = new ObjectMapper()
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(topic);
            return ResponseEntity.ok().body(str);
        }
        else{return ResponseEntity.status(HttpStatus.FORBIDDEN).build();}
    }

    // Delete
    /**
     * Delete operation for Topic objects
     * revUser retrieves the username from the current JWT
     */

    @DeleteMapping("/{topicId}")
    public void deleteTopic(@PathVariable int topicId){
        RevUser revUser = JwtUtil.extractUser(revUserService);
        if(revUser.isTrainer()){topicService.deleteTopic(topicId);}
    }

}


