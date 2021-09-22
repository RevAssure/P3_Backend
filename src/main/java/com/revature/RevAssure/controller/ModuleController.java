package com.revature.RevAssure.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.RevAssure.dto.ModuleDTO;
import com.revature.RevAssure.model.Module;
import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.service.ModuleService;
import com.revature.RevAssure.service.RevUserService;
import com.revature.RevAssure.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/module")
public class ModuleController {

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
    public ResponseEntity<String> createModule(@RequestBody ModuleDTO moduledto) throws JsonProcessingException {
        RevUser revUser = JwtUtil.extractUser(revUserService);
        if(revUser.isTrainer()){
            Module module = moduleService.saveNewModule(moduledto.convertToEntity(revUser));
            String str = new ObjectMapper()
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(module);
            return ResponseEntity.ok().body(str);
        }
        else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    /**
     * Read functionality for retrieving all modules
     * @return a list of all modules in the database
     */
    // Read
    // TODO: consider associates might want to get modules only associated with themselves
    @GetMapping
    public List<Module> getAllModules(){
        return moduleService.findAllModules();
    }

    /**
     * Update functionality for modules
     * @param moduledto DTO object of module to be updated
     * @return updated and persisted module or sets bad status if the user is not a trainer
     */
    // Update
    @PutMapping
    public ResponseEntity<String> updateModules(@RequestBody ModuleDTO moduledto) throws JsonProcessingException {
        RevUser revUser = JwtUtil.extractUser(revUserService);
        if(revUser.isTrainer()){
            Module module = moduleService.saveExistingModule(moduledto.convertToEntity(revUser));
            String str = new ObjectMapper()
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(module);
            return ResponseEntity.ok().body(str);
        }
        else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    /**
     * Delete functionality for modules
     * (if the current user is a trainer)
     * @param moduleId the ID of the module to be deleted
     */
    // Delete
    @DeleteMapping("/{moduleId}")
    public void deleteModules(@PathVariable int moduleId){
        RevUser revUser = JwtUtil.extractUser(revUserService);
        if(revUser.isTrainer()){moduleService.deleteModule(moduleId);}
    }
}
