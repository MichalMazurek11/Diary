package org.school.diary.service;


import lombok.AllArgsConstructor;
import org.school.diary.dao.SubjectRepository;
import org.school.diary.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class SubjectServiceImpl implements SubjectService{

    @Autowired
    SubjectRepository subjectRepository;

    @Override
    public List<Subject> listAllSubject() {
        return subjectRepository.findAll();
    }

    @Override
    public void saveSubjects(Set<Subject> subjects) {
        subjectRepository.saveAll(subjects);
    }

    @Override
    public Subject findById(long id) {
        return subjectRepository.findById(id);
    }

    @Override
    public Subject findByName(String name) {
        return subjectRepository.findByName(name);
    }
}
