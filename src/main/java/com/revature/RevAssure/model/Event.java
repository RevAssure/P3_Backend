package com.revature.RevAssure.model;

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
    @Column(name = "id", nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name="curriculum_id", nullable = false, referencedColumnName = "id")
    private Curriculum curriculum;

    @Column(name="start_datetime", nullable = false)
    // unix timestamp
    private int startDatetime;

    @OneToOne
    @JoinColumn(name="topic_id", nullable = false)
    private Topic topic;

}
