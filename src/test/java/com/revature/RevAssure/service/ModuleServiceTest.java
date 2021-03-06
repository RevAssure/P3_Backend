package com.revature.RevAssure.service;

import com.revature.RevAssure.model.Module;
import com.revature.RevAssure.model.TechnologyCategory;
import com.revature.RevAssure.model.Topic;
import com.revature.RevAssure.repository.ModuleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ModuleServiceTest {

    static TechnologyCategory tc;
    static TechnologyCategory tc2;
    static List<Topic> topics;
    static List<Topic> topics2;
    static Module module = new Module();
    static Module module2 = new Module();
    static List<Module> modules;

    private ModuleService service;

    @Mock
    private ModuleRepository repo;

    @BeforeEach
    public void setUp(){
        service = new ModuleService(repo);

        module.setId(1);
        module.setName("test");
        module.setDescription("test");
        module.setTechnologyCategory(tc);
        module.setTopics(topics);
        module2.setId(2);
        module2.setName("test2");
        module2.setDescription("test2");
        module2.setTechnologyCategory(tc2);
        module2.setTopics(topics2);
        modules = new ArrayList();
        modules.add(module);
        modules.add(module2);
    }

    @Test
    void saveNewModuleTest(){
        when(repo.save(module)).thenReturn(module);
        assertEquals(1,service.saveNewModule(module).getId());
    }

    @Test
    void findAllModulesTest(){
        when(repo.findAll()).thenReturn(modules);
        assertEquals(2,service.findAllModules().size());
        assertEquals(modules, service.findAllModules());
        assertEquals(module, service.findAllModules().get(0));
        assertEquals(module2,service.findAllModules().get(1));
    }

    @Test
    void updateExistingModuleTest(){
        module.setName("newTestName");
        when(repo.save(module)).thenReturn(module);
        assertEquals("newTestName",service.saveExistingModule(module).getName());
    }

    @Test
    void deleteExistingModuleTest(){
        doNothing().when(repo).delete(module);
        when(repo.getById(1)).thenReturn(module);
        service.deleteModule(1);
        verify(repo, times(1)).delete(module);
    }
}