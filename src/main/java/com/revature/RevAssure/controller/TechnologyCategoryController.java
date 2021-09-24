package com.revature.RevAssure.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.model.TechnologyCategory;
import com.revature.RevAssure.service.RevUserService;
import com.revature.RevAssure.service.TechnologyCategoryService;
import com.revature.RevAssure.util.JwtUtil;
import org.postgresql.core.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/technology_category")
public class TechnologyCategoryController {
    private static final Logger log = LoggerFactory.getLogger(ConnectionFactory.class);

    private TechnologyCategoryService technologyCategoryService;
    private RevUserService revUserService;

    /**
     * Constructor for TechnologyCategoryController
     *
     * @param technologyCategoryService is a technologyCategoryService object
     */
    @Autowired
    public TechnologyCategoryController(TechnologyCategoryService technologyCategoryService, RevUserService revUserService){
        this.technologyCategoryService = technologyCategoryService;
        this.revUserService = revUserService;
    }

    /**
     * Read operation for all TechnologyCategories objects
     * revUser retrieves the username from the current JWT
     * @return A list of all the technology categories
     */
    @GetMapping
    public List<TechnologyCategory> getTechnologyCategories() {
        log.info("Getting all technology categories");
        return technologyCategoryService.getAll();
    }

    /**
     * Create operation to persist a technology category into the database
     * @param technologyCategory
     * @return TechnologyCategory which was persisted
     */
    @PostMapping
    public ResponseEntity<String> createTechnologyCategory(@RequestBody TechnologyCategory technologyCategory) {
        RevUser revUser = JwtUtil.extractUser(revUserService);
        try {
            if (revUser.isTrainer()) {
                log.info("Trainer is creating a new technology category");
                return ResponseEntity.ok().body(
                        new ObjectMapper().writeValueAsString(
                                technologyCategoryService.create(technologyCategory)));


            } else {
                log.warn("Associate is attempting to add a new technology category");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        } catch (Exception e) {
            log.error("Topic failed to be mapped as a JSON string",e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
