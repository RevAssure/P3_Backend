package com.revature.RevAssure.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="technology_category")

@Getter
@Setter
@NoArgsConstructor
public class TechnologyCategory {
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name="name", nullable = false)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "technologyCategory")
    private List<Topic> topics;

}
