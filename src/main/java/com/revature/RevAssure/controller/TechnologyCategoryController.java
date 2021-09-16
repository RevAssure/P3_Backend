package com.revature.RevAssure.controller;

import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.model.TechnologyCategory;
import com.revature.RevAssure.service.RevUserService;
import com.revature.RevAssure.service.TechnologyCategoryService;
import com.revature.RevAssure.util.JwtUtil;
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
    private RevUserService revUserService;

    @Autowired
    public TechnologyCategoryController(TechnologyCategoryService technologyCategoryService, RevUserService revUserService){
        this.technologyCategoryService = technologyCategoryService;
        this.revUserService = revUserService;
    }

    @GetMapping
    public List<TechnologyCategory> getTechnologyCategories(){
        RevUser revUser = extractUser();
        // return technologyCategoryService.getAll();
        return null;
    }

    private RevUser extractUser(){
        String username = JwtUtil.extractUsername();
        // return revUserService.getRevUserByUsername(username);
        return null;
    }

}
