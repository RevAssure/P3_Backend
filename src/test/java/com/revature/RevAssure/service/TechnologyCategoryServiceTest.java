package com.revature.RevAssure.service;


import com.revature.RevAssure.model.TechnologyCategory;

import com.revature.RevAssure.repository.TechnologyCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TechnologyCategoryServiceTest {
    @Mock
    private TechnologyCategoryRepository technologyCategoryRepository;

    private TechnologyCategoryService technologyCategoryService;

    private TechnologyCategory techCat;
    private List<TechnologyCategory> technologyCategoryList;


    @BeforeEach
    void setUp() {
        technologyCategoryService = new TechnologyCategoryService(technologyCategoryRepository);

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
    void getAllTechnologyCategories(){
        when(technologyCategoryRepository.findAll()).thenReturn(technologyCategoryList);
        assertEquals(technologyCategoryList, technologyCategoryService.getAll());
    }

    /**
     * Test if create works
     */
    @Test
    void createTest() {
        when(technologyCategoryRepository.save(techCat)).thenReturn(techCat);
        assertEquals(techCat, technologyCategoryService.create(techCat));
    }

}
