package org.school.diary.service;


import lombok.AllArgsConstructor;
import org.school.diary.dao.SubjectRepository;
import org.school.diary.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SubjectServiceImpl implements SubjectService{


    SubjectRepository subjectRepository;

    @Override
    public List<Subject> listAllSubject() {
        return subjectRepository.findAll();
    }
}
