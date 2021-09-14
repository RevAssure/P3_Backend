package com.revature.RevAssure.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="technology_area")

@Getter
@Setter
@NoArgsConstructor
public class TechnologyArea {

    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name="name", nullable = false)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "techArea")
    private List<Module> modules;
}
