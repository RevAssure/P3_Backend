package com.revature.RevAssure.service;

import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.model.Topic;
import com.revature.RevAssure.repository.TopicRepository;

import java.util.ArrayList;
import java.util.List;

import org.postgresql.core.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicService {
    private final TopicRepository topicRepository;
<<<<<<< HEAD
    private final ModuleRepository moduleRepository;
    private static final Logger log = LoggerFactory.getLogger(ConnectionFactory.class);
=======
>>>>>>> d6367137558e9cf5e612ba8efd9dafe7ddf1d951

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
     * @param trainer
     * @return List of Topic Objects
     */
    public List<Topic> getByTrainer(RevUser trainer) {
<<<<<<< HEAD
        log.info("trainer getting list of topics");
        return topicRepository.findByTrainer(trainer);
=======
        try {
            return topicRepository.findByTrainer(trainer).orElseThrow(RuntimeException::new);
        } catch(RuntimeException e){
            // log
            return new ArrayList<>();
        }
>>>>>>> d6367137558e9cf5e612ba8efd9dafe7ddf1d951
    }

    /**
     * gets topic by id. If id does not exists, then returns null
     * @param topicId integer denoted current topic id
     * @return Topic object or null
     */
    public Topic getById(int topicId){
<<<<<<< HEAD
        log.info("grab topic by id");
        return topicRepository.findById(topicId).orElse(null);
=======
        return topicRepository.getById(topicId);
>>>>>>> d6367137558e9cf5e612ba8efd9dafe7ddf1d951
    }

    /**
     * gets all topic according to moduleId. If moduleId does no exists return empty array
     * @param moduleId Module id that is shared by all returned topics
     * @return A list of Topics with the same module ID
     */
    public List<Topic> getAllTopicsByModuleId(int moduleId){
<<<<<<< HEAD
        log.info("use module id to get list of topics");
        Module module = moduleRepository.findById(moduleId).orElse(null);
        if(module == null){
            // TODO: when we get logger. log that ModuleId does not exists
            return new ArrayList<Topic>(); // I'm not sure if findAllByModules will give an empty array when given null
=======
        try {
            return topicRepository.findByModulesId(moduleId).orElseThrow(RuntimeException::new);
        }
        catch(RuntimeException e){
            // TODO: when we get logger. log that there are no topics with corresponding Module id
            return new ArrayList<Topic>();
>>>>>>> d6367137558e9cf5e612ba8efd9dafe7ddf1d951
        }
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
