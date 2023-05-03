package com.example.volunteer.services;

import com.example.volunteer.entities.AcademicMethod;
import com.example.volunteer.entities.User;
import com.example.volunteer.modules.UpdateAcademicMethod;
import com.example.volunteer.repositories.AcademicMethodRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AcademicMethodService {

    private final AcademicMethodRepo academicMethodRepo;

    public void saveAcademicMethod(AcademicMethod AcademicMethod){
        academicMethodRepo.save(AcademicMethod);
    }

    public void deleteAcademicMethodById(Long id){
        AcademicMethod AcademicMethod = academicMethodRepo.getById(id);
        academicMethodRepo.delete(AcademicMethod);
    }


    public AcademicMethod getAcademicMethodById(Long id){
        return academicMethodRepo.getById(id);
    }

    public void updateAcademicMethod(UpdateAcademicMethod academicMethod){
        AcademicMethod toUpdate = academicMethodRepo.getById(academicMethod.getId());
        toUpdate.setDiscipline(academicMethod.getDiscipline());
        toUpdate.setDeadlines(academicMethod.getDeadlines());
        toUpdate.setNameWork(academicMethod.getNameWork());
        toUpdate.setInfoImplementation(academicMethod.getInfoImplementation());
        toUpdate.setComment(academicMethod.getComment());
        academicMethodRepo.save(toUpdate);
    }
}
