package com.revature.RevAssure.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.RevAssure.dto.CurriculumDTO;
import com.revature.RevAssure.model.Curriculum;
import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.service.CurriculumService;
import com.revature.RevAssure.service.RevUserService;
import com.revature.RevAssure.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
     * stores a new curriculum in the database
     * @param curriculumdto the curriculum to be stored
     * @return the stored curriculum or sets bad status if the user is not a trainer
     */
    @PostMapping
    public ResponseEntity<String> createCurriculum(@RequestBody CurriculumDTO curriculumdto) throws JsonProcessingException {
        RevUser revUser = JwtUtil.extractUser(revUserService);
        if(revUser.isTrainer()) {
            Curriculum cur = curriculumService.saveCurriculum(curriculumdto.convertToEntity(revUser));
            String str = new ObjectMapper()
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(cur);
            return ResponseEntity.ok().body(str);
        }
        else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    // Read

    /**
     * returns list of Curricula from database by current user id
     * @return List of Curricula belonging to current user
     */
    @GetMapping
    public List<Curriculum> getAllCurriculaByTrainerId()
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
    public List<Curriculum> getAssignedCurriculaByAssociateId()
    {
        RevUser revUser = JwtUtil.extractUser(revUserService);
        return curriculumService.getAllCurriculaByUser(revUser);
    }

    // Update

    /**
     * update a current curriculum on the database
     * @param curriculumdto the curriculum to be updated
     * @return the updated curriculum or sets bad status if user is not a trainer
     */
    @PutMapping
    public ResponseEntity<String> updateCurriculum(@RequestBody CurriculumDTO curriculumdto) throws JsonProcessingException {
        RevUser revUser = JwtUtil.extractUser(revUserService);
        if(revUser.isTrainer()) {
            Curriculum cur = curriculumService.saveCurriculum(curriculumdto.convertToEntity(revUser));
            String str = new ObjectMapper()
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(cur);
            return ResponseEntity.ok().body(str);
        }
        else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    // Delete -- not in MVP
}
