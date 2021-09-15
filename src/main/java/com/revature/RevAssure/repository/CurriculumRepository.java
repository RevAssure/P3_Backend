package com.revature.RevAssure.repository;

import com.revature.RevAssure.model.Curriculum;
import com.revature.RevAssure.model.RevUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, Integer>
{
    public List<Curriculum> findAllByRevUser(RevUser trainer);
}
