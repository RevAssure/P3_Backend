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

    @ManyToOne
    @JoinColumn(name = "trainer_id" , referencedColumnName = "id")
    @JsonIgnoreProperties("ownedCurricula")
    private RevUser trainer;

    @OneToMany(mappedBy = "curriculum")
    @JsonIgnoreProperties("curriculum")
    private List<Event> events;

    @ManyToMany
    @JoinTable(
            name="revuser_curriculum",
            joinColumns=@JoinColumn(name="revuser_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "curriculum_id", referencedColumnName = "id")
    )
    @JsonIgnoreProperties("curricula")
    private List<RevUser> revUsers;
}
