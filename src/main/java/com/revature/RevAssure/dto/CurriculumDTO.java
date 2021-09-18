package com.revature.RevAssure.dto;

import com.revature.RevAssure.model.Curriculum;
import com.revature.RevAssure.model.Event;
import com.revature.RevAssure.model.RevUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CurriculumDTO {

    private int id;
    private String name;
    private List<Integer> events;
    private List<Integer> associates;

    public Curriculum convertToEntity(RevUser revUser) {

        Curriculum curriculum = new Curriculum();
        curriculum.setTrainer(revUser);

        curriculum.setId(this.id);
        curriculum.setName(this.name);

        List<Event> events = new ArrayList<>();
        if (this.events != null) {
            for (Integer eventId: this.events) {
                Event event = new Event();
                event.setId(eventId);
                events.add(event);
            }
        }
        curriculum.setEvents(events);

        List<RevUser> associates = new ArrayList<>();
        if (this.associates != null) {
            for (Integer associateId: this.events) {
                RevUser associate = new RevUser();
                associate.setId(associateId);
                associates.add(associate);
            }
        }
        curriculum.setRevUsers(associates);

        return curriculum;
    }
}
