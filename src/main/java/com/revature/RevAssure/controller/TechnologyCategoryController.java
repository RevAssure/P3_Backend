package com.revature.RevAssure.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.model.TechnologyCategory;
import com.revature.RevAssure.service.RevUserService;
import com.revature.RevAssure.service.TechnologyCategoryService;
import com.revature.RevAssure.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/technology_category")
public class TechnologyCategoryController {
    private TechnologyCategoryService technologyCategoryService;
    private RevUserService revUserService;

    /**
     * Constructor for TechnologyCategoryController
     *
     * @param technologyCategoryService is a technologyCategoryService object
     */
    @Autowired
    public TechnologyCategoryController(TechnologyCategoryService technologyCategoryService, RevUserService revUserService){
        this.revUserService = revUserService;
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

    /**
     * Create operation to persist a technology category into the database
     * @param technologyCategory
     * @return TechnologyCategory which was persisted
     */
    @PostMapping
    public ResponseEntity<String> createTechnologyCategory(@RequestBody TechnologyCategory technologyCategory) throws JsonProcessingException {
        RevUser revUser = JwtUtil.extractUser(revUserService);
        if(revUser.isTrainer()) {
            return ResponseEntity.ok().body(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(technologyCategoryService.create(technologyCategory)));
        }
        else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
