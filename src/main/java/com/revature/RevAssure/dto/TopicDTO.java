package com.revature.RevAssure.dto;

import com.revature.RevAssure.model.Module;
import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.model.TechnologyCategory;
import com.revature.RevAssure.model.Topic;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Getter
@Setter
@NoArgsConstructor
public class TopicDTO {

    private int id;

    private String title;

    private String description;

    // duration in minutes
    private int estimatedDuration;

    private String lectureNotes;

    private String githubRepo;

    private int trainer;

    private int technologyCategory;

    private List<Integer> modules;

    public Topic convertToEntity(RevUser revUser){
        Topic topic = new Topic();
        topic.setId(this.id);
        topic.setTitle(this.description);
        topic.setDescription(this.description);
        topic.setEstimatedDuration(this.estimatedDuration);
        topic.setGithubRepo(this.githubRepo);
        topic.setLectureNotes(this.lectureNotes);
        topic.setTrainer(revUser);
        TechnologyCategory technologyCategory = new TechnologyCategory();
        technologyCategory.setId(this.technologyCategory);
        topic.setTechnologyCategory(technologyCategory);
        List<Module> mods = new ArrayList<>();
        for(Integer module:modules){
            Module module1 = new Module();
            module1.setId(module);
            mods.add(module1);
        }
        topic.setModules(mods);
        return topic;
    }
}

