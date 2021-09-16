package com.revature.RevAssure.service;

import com.revature.RevAssure.model.Curriculum;
import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.repository.CurriculumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurriculumService
{
    private CurriculumRepository curriculumRepository;

    @Autowired
    public CurriculumService(CurriculumRepository curriculumRepository)
    {
        this.curriculumRepository = curriculumRepository;
    }

    /**
     * passes the curriculum to the repository to be saved
     * @param curriculum the curriculum to be saved
     * @return the saved curriculum
     */
    public Curriculum saveCurriculum(Curriculum curriculum)
    {
        return curriculumRepository.save(curriculum);
    }

    /**
     * passes the trainer to the repository to find its corresponding curricula
     * @param trainer the owner of the curricula
     * @return all curricula that belong to the trainer
     */
    public List<Curriculum> getAllCurriculaByTrainerId(RevUser trainer)
    {
        return curriculumRepository.findAllByRevUsers(trainer);
    }
}
