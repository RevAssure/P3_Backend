package com.revature.RevAssure.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name", nullable=false)
    private String firstName;

    @Column(name = "last_name", nullable=false)
    private String lastName;

    @Column(name = "is_trainer", nullable = false)
    private boolean isTrainer;

    @OneToMany(mappedBy = "trainer")
    @JsonIgnoreProperties("trainer")
    private List<Topic> topics;

    @ManyToMany(mappedBy = "revUsers")
    @JsonIgnoreProperties("revUsers")
    private List<Curriculum> curricula;

    @OneToMany(mappedBy = "trainer")
    @JsonIgnoreProperties({"trainer", "topics"})
    private List<Module> modules;

}
