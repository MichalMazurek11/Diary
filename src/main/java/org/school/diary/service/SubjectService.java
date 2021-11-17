package org.school.diary.service;

import org.school.diary.model.Subject;

import java.util.List;
import java.util.Set;

public interface SubjectService {
    List<Subject> listAllSubject();

    void saveSubjects(Set<Subject> subjects);
}
