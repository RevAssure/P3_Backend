package com.revature.RevAssure.controller;

import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.model.Topic;
import com.revature.RevAssure.service.RevUserService;
import com.revature.RevAssure.service.TopicService;
import com.revature.RevAssure.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/topic")
public class TopicController{
    private final TopicService topicService;
    private final RevUserService revUserService;

    @Autowired
    public TopicController(TopicService topicService, RevUserService revUserService){
        this.topicService = topicService;
        this.revUserService = revUserService;
    }

    // Create

    @PostMapping
    public Topic createTopic(@RequestBody Topic topic){
        RevUser revUser = extractUser();
        return topicService.saveTopic(topic);
    }

    // Read

    @GetMapping
    public List<Topic> getAllTopics(){
        RevUser revUser = extractUser();
        return topicService.getAll();

    }

    @GetMapping("/topic/{topicId}")
    public Topic getTopicById(@PathVariable int topicId){
        RevUser revUser = extractUser();
        return topicService.getById(topicId);

    }

    @GetMapping("/module/{moduleId}")
    public List<Topic> getTopicsByModuleId(@PathVariable int moduleId){
        RevUser revUser = extractUser();
        return topicService.getAllTopicsByModuleId(moduleId);

    }

    @Deprecated // we might not use it or we might
    @GetMapping("/trainer/{trainerId}")
    public List<Topic> getTopicsByTrainerId(@PathVariable int trainerId){
        RevUser revUser = extractUser();
        return topicService.getAllTopicsByModuleId(trainerId);

    }

    // Update

    // TODO: Make sure when a trainer is updating a topic that is not owned by them
    //  they are creating a new topic instead of modifying a previous one
    @PutMapping
    public Topic updateTopic(@RequestBody Topic topic){
        RevUser revUser = extractUser();
        return topicService.saveTopic(topic);

    }

    // Delete

    @DeleteMapping("/{topicId}")
    public Topic deleteTopic(@PathVariable int topicId){
        RevUser revUser = extractUser();
        return topicService.deleteTopic(topicId);

    }

    private RevUser extractUser(){
        String username = JwtUtil.extractUsername();
        return revUserService.getRevUserByUsername(username);

    }

}


