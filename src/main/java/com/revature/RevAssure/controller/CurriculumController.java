package com.revature.RevAssure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.RevAssure.dto.CurriculumDTO;
import com.revature.RevAssure.model.Curriculum;
import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.service.CurriculumService;
import com.revature.RevAssure.service.RevUserService;
import com.revature.RevAssure.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/curriculum")
public class CurriculumController {
    private static final Logger log = LoggerFactory.getLogger(CurriculumController.class);

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
    public ResponseEntity<String> createCurriculum(@RequestBody CurriculumDTO curriculumdto) {
        RevUser revUser = JwtUtil.extractUser(revUserService);
        try {
            if (revUser.isTrainer()) {
                log.info("Trainer is making new curriculum.");
                return ResponseEntity.ok().body(
                        new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(
                                curriculumService.saveCurriculum(
                                        curriculumdto.convertToEntity(revUser))));
            } else {
                log.warn("Associate attempted to create curriculum.");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        } catch (Exception e) {
            log.error("Curriculum failed to be mapped as a JSON string",e);
            return ResponseEntity.internalServerError().build();
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
        log.info("Trainer is getting all curriculum they are an owner of.");
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
        log.info("User is getting all curriculum they are a participant in");
        return curriculumService.getAllCurriculaByUser(revUser);
    }

    // Update

    /**
     * update a current curriculum on the database
     * @param curriculumdto the curriculum to be updated
     * @return the updated curriculum or sets bad status if user is not a trainer
     */
    @PutMapping
    public ResponseEntity<String> updateCurriculum(@RequestBody CurriculumDTO curriculumdto) {
        RevUser revUser = JwtUtil.extractUser(revUserService);
        try {
            if (revUser.isTrainer()) {
                log.info("Trainer is updating their curriculum.");
                return ResponseEntity.ok().body(
                        new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(
                                curriculumService.saveCurriculum(
                                        curriculumdto.convertToEntity(revUser))));
            } else {
                log.warn("Associate attempting to update curriculum.");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        } catch (Exception e) {
            log.error("Curriculum failed to be mapped as a JSON string",e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // Delete -- not in MVP
}
