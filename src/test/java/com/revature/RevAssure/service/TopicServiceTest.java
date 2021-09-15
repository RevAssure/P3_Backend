package com.revature.RevAssure.service;

import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.model.Topic;
import com.revature.RevAssure.model.Module;
import com.revature.RevAssure.repository.ModuleRepository;
import com.revature.RevAssure.repository.TopicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

class TopicServiceTest {

    private TopicRepository topicRepository;
    private ModuleRepository moduleRepository;
    private TopicService topicService;

    private Topic topic;
    private List<Topic> topicList;

    private RevUser trainer;
    private Module module;
    private List<Module> moduleList;

    @BeforeEach
    void setUp() {
        topicRepository = Mockito.mock(TopicRepository.class);
        moduleRepository = Mockito.mock(ModuleRepository.class);
        topicService = new TopicService(topicRepository, moduleRepository);

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

    /**
     * Testing the saveTopic method will properly save and return topic object.
     */
    @Test
    void creatingRevUserWithSaveTopic(){
        when(topicRepository.save(any(Topic.class))).thenReturn(topic);
        assertEquals(topic, topicService.saveTopic(topic));
    }

    /**
     * Testing if the getAllTopics works properly
     */
    @Test
    void getAllTopics(){
        when(topicRepository.findAll()).thenReturn(topicList);
        assertEquals(topicList, topicService.getAll());
    }

    /**
     * Testing service will return topic by id
     */
    @Test
    void getTheTopicById(){
        when(topicRepository.findById(anyInt())).thenReturn(Optional.of(topic));
        assertEquals(topic, topicService.getById(1));
    }

    /**
     * if topic id does not exists should return null
     */
    @Test
    void getTopicByIdButTopicIdDoesNotExists(){
        when(topicRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertEquals(null, topicService.getById(1));
    }

    /**
     * get a topicList by moduleId
     */
    @Test
    void getTopicsByModuleId(){
        when(moduleRepository.findById(anyInt())).thenReturn(Optional.of(module));
        when(topicRepository.findAllByModules(module)).thenReturn(topicList);
        assertEquals(topicList, topicService.getAllTopicsByModuleId(1));
    }

    /**
     * gets topicList with moduleId. If moduleId does not exists then returns empty ArrayList<Topic>
     */
    @Test
    void getTopicsByModuleIdButModuleIdDoesnotExists(){
        when(moduleRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertEquals(new ArrayList<Topic>(), topicService.getAllTopicsByModuleId(1));
    }

}