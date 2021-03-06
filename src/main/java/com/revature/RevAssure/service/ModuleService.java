package com.revature.RevAssure.service;

import com.revature.RevAssure.model.Module;
import com.revature.RevAssure.repository.ModuleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModuleService {

    private static final Logger log = LoggerFactory.getLogger(ModuleService.class);
    /**
     * Repository Layer for Modules
     */
    private final ModuleRepository repository;

    /**
     * Constructor for ModuleService Object
     * @param repo Repository Layer
     */
    @Autowired
    public ModuleService(ModuleRepository repo){
        this.repository = repo;
    }

    /**
     * Persisting a new Module to the database
     * @param module new Module to be persisted
     * @return The module after being persisted
     */
    public Module saveNewModule(Module module) {
        log.info("save module");
        return repository.save(module);
    }

    /**
     * Reading all existing modules from the database
     * and returning them in a List object
     * @return List containing all existing modules in the database
     */
    public List<Module> findAllModules() {
        //MORE HERE?
        log.info("find all modules");
        return repository.findAll();
    }

    /**
     * Update functionality for an existing module
     * @param newModule the locally updated version of the module
     * @return the updated version of the module after persisting
     */
    public Module saveExistingModule(Module newModule) {
        log.info("save existing module");
        return repository.save(newModule);
    }

    /**
     * Delete functionality of a module in the database
     * @param moduleId moduleId to be deleted
     */
    public void deleteModule(int moduleId) {
        log.info("deleting module");
        repository.delete(repository.getById(moduleId));
    }
}
