package org.school.diary.service;

import org.school.diary.model.ClassGroup;
import org.school.diary.model.Teacher;

import java.util.List;

public interface TeacherService {


    public void addTeacher(Teacher teacher);

    public List<Teacher> listTeachers();
}
