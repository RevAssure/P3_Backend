package com.revature.RevAssure.service;

import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.model.Topic;
import com.revature.RevAssure.model.Module;
import com.revature.RevAssure.repository.TopicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TopicServiceTest {
    @Mock
    private TopicRepository topicRepository;

    private TopicService topicService;

    private Topic topic;
    private List<Topic> topicList;

    private RevUser trainer;
    private Module module;
    private List<Module> moduleList;

    @BeforeEach
    void setUp() {
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

    /**
     * Testing the saveTopic method will properly save and return topic object.
     */
    @Test
    void creatingRevUserWithSaveTopic(){
        when(topicRepository.save(topic)).thenReturn(topic);
        assertEquals(topic, topicService.createTopic(topic));
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
        when(topicRepository.findByTrainer(trainer)).thenReturn(topicList);
        assertEquals(topicList, topicService.getByTrainer(trainer));
    }

    /**
     * Testing service will return topic by trainer
     */
    @Test
    void getByTrainerButTrainerHasNoTopicsTest() {
        when(topicRepository.findByTrainer(trainer)).thenReturn(new ArrayList<>());
        assertEquals(new ArrayList<>(), topicService.getByTrainer(trainer));
    }

//    /**
//     * if topic id does not exists should return null
//     */
//    @Deprecated
//    @Test
//    void getTopicByIdButTopicIdDoesNotExists(){
//        when(topicRepository.getById(1)).thenReturn(null);
//        assertEquals(null, topicService.getById(1));
//    }

    @Test
    void createTopicTest()
    {
        when(topicRepository.findById(1)).thenReturn(Optional.empty());
        when(topicRepository.save(topic)).thenReturn(topic);
        assertEquals(topic, topicService.createTopic(topic));
    }

    /**
     * Unit test for the update operation of Topic objects
     */
    @Test
    void updateTopicTest(){
        when(topicRepository.findById(1)).thenReturn(Optional.of(topic));
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
        when(topicRepository.findByModulesId(1)).thenReturn(topicList);
        assertEquals(topicList, topicService.getAllTopicsByModuleId(1));
    }

    /**
     * gets topicList with moduleId. If moduleId does not exists then returns empty ArrayList<Topic>
     */
    @Test
    void getTopicsByModuleIdButModuleIdDoesnotExists(){
        when(topicRepository.findByModulesId(1)).thenReturn(new ArrayList<>());
        assertEquals(new ArrayList<Topic>(), topicService.getAllTopicsByModuleId(1));
    }

    /**
     * test getById is working correctly
     */
    @Test
    void getById() {
        when(topicRepository.getById(1)).thenReturn(topic);
        assertEquals(topic, topicService.getById(1));
    }
}