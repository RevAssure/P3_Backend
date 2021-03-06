package com.revature.RevAssure.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="module")

@Getter
@Setter
@NoArgsConstructor
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name="description", length = 7000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "trainer_id" , referencedColumnName = "id")
    private RevUser trainer;

    @ManyToOne
    @JoinColumn(name = "tech_category_id", referencedColumnName = "id")
    private TechnologyCategory technologyCategory;

    @ManyToMany(mappedBy = "modules")
    private List<Topic> topics;
}
