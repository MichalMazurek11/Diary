package org.school.diary.service;

import org.school.diary.model.common.PersonRelatedWithSchool;
import org.school.diary.model.common.Teacher;

import java.util.List;

public interface TeacherService {


    public void addTeacher(Teacher teacher);

    public List<Teacher> listTeachers();

    void save(PersonRelatedWithSchool Teacher);
}
