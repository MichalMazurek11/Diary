package org.school.diary.service;

import org.school.diary.model.Mark;
import org.school.diary.model.Subject;
import org.school.diary.model.common.Student;

import java.util.List;
import java.util.Set;

public interface SubjectService {
    List<Subject> listAllSubject();

    void saveSubjects(Set<Subject> subjects);

    Subject findById(long id);

    Subject findByName(String name);
}
