package com.revature.RevAssure.service;

import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.model.Topic;
import com.revature.RevAssure.model.Module;
import com.revature.RevAssure.repository.ModuleRepository;
import com.revature.RevAssure.repository.TopicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TopicServiceTest {
    @MockBean
    private TopicRepository topicRepository;

    @Autowired
    private TopicService topicService;

    private Topic topic;
    private List<Topic> topicList;

    private RevUser trainer;
    private Module module;
    private List<Module> moduleList;

    @BeforeEach
    void setUp() {
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
        when(topicRepository.save(topic)).thenReturn(topic);
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
        when(topicRepository.getById(1)).thenReturn(topic);
        assertEquals(topic, topicService.getById(1));
    }

    /**
     * Testing service will return topic by trainer
     */
    @Test
    void getByTrainerTest() {
        when(topicRepository.findByTrainer(trainer)).thenReturn(Optional.of(topicList));
        assertEquals(topicList, topicService.getByTrainer(trainer));
    }

    /**
     * Testing service will return topic by trainer
     */
    @Test
    void getByTrainerButTrainerHasNoTopicsTest() {
        when(topicRepository.findByTrainer(trainer)).thenReturn(Optional.empty());
        assertEquals(new ArrayList<>(), topicService.getByTrainer(trainer));
    }

    /**
     * if topic id does not exists should return null
     */
    @Test
    void getTopicByIdButTopicIdDoesNotExists(){
        when(topicRepository.findById(1)).thenReturn(Optional.empty());
        assertEquals(null, topicService.getById(1));
    }

    /**
     * Unit test for the update operation of Topic objects
     */
    @Test
    void updateTopicTest(){
        when(topicRepository.save(topic)).thenReturn(topic);
        assertEquals(topic, topicService.updateTopic(topic));
    }
    /**
     * Unit test for the delete operation of Topic objects
     */
    @Test
    void deleteTopicTest(){
        doNothing().when(topicRepository).deleteById(1);
        topicService.deleteTopic(1);
       verify(topicRepository, times(1)).deleteById(1);
    }

    /**
     * get a topicList by moduleId
     */
    @Test
    void getTopicsByModuleId(){
        when(topicRepository.findByModulesId(1)).thenReturn(Optional.of(topicList));
        assertEquals(topicList, topicService.getAllTopicsByModuleId(1));
    }

    /**
     * gets topicList with moduleId. If moduleId does not exists then returns empty ArrayList<Topic>
     */
    @Test
    void getTopicsByModuleIdButModuleIdDoesnotExists(){
        when(topicRepository.findById(1)).thenReturn(Optional.empty());
        assertEquals(new ArrayList<Topic>(), topicService.getAllTopicsByModuleId(1));
    }

}