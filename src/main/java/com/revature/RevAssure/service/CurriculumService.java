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

    public Curriculum saveCurriculum(Curriculum curriculum)
    {
        return curriculumRepository.save(curriculum);
    }

    public List<Curriculum> getAllCurriculaByTrainerId(RevUser trainer)
    {
        return curriculumRepository.findAllByRevUser(trainer);
    }
}
