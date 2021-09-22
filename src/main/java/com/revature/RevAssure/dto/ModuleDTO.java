package com.revature.RevAssure.dto;

import com.revature.RevAssure.model.Module;
import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.model.TechnologyCategory;
import com.revature.RevAssure.model.Topic;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

public class ModuleDTO {
    private int id;
    private String name;
    private String description;

    private int technologyCategory;

    private List<Integer> topics;

    public Module convertToEntity(RevUser trainer) {
        Module module = new Module();
        module.setTrainer(trainer);

        module.setId(this.id);
        module.setName(this.name);
        module.setDescription(this.description);

        TechnologyCategory technologyCategory = new TechnologyCategory();
        technologyCategory.setId(this.technologyCategory);
        module.setTechnologyCategory(technologyCategory);

        List<Topic> topics = new ArrayList<>();
        if (this.topics != null) {
            for (Integer topicId: this.topics) {
                Topic topic = new Topic();
                topic.setId(topicId);
                topics.add(topic);
            }
        }
        module.setTopics(topics);
        return module;
    }
}
