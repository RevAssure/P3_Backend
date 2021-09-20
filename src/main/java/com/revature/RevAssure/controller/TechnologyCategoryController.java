package com.revature.RevAssure.controller;

import com.revature.RevAssure.model.TechnologyCategory;
import com.revature.RevAssure.service.TechnologyCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/technology_category")
public class TechnologyCategoryController {
    private TechnologyCategoryService technologyCategoryService;

    /**
     * Constructor for TechnologyCategoryController
     *
     * @param technologyCategoryService is a technologyCategoryService object
     */
    @Autowired
    public TechnologyCategoryController(TechnologyCategoryService technologyCategoryService){
        this.technologyCategoryService = technologyCategoryService;
    }

    /**
     * Read operation for all TechnologyCategories objects
     * revUser retrieves the username from the current JWT
     * @return A list of all the technology categories
     */
    @GetMapping
    public List<TechnologyCategory> getTechnologyCategories() {
        return technologyCategoryService.getAll();
    }
}
