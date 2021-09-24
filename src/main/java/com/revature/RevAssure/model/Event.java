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

    //hibernateLazyInitializer created when hibernate loads list from database. I can't find which attribute this comes from, though
    // - Victor Liu
    @ManyToOne
    @JsonIgnoreProperties({"revUsers", "events", "hibernateLazyInitializer"})
    @JoinColumn(name="curriculum_id", nullable = false, referencedColumnName = "id")
    private Curriculum curriculum;

    @Column(name="start_datetime", nullable = false)
    // unix timestamp
    private long startDatetime;

    @OneToOne
    @JsonIgnoreProperties("trainer")
    @JoinColumn(name="topic_id", nullable = false)
    private Topic topic;
}
