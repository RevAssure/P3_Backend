package com.revature.RevAssure.repository;


import com.revature.RevAssure.model.TechnologyCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TechnologyCategoryRepository extends JpaRepository<TechnologyCategory, Integer>{


}
