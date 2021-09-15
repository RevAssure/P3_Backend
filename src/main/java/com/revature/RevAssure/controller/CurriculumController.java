package com.revature.RevAssure.controller;

import com.revature.RevAssure.model.Curriculum;
import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.service.CurriculumService;
import com.revature.RevAssure.service.RevUserService;
import com.revature.RevAssure.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/curriculum")
public class CurriculumController {

    @Autowired
    private final CurriculumService curriculumService;

    @Autowired
    private final RevUserService revUserService;

    public CurriculumController(CurriculumService curriculumService, RevUserService revUserService){
        this.curriculumService = curriculumService;
        this.revUserService = revUserService;
    }

    // Create

    /**
     * Create a new curriculum when given curriculum object
     * @param curriculum curriculum object
     * @return curriculum object
     */
    @PostMapping
    public Curriculum createCurriculum(@RequestBody Curriculum curriculum)
    {
        RevUser revUser = extractUser();
        return curriculumService.saveCurriculum(curriculum);
    }

    // Read

    /**
     * returns list of Curriculum objects based on user id from the jwt
     * @return List of Curriculum objects
     */
    @GetMapping
    public List<Curriculum> getAllCurriculaByCurrentUserId()
    {
        RevUser revUser = extractUser();
        return curriculumService.getAllCurriculaByTrainerId(revUser);
    }

    /**
     * TODO: ask frontend if they want to narrow the view on getAllCurriculumByCurrentUserId()
     */

    // Update

    /**
     * update a current curriculum when given curriculum object
     * @param curriculum curriculum object
     * @return curriculum object
     */
    @PutMapping
    public Curriculum updateCurriculum(@RequestBody Curriculum curriculum)
    {
        RevUser revUser = extractUser();
        // TODO: make sure it is trainer updating and not associate/general user
        return curriculumService.saveCurriculum(curriculum);
    }

    // Delete -- not in MVP


    private RevUser extractUser(){
        String username = JwtUtil.extractUsername();
        // return revUserService.getRevUserByUsername(username);
        return null;
    }

}
