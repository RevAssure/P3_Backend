package com.revature.RevAssure.service;

import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.model.Topic;
import com.revature.RevAssure.model.Module;
import com.revature.RevAssure.repository.TopicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class TopicServiceTest {

    private TopicRepository topicRepository;
    private TopicService topicService;

    private Topic topic;
    private List<Topic> topicList;

    private RevUser trainer;
    private Module module;
    private List<Module> moduleList;

    @BeforeEach
    void setUp() {
        topicRepository = Mockito.mock(TopicRepository.class);
        topicService = new TopicService(topicRepository);

        trainer = Mockito.mock(RevUser.class);
        module = Mockito.mock(Module.class);
        moduleList = new ArrayList<>();

        moduleList.add(module);

        topic = new Topic();
        topic.setId(1);
        topic.setTitle("title");
        topic.setDescription("topic description");
        topic.setEstimatedDuration(90);
        topic.setLectureNotes("Lecture Notes String");
        topic.setGithubRepo("Github repo link");
        topic.setTrainer(trainer);
        topic.setModules(moduleList);

        topicList = new ArrayList<>();
        topicList.add(topic);

    }

    @Test
    void creatingRevUserWithSaveTopic(){
        when(topicRepository.save(any(Topic.class))).thenReturn(topic);
        assertEquals(topic, topicService.saveTopic(topic));
    }

    @Test
    void getAllTopics(){
        when(topicRepository.findAll()).thenReturn(topicList);
        assertEquals(topicList, topicService.getAllTopics());
    }

    @Test
    void getTheTopicById(){
        when(topicRepository.getById(anyInt())).thenReturn(topic);
        assertEquals(topic, topicService.getById(1));
    }

    /**
     * Unit test for the update operation of Topic objects
     */
    @Test
    void updateTopicTest(){
        when(topicRepository.save(any(Topic.class))).thenReturn(topic);
        assertEquals(topic, topicService.updateTopic(topic));
    }
    /**
     * Unit test for the delete operation of Topic objects
     */
    @Test
    void deleteTopicTest(){
        doNothing().when(topicRepository).delete(topic);
        assertEquals(topic, topicService.deleteTopic(topic));
    }





}