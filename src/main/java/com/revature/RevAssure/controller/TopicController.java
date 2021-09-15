package com.revature.RevAssure.controller;

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
     * @return Returns null
     */
    @PostMapping
    public Topic createTopic(@RequestBody Topic topic){
        RevUser revUser = extractUser();
        // return topicService.saveTopic(topic);
        return null;
    }

    // Read
    /**
     * Read operation for all Topic objects
     * @return Returns null
     */
    @GetMapping
    public List<Topic> getAllTopics(){
        RevUser revUser = extractUser();
        // return topicService.getAll();
        return null;
    }

    /**
     * Read operation for Topic objects by their ID
     * @return Returns null
     */
    @GetMapping("/topic/{topicId}")
    public Topic getTopicById(@PathVariable int topicId){
        RevUser revUser = extractUser();
//        return topicService.getById(topicId);
        return null;
    }
    /**
     * Read operation for Topic objects by their module ID
     * @return Returns null
     */
    @GetMapping("/module/{moduleId}")
    public List<Topic> getTopicsByModuleId(@PathVariable int moduleId){
        RevUser revUser = extractUser();
//        return topicService.getAllTopicsByModuleId(moduleId);
        return null;
    }
    /**
     * Read operation for Topic objects by the trainer that created them
     * @return Returns null
     */
    @Deprecated // we might not use it or we might
    @GetMapping("/trainer/{trainerId}")
    public List<Topic> getTopicsByTrainerId(@PathVariable int trainerId){
        RevUser revUser = extractUser();
//        return topicService.getAllTopicsByModuleId(trainerId);
        return null;
    }

    // Update
    /**
     * Update operation for Topic objects
     * @return Returns null
     */
    // TODO: Make sure when a trainer is updating a topic that is not owned by them
    //  they are creating a new topic instead of modifying a previous one
    @PutMapping
    public Topic updateTopic(@RequestBody Topic topic){
        RevUser revUser = extractUser();
        // return topicService.saveTopic(topic);
        return null;
    }

    // Delete
    /**
     * Delete operation for Topic objects
     * @return Returns null
     */
    @DeleteMapping("/{topicId}")
    public Topic deleteTopic(@PathVariable int topicId){
        RevUser revUser = extractUser();
        // return topicService.deleteTopic(topicId);
        return null;
    }

    private RevUser extractUser(){
        String username = JwtUtil.extractUsername();
        // return revUserService.getRevUserByUsername(username);
        return null;
    }

}


