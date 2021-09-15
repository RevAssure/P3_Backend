package com.revature.RevAssure.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="curriculum")

@Getter
@Setter
@NoArgsConstructor
public class Curriculum {

    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "curriculum")
    private List<Event> events;

    @ManyToMany(mappedBy = "curricula")
    private List<RevUser> revUsers;
}
