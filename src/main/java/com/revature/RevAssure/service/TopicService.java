package com.revature.RevAssure.service;

import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.model.Topic;
import com.revature.RevAssure.repository.TopicRepository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicService {
    private final TopicRepository topicRepository;
    private static final Logger log = LoggerFactory.getLogger(TopicService.class);

    @Autowired
    public TopicService(TopicRepository topicRepository){
        this.topicRepository = topicRepository;
    }

    /**
     * Adds topic object into database
     * Does not require topic_id
     * @param topic topic object
     * @return Topic object
     */
    public Topic saveTopic(Topic topic){
        log.info("saving new topic");
        return topicRepository.save(topic);
    }

    /**
     * Get a lists of all Topic objects in the database
     * @return List of Topic Objects
     */
    public List<Topic> getAll(){
        log.info("getting all list of topics");
        return topicRepository.findAll();
    }

    /**
     *
     * @param trainer The RevUser object representing the person making the request
     * @return List of Topic Objects
     */
    public List<Topic> getByTrainer(RevUser trainer) {
        log.info("getting list of topics by trainer");
        return topicRepository.findByTrainer(trainer);
    }

    /**
     * gets topic by id. If id does not exists, then returns null
     * @param topicId integer denoted current topic id
     * @return Topic object or null
     */
    public Topic getById(int topicId){
        log.info("grab topic by id");
        return topicRepository.getById(topicId);
    }

    /**
     * gets all topic according to moduleId. If moduleId does no exists return empty array
     * @param moduleId Module id that is shared by all returned topics
     * @return A list of Topics with the same module ID
     */
    public List<Topic> getAllTopicsByModuleId(int moduleId){
        log.info("trying to get topics by module id");
        return topicRepository.findByModulesId(moduleId);
    }

    /**
     * Service to update an existing topic
     * @param topic the topic to be updated
     */
    public Topic updateTopic(Topic topic){
        log.info("updating topic");
        return topicRepository.save(topic);}

    /**
     * Service to delete an existing topic
     * @param topicId the ID number of a topic
     */
    public void deleteTopic(int topicId){
        log.info("deleting topic");
        topicRepository.deleteById(topicId);
    }
}
