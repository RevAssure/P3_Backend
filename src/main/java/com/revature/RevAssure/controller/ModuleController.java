package com.revature.RevAssure.controller;

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
    public Module createModule(@RequestBody Module module){
        RevUser revUser = extractUser();
        // return moduleService.saveModule(module);
        return null;
    }
    // Read

    // TODO: consider associates might want to get modules only associated with themselves
    @GetMapping
    public List<Module> getAllModules(){
        RevUser revUser = extractUser(); // might be an instance we don't need to extract user
        // return moduleService.getAllModules();
        return null;
    }

    // Update

    @PutMapping
    public Module updateModules(@RequestBody Module module){
        RevUser revUser = extractUser();
        // return moduleService.saveModule(module);
        return null;
    }


    // Delete
    @DeleteMapping("/{moduleId}")
    public Module deleteModules(@PathVariable int moduleId){
        RevUser revUser = extractUser();
        // return moduleService.deleteModule(moduleId);
        return null;
    }

    private RevUser extractUser(){
        String username = JwtUtil.extractUsername();
        // return revUserService.getRevUserByUsername(username);
        return null;
    }
}
