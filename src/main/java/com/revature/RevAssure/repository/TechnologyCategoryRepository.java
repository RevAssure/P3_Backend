package com.revature.RevAssure.repository;

import com.revature.RevAssure.model.Module;
import com.revature.RevAssure.model.Topic;
import com.revature.RevAssure.model.TechnologyCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TechnologyCategoryRepository extends JpaRepository<TechnologyCategory, Integer>{


}
