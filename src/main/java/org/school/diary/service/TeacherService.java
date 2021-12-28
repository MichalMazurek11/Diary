package org.school.diary.service;

import org.school.diary.dto.TeacherDTO;
import org.school.diary.model.Subject;
import org.school.diary.model.common.PersonRelatedWithSchool;
import org.school.diary.model.common.Student;
import org.school.diary.model.common.Teacher;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface TeacherService {

    void saveTeacher(Teacher teacher);

    List<Teacher> listTeachers();

    void save(PersonRelatedWithSchool Teacher);

    void saveAllTeachers(Set<Teacher> teachers);

    List<Teacher> getTeachers();

    Teacher findByEmail(String email);

    void deleteTeacher(long teacherId);

    Teacher findByLogin(String login);

    void saveTeacher(LocalDate birthDate, TeacherDTO teacherDTO, Set<Subject> subjectSet);
}

