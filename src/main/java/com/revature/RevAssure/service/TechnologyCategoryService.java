package com.revature.RevAssure.service;

import com.revature.RevAssure.model.TechnologyCategory;
import com.revature.RevAssure.repository.TechnologyCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Layer for TechnologyCategory
 */
@Service
public class TechnologyCategoryService {
    private final TechnologyCategoryRepository techCatRepo;


    @Autowired
    public TechnologyCategoryService(TechnologyCategoryRepository tcr){
        techCatRepo = tcr;
    }

    /**
     * Get a lists of all TechnologyCategory objects in the database
     * @return List of TechnologyCategory Objects
     */
    public List<TechnologyCategory> getAll(){
        return techCatRepo.findAll();
    }

}
