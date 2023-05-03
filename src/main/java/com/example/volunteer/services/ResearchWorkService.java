package com.example.volunteer.services;

import com.example.volunteer.entities.ResearchWork;
import com.example.volunteer.repositories.ResearchWorkRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ResearchWorkService {

    private final ResearchWorkRepo researchWorkRepo;

    public void saveResearchWork(ResearchWork researchWork){
        researchWorkRepo.save(researchWork);
    }

    public void deleteResearchWorkById(Long id){
        ResearchWork researchWork = researchWorkRepo.getById(id);
        researchWorkRepo.delete(researchWork);
    }


    public ResearchWork getResearchWorkById(Long id){
        return researchWorkRepo.getById(id);
    }

    public void updateResearchWork(ResearchWork researchWork){
        ResearchWork toUpdate = researchWorkRepo.getById(researchWork.getId());
        toUpdate.setTypeOfTheWork(researchWork.getTypeOfTheWork());
        if (researchWork.getTypeOfTheWork() == 1) {
            toUpdate.setNameOfTheArticle(researchWork.getNameOfTheArticle());
            toUpdate.setNameOfTheJournal(researchWork.getNameOfTheJournal());
            toUpdate.setCoAuthors(researchWork.getCoAuthors());
        } else if (researchWork.getTypeOfTheWork() == 2) {
            toUpdate.setConferenceName(researchWork.getConferenceName());
            toUpdate.setDate(researchWork.getDate());
            toUpdate.setTopicOfTheSpeech(researchWork.getTopicOfTheSpeech());
        } else if (researchWork.getTypeOfTheWork() == 3) {
            toUpdate.setNameOfTheArticle(researchWork.getNameOfTheArticle());
            toUpdate.setEvent(researchWork.getEvent());
            toUpdate.setStudents(researchWork.getStudents());
        } else {
            toUpdate.setNameOfTheWorks(researchWork.getNameOfTheWorks());
        }
        toUpdate.setDeadlines(researchWork.getDeadlines());
        toUpdate.setInformationOnImplementation(researchWork.getInformationOnImplementation());
        toUpdate.setResults(researchWork.getResults());
        toUpdate.setComments(researchWork.getComments());

        researchWorkRepo.save(toUpdate);
    }
}
