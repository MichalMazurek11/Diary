package org.school.diary.service;

import org.school.diary.model.Subject;
import org.school.diary.model.common.PersonRelatedWithSchool;
import org.school.diary.model.common.Student;
import org.school.diary.model.common.Teacher;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface TeacherService {


//    public void saveTeacher(Teacher teacher, LocalDate localDate, Set<Subject> subjectSet);

    public void saveTeacher(Teacher teacher);
//    public void addTeacher(Teacher teacher);

    public List<Teacher> listTeachers();

    void save(PersonRelatedWithSchool Teacher);

    void saveAllTeachers(Set<Teacher> teachers);

    List<Teacher> getTeachers();

    Teacher findByEmail(String email);

    public void deleteTeacher(long teacherId);

    Teacher findByLogin(String login);
}

