package com.revature.RevAssure.service;

import com.revature.RevAssure.dto.TopicDTO;
import com.revature.RevAssure.model.Module;
import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.model.Topic;
import com.revature.RevAssure.repository.TechnologyCategoryRepository;
import com.revature.RevAssure.repository.TopicRepository;
import com.revature.RevAssure.repository.ModuleRepository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicService {
    private final TopicRepository topicRepository;
    private final ModuleRepository moduleRepository;

    @Autowired
    public TopicService(TopicRepository topicRepository, ModuleRepository moduleRepository){
        this.topicRepository = topicRepository;
        this.moduleRepository = moduleRepository;
    }

    /**
     * Adds topic object into database
     * Does not require topic_id
     * @param topic topic object
     * @return Topic object
     */
    public Topic saveTopic(Topic topic){
        return topicRepository.save(topic);
    }

    /**
     * Get a lists of all Topic objects in the database
     * @return List of Topic Objects
     */
    public List<Topic> getAll(){
        return topicRepository.findAll();
    }

    /**
     * gets topic by id. If id does not exists, then returns null
     * @param topicId integer denoted current topic id
     * @return Topic object or null
     */
    public Topic getById(int topicId){
        return topicRepository.findById(topicId).orElse(null);
    }

    /**
     * gets all topic according to moduleId. If moduleId does no exists return empty array
     * @param moduleId
     * @return A list of Topics with the same module ID
     */
    public List<Topic> getAllTopicsByModuleId(int moduleId){
        Module module = moduleRepository.findById(moduleId).orElse(null);
        if(module == null){
            // TODO: when we get logger. log that ModuleId does not exists
            return new ArrayList<Topic>(); // I'm not sure if findAllByModules will give an empty array when given null
        }
        return topicRepository.findAllByModules(module);
    }

    /**
     * Service to update an existing topic
     * @param topic the topic to be updated
     */
    public Topic updateTopic(Topic topic){return topicRepository.save(topic);}

    /**
     * Service to delete an existing topic
     * @param topicId the ID number of a topic
     */
    public void deleteTopic(int topicId){
        topicRepository.deleteById(topicId);
    }
}
