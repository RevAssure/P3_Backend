package com.revature.RevAssure.service;

import com.revature.RevAssure.model.Curriculum;
import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.repository.CurriculumRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurriculumServiceTest
{
    private CurriculumService curriculumServiceTest;

    @Mock
    private CurriculumRepository mockCurriculumRepository;

    private Curriculum mockCurriculum;

    private List<Curriculum> mockCurriculumList;

    private RevUser mockTrainer;

    private RevUser mockAssociate;

    @BeforeEach
    void setUp()
    {
        curriculumServiceTest = new CurriculumService(mockCurriculumRepository);

        mockCurriculum = new Curriculum();
        mockCurriculum.setId(1);
        mockCurriculum.setName("test");
        mockCurriculum.setEvents(new ArrayList<>());

        mockTrainer = new RevUser();
        mockTrainer.setId(1);
        mockTrainer.setTrainer(true);
        mockAssociate = new RevUser();
        mockAssociate.setId(2);
        mockAssociate.setTrainer(false);
        List<RevUser> revUserList = new ArrayList<>();
        revUserList.add(mockTrainer);
        revUserList.add(mockAssociate);
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
    void createCurriculumReturnsNewCurriculum()
    {
        when(mockCurriculumRepository.findById(1)).thenReturn(Optional.empty());
        when(mockCurriculumRepository.save(mockCurriculum)).thenReturn(mockCurriculum);
        assertEquals(curriculumServiceTest.createCurriculum(mockCurriculum), mockCurriculum);
    }

    @Test
    void createCurriculumThrowsEntityExistsException()
    {
        when(mockCurriculumRepository.findById(1)).thenReturn(Optional.of(mockCurriculum));
        assertThrows(EntityExistsException.class, () -> curriculumServiceTest.createCurriculum(mockCurriculum));
    }

    @Test
    void updateCurriculumReturnsUpdatedCurriculum()
    {
        when(mockCurriculumRepository.findById(1)).thenReturn(Optional.of(mockCurriculum));
        when(mockCurriculumRepository.save(mockCurriculum)).thenReturn(mockCurriculum);
        assertEquals(curriculumServiceTest.updateCurriculum(mockCurriculum), mockCurriculum);
    }

    @Test
    void updateCurriculumThrowsEntityNotFoundException()
    {
        when(mockCurriculumRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> curriculumServiceTest.updateCurriculum(mockCurriculum));
    }

    /**
     * test getAllCurriculaByTrainerId works as intended
     */
    @Test
    void getAllCurriculaByTrainerIdReturnsListOfCurriculaCreatedByTrainer()
    {
        when(mockCurriculumRepository.findByTrainer(mockTrainer)).thenReturn(mockCurriculumList);
        assertEquals(curriculumServiceTest.getAllCurriculaByTrainer(mockTrainer), mockCurriculumList);
    }

    /**
     * test getAllCurriculaByTrainerId returns an empty list when trainer not found
     */
    @Test
    void getAllCurriculaByTrainerReturnsEmptyList()
    {
        when(mockCurriculumRepository.findByTrainer(mockTrainer)).thenReturn(new ArrayList<Curriculum>());
        assert(curriculumServiceTest.getAllCurriculaByTrainer(mockTrainer).isEmpty());
    }

    /**
     * test getAllCurriculaByUser works as intended
     */
    @Test
    void getAllCurriculaByUserReturnsListOfCurriculaAssignedToAssociate()
    {
        when(mockCurriculumRepository.findByRevUsers(mockAssociate)).thenReturn(mockCurriculumList);
        assertEquals(curriculumServiceTest.getAllCurriculaByUser(mockAssociate), mockCurriculumList);
    }

    /**
     * test getAllCurriculaByUser returns an empty list when associate not found
     */
    @Test
    void getAllCurriculaByUserReturnsEmptyList()
    {
        when(mockCurriculumRepository.findByRevUsers(mockAssociate)).thenReturn(new ArrayList<Curriculum>());
        assert(curriculumServiceTest.getAllCurriculaByUser(mockAssociate).isEmpty());
    }
}