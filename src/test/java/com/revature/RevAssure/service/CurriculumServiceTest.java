package com.revature.RevAssure.service;

import com.revature.RevAssure.model.Curriculum;
import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.repository.CurriculumRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class CurriculumServiceTest
{
    @Autowired
    private CurriculumService curriculumServiceTest;

    @MockBean
    private CurriculumRepository mockCurriculumRepository;

    private Curriculum mockCurriculum;

    private List<Curriculum> mockCurriculumList;

    private RevUser mockTrainer;

    @BeforeEach
    void setUp()
    {
        mockCurriculum = new Curriculum();
        mockCurriculum.setId(1);
        mockCurriculum.setName("test");
        mockCurriculum.setEvents(new ArrayList<>());

        mockTrainer = new RevUser();
        mockTrainer.setId(1);
        mockTrainer.setTrainer(true);
        List<RevUser> revUserList = new ArrayList<>();
        revUserList.add(mockTrainer);
        mockCurriculum.setRevUsers(revUserList);

        mockCurriculumList = new ArrayList<>();
        mockCurriculumList.add(mockCurriculum);
    }

    @AfterEach
    void tearDown()
    {
    }

    /**
     * test saveCurriculum works as intended
     */
    @Test
    void saveCurriculumReturnsNewOrUpdatedCurriculum()
    {
        when(mockCurriculumRepository.save(mockCurriculum)).thenReturn(mockCurriculum);
        assertEquals(curriculumServiceTest.saveCurriculum(mockCurriculum), mockCurriculum);
    }

    /**
     * test getAllCurriculaByTrainerId works as intended
     */
    @Test
    void getAllCurriculaByTrainerIdReturnsListOfCurriculaCreatedByTrainer()
    {
        when(mockCurriculumRepository.findAllByRevUsers(mockTrainer)).thenReturn(mockCurriculumList);
        assertEquals(curriculumServiceTest.getAllCurriculaByTrainerId(mockTrainer), mockCurriculumList);
    }
}