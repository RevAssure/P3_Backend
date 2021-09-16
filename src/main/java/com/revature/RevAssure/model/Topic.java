package com.revature.RevAssure.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="topic")

@Getter
@Setter
@NoArgsConstructor
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "estimated_duration")
    // duration in minutes
    private int estimatedDuration;

    @Column(name = "lecture_notes")
    private String lectureNotes;

    @Column(name = "github_repo")
    private String githubRepo;

    @ManyToOne
    @JoinColumn(name = "trainer_id" , referencedColumnName = "id")
    private RevUser trainer;

    @ManyToOne
    @JoinColumn(name = "tech_category_id", referencedColumnName = "id")
    private TechnologyCategory technologyCategory;

    @ManyToMany
    @JoinTable(name = "topic_module")
    @JsonIgnoreProperties({"topics", "trainer"})
    private List<Module> modules;


}
