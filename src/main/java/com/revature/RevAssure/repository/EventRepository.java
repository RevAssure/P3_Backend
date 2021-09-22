package com.revature.RevAssure.repository;

import com.revature.RevAssure.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByCurriculumId(int curriculum); // This method finds the event by the given curriculum
}
