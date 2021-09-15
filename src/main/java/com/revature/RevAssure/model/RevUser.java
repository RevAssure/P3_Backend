package com.revature.RevAssure.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="rev_user")

@Getter
@Setter
@NoArgsConstructor

public class RevUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name", nullable=false)
    private String firstName;

    @Column(name = "last_name", nullable=false)
    private String lastName;

    @Column(name = "is_trainer", nullable = false)
    private boolean isTrainer;

    @OneToMany(mappedBy = "trainer")
    private List<Topic> topics;

    @ManyToMany
    @JoinTable(name="revuser_curriculum")
    private List<Curriculum> curricula;

}
