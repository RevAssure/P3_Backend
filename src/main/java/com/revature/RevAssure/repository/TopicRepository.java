package com.revature.RevAssure.repository;


import com.revature.RevAssure.model.Module;
import com.revature.RevAssure.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer>{

    /**
     * Return a list of all topics within given module object
     * @param module Given Module Object
     * @return list of Topic objects
     */
    List<Topic> findAllByModules(Module module);

}
