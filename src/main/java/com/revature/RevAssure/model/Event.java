package com.revature.RevAssure.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="event")

@Getter
@Setter
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @ManyToOne
    @JsonIgnoreProperties({"revUsers", "curriculum", "events"})
    @JoinColumn(name="curriculum_id", nullable = false, referencedColumnName = "id")
    private Curriculum curriculum;

    @Column(name="start_datetime", nullable = false)
    // unix timestamp
    private int startDatetime;

    @OneToOne
    @JsonIgnoreProperties("trainer")
    @JoinColumn(name="topic_id", nullable = false)
    private Topic topic;

    //TODO: write method to parse request and convert to Event oject.
    /* request looks like this:
    {
    "curriculum" : {
        "id":1
    },
    "startDatetime": 1633158200,
    "topic": {
        "id": 4
    }
}

     */

}
