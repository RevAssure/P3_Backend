package com.revature.RevAssure.model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name="topic")

@Getter
@Setter
@NoArgsConstructor
public class Topic {

    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name="title")
    private String title;

    @Column(name="description")
    private String description;

    @Column(name="estimated_duration")
    // duration in minutes
    private int estimatedDuration;

    @Column(name="lecture_notes")
    private String lectureNotes;

    @Column(name="github_repo")
    private String githubRepo;

    @ManyToOne
    @JoinColumn(name="tech_category_id", referencedColumnName = "id")
    private TechnologyCategory technologyCategory;

    @ManyToOne
    @JoinColumn(name="module_id", referencedColumnName = "id")
    private Module module;
}