package com.revature.RevAssure.service;

import com.revature.RevAssure.model.Curriculum;
import com.revature.RevAssure.model.RevUser;
import com.revature.RevAssure.repository.CurriculumRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurriculumService
{
    private static final Logger log = LoggerFactory.getLogger(CurriculumService.class);
    private final CurriculumRepository curriculumRepository;

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
        log.info("Saving Curriculum");
        return curriculumRepository.save(curriculum);
    }

    /**
     * passes the trainer to the repository to find its corresponding curricula
     * @param trainer the owner of the curricula
     * @return all curricula that belong to the trainer
     */
    public List<Curriculum> getAllCurriculaByTrainer(RevUser trainer)
    {
        log.info("Get list of Curriculum by trainer id");
        return curriculumRepository.findByTrainer(trainer);
    }

    public List<Curriculum> getAllCurriculaByUser(RevUser revUser) {
        log.info("get all curriculum by associate user");
        return curriculumRepository.findByRevUsers(revUser);
    }
}
