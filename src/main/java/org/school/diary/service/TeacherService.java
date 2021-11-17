package org.school.diary.service;

import org.school.diary.model.common.PersonRelatedWithSchool;
import org.school.diary.model.common.Teacher;

import java.util.List;
import java.util.Set;

public interface TeacherService {


    public void addTeacher(Teacher teacher);

    public List<Teacher> listTeachers();

    void save(PersonRelatedWithSchool Teacher);

    void saveAllTeachers(Set<Teacher> teachers);

    List<Teacher> getTeachers();
}
