package com.revature.RevAssure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.RevAssure.dto.ModuleDTO;
import com.revature.RevAssure.model.Module;
import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.service.ModuleService;
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
@RequestMapping("/module")
public class ModuleController {
    private static final Logger log = LoggerFactory.getLogger(ModuleController.class);

    @Autowired
    private ModuleService moduleService;
    @Autowired
    private RevUserService revUserService;

    /**
     * Constructor for ModuleController
     * @param moduleService service layer for modules
     * @param revUserService service layer for RevUsers
     */
    public ModuleController(ModuleService moduleService, RevUserService revUserService){
        this.moduleService = moduleService;
        this.revUserService = revUserService;
    }

    /**
     * Create functionality for modules
     * @param moduledto DTO object for module to be persisted
     * @return module after being persisted to the database or sets bad status if user is not a trainer
     */
    // Create
    @PostMapping
    public ResponseEntity<String> createModule(@RequestBody ModuleDTO moduledto) {
        RevUser revUser = JwtUtil.extractUser(revUserService);
        try {
            if (revUser.isTrainer()) {
                log.info("Trainer is creating a new module");
                return ResponseEntity.ok().body(
                        new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(
                                moduleService.saveNewModule(
                                        moduledto.convertToEntity(revUser))));
            } else {
                log.warn("Associate is attempting to create new module");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        } catch (Exception e){
            log.error("Module failed to be mapped as a JSON string",e);
            return ResponseEntity.internalServerError().build();
        }
    }


    // Read
    // TODO: consider associates might want to get modules only associated with themselves

    /**
     * Read functionality for retrieving all modules
     * @return a list of all modules in the database
     */
    @GetMapping
    public List<Module> getAllModules(){
        log.info("Getting all modules");
        return moduleService.findAllModules();
    }

    /**
     * Update functionality for modules
     * @param moduledto DTO object of module to be updated
     * @return updated and persisted module or sets bad status if the user is not a trainer
     */
    // Update
    @PutMapping
    public ResponseEntity<String> updateModules(@RequestBody ModuleDTO moduledto) {
        RevUser revUser = JwtUtil.extractUser(revUserService);
        try {
            if (revUser.isTrainer()) {
                log.info("Trainer is updating their module");
                return ResponseEntity.ok().body(
                        new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(
                                moduleService.saveExistingModule(
                                        moduledto.convertToEntity(revUser))));
            } else {
                log.warn("Associate is attempting to update a module");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        } catch (Exception e){
            log.error("Module failed to be mapped as a JSON string",e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // Delete

    /**
     * Endpoint available for deleting an existing module
     * @param moduleId : Module id of module that is to be deleted
     * @return ResponseEntity that will return status code
     */
    @DeleteMapping("/{moduleId}")
    public ResponseEntity<String> deleteModules(@PathVariable int moduleId){
        RevUser revUser = JwtUtil.extractUser(revUserService);
        if(revUser.isTrainer()) {
            log.info("Trainer is deleting one of their modules");
            moduleService.deleteModule(moduleId);
            return ResponseEntity.ok().build();
        }
        else {
            log.warn("Associate is attempting to delete a module");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}