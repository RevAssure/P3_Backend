package com.revature.RevAssure.controller;

import com.revature.RevAssure.dto.ModuleDTO;
import com.revature.RevAssure.model.Module;
import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.service.ModuleService;
import com.revature.RevAssure.service.RevUserService;
import com.revature.RevAssure.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

    public ModuleController(ModuleService moduleService, RevUserService revUserService){
        this.moduleService = moduleService;
        this.revUserService = revUserService;
    }

    // Create
    @PostMapping
    public Module createModule(@RequestBody ModuleDTO moduledto){
        RevUser revUser = JwtUtil.extractUser(revUserService);
        Module module = moduledto.convertToEntity(revUser);
        return moduleService.saveNewModule(module);
    }

    // Read
    // TODO: consider associates might want to get modules only associated with themselves
    @GetMapping
    public List<Module> getAllModules(){
        return moduleService.findAllModules();
    }

    // Update
    @PutMapping
    public Module updateModules(@RequestBody ModuleDTO moduledto){
        RevUser revUser = JwtUtil.extractUser(revUserService);
        return moduleService.saveExistingModule(moduledto.convertToEntity(revUser));
    }

    // Delete
    @DeleteMapping("/{moduleId}")
    public void deleteModules(@PathVariable int moduleId){
        RevUser revUser = JwtUtil.extractUser(revUserService);
        moduleService.deleteModule(moduleId);
    }
}
