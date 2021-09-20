package com.revature.RevAssure.controller;

import com.revature.RevAssure.dto.CurriculumDTO;
import com.revature.RevAssure.model.Curriculum;
import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.service.CurriculumService;
import com.revature.RevAssure.service.RevUserService;
import com.revature.RevAssure.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
     * stores a new curriculum in the database
     * @param curriculumdto the curriculum to be stored
     * @return the stored curriculum or null if the user is not a trainer
     */
    @PostMapping
    public Curriculum createCurriculum(@RequestBody CurriculumDTO curriculumdto)
    {
        RevUser revUser = JwtUtil.extractUser(revUserService);
        if(revUser.isTrainer()) {
            Curriculum curriculum = curriculumdto.convertToEntity(revUser);
            return curriculumService.saveCurriculum(curriculum);
        }
        else{
            return null;
        }
    }

    // Read

    /**
     * returns list of Curricula from database by current user id
     * @return List of Curricula belonging to current user
     */
    @GetMapping
    public List<Curriculum> getAllCurriculaByCurrentUserId()
    {
        RevUser revUser = JwtUtil.extractUser(revUserService);
        return curriculumService.getAllCurriculaByTrainer(revUser);
    }

    /**
     * TODO: ask frontend if they want to narrow the view on getAllCurriculumByCurrentUserId()
     */

    /**
     * returns list of Curricula from database by current user id
     * @return List of Curricula current user is assigned to
     */
    @GetMapping("/assigned")
    public List<Curriculum> getAssignedCurriculaByCurrentUserId()
    {
        RevUser revUser = JwtUtil.extractUser(revUserService);
            return curriculumService.getAllCurriculaByUser(revUser);
    }

    // Update

    /**
     * update a current curriculum on the database
     * @param curriculumdto the curriculum to be updated
     * @return the updated curriculum or null if user is not a trainer
     */
    @PutMapping
    public Curriculum updateCurriculum(@RequestBody CurriculumDTO curriculumdto)
    {
        RevUser revUser = JwtUtil.extractUser(revUserService);
        if(revUser.isTrainer()) {
            Curriculum curriculum = curriculumdto.convertToEntity(revUser);
            return curriculumService.saveCurriculum(curriculum);
        }
        else{
            return null;
        }
    }

    // Delete -- not in MVP
}
