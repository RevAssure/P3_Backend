package com.revature.RevAssure.repository;

import com.revature.RevAssure.model.Curriculum;
import com.revature.RevAssure.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Integer> {
    Optional<List<Event>> findByCurriculumId(int curriculum); // This method finds the event by the given curriculum

}
