package com.revature.RevAssure.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "curriculum")
    @JsonIgnoreProperties("curriculum")
    private List<Event> events;

    @ManyToMany
    @JoinTable(
            name="revuser_curriculum",
            joinColumns=@JoinColumn(name="rev_users_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "curricula_id", referencedColumnName = "id")
    )
    private List<RevUser> revUsers;
}
