package com.revature.RevAssure.repository;

import com.revature.RevAssure.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByCurriculumId(int curriculum); // This method finds the event by the given curriculum
}
