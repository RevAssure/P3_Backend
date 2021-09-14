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
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name="description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "tech_area_id", referencedColumnName = "id")
    private TechnologyArea techArea;

    @OneToMany(mappedBy = "module")
    private List<Topic> topics;
}
