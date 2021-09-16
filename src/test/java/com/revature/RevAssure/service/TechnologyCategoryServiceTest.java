package com.revature.RevAssure.service;


import com.revature.RevAssure.model.TechnologyCategory;

import com.revature.RevAssure.repository.TechnologyCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TechnologyCategoryServiceTest {
    @MockBean
    private TechnologyCategoryRepository TechnologyCategoryRepository;

    @Autowired
    private TechnologyCategoryService technologyCategoryService;

    private TechnologyCategory techCat;
    private List<TechnologyCategory> technologyCategoryList;


    @BeforeEach
    void setUp() {

        techCat = new TechnologyCategory();
        techCat.setId(1);
        techCat.setName("TC Name");

        technologyCategoryList = new ArrayList<>();
        technologyCategoryList.add(techCat);

    }

    /**
     * Test if findAll works properly
     */
    @Test
    void getAllTopics(){
        when(TechnologyCategoryRepository.findAll()).thenReturn(technologyCategoryList);
        assertEquals(technologyCategoryList, technologyCategoryService.getAll());
    }

}
