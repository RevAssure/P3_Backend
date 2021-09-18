package com.revature.RevAssure.dto;

import com.revature.RevAssure.model.Curriculum;
import com.revature.RevAssure.model.Event;
import com.revature.RevAssure.model.Topic;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EventDTO {

    private int id;
    private int startDatetime;

    private int curriculum;
    private int topic;

    public Event convertToEntity() {

        Event event = new Event();

        event.setId(id);
        event.setStartDatetime(this.startDatetime);

        Curriculum curriculum = new Curriculum();
        curriculum.setId(this.curriculum);
        event.setCurriculum(curriculum);

        Topic topic = new Topic();
        topic.setId(this.topic);
        event.setTopic(topic);
        return event;
    }

}
