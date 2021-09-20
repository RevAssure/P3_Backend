package com.revature.RevAssure.controller;

import com.revature.RevAssure.dto.TopicDTO;
import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.model.Topic;
import com.revature.RevAssure.service.RevUserService;
import com.revature.RevAssure.service.TopicService;
import com.revature.RevAssure.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
     * @return the topic that is saved
     */
    @PostMapping
    public Topic createTopic(@RequestBody TopicDTO topicdto){
        RevUser revUser = extractUser();
        Topic topic = topicdto.convertToEntity(revUser);
        return topicService.saveTopic(topic);
    }

    // Read
    /**
     * Read operation for all Topic objects
     * revUser retrieves the username from the current JWT
     * @return A list of all the topics
     */
    @GetMapping
    public List<Topic> getAllTopics(){
        RevUser revUser = extractUser();
        return topicService.getAll();

    }

    /**
     * Read operation for Topic objects by their ID
     * revUser retrieves the username from the current JWT
     * @return The topic with the corresponding ID
     */
    @GetMapping("/topic/{topicId}")
    public Topic getTopicById(@PathVariable int topicId){
        RevUser revUser = extractUser();
        return topicService.getById(topicId);

    }
    /**
     * Read operation for Topic objects by their module ID
     * revUser retrieves the username from the current JWT
     * @return A list of topics with the same module ID
     */
    @GetMapping("/module/{moduleId}")
    public List<Topic> getTopicsByModuleId(@PathVariable int moduleId){
        RevUser revUser = extractUser();
        return topicService.getAllTopicsByModuleId(moduleId);

    }
    /**
     * Read operation for Topic objects by the trainer that created them
     * revUser retrieves the username from the current JWT
     * @return A list of topics with the same Trainer ID
     */
    @Deprecated // we might not use it or we might
    @GetMapping("/trainer/{trainerId}")
    public List<Topic> getTopicsByTrainerId(@PathVariable int trainerId){
        RevUser revUser = extractUser();
        return topicService.getAllTopicsByModuleId(trainerId);

    }

    // Update
    /**
     * Update operation for Topic objects
     * revUser retrieves the username from the current JWT
     * @return The updated topic
     */
    // TODO: Make sure when a trainer is updating a topic that is not owned by them
    //  they are creating a new topic instead of modifying a previous one
    @PutMapping
    public Topic updateTopic(@RequestBody TopicDTO topicdto){
        RevUser revUser = extractUser();
        Topic topic = topicdto.convertToEntity(revUser);
        return topicService.saveTopic(topic);

    }

    // Delete
    /**
     * Delete operation for Topic objects
     * revUser retrieves the username from the current JWT
     */

    @DeleteMapping("/{topicId}")
    public void deleteTopic(@PathVariable int topicId){
        RevUser revUser = extractUser();
        topicService.deleteTopic(topicId);


    }
    /**
     * Retrieves the username from the current JWT
     * @return Returns null
     */
    private RevUser extractUser(){
        String username = JwtUtil.extractUsername();
        return revUserService.getRevUserByUsername(username);
    }

}


