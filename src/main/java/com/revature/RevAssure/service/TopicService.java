package com.revature.RevAssure.service;

import com.revature.RevAssure.model.Topic;
import com.revature.RevAssure.repository.TopicRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicService {
    private final TopicRepository topicRepository;

    @Autowired
    public TopicService(TopicRepository topicRepository){
        this.topicRepository = topicRepository;
    }

    public Topic saveTopic(Topic topic){
        return topicRepository.save(topic);
    }

    public List<Topic> getAllTopics(){
        return topicRepository.findAll();
    }

    public Topic getById(int topicId){
        return topicRepository.getById(topicId);
    }

    public Topic updateTopic(Topic topic){return topicRepository.save(topic);}

    public Topic deleteTopic(Topic topic){
        topicRepository.delete(topic);
        return topic;
    }

}
