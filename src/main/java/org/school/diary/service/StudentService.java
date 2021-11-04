package org.school.diary.service;

import org.school.diary.model.ClassGroup;
import org.school.diary.model.Student;
import org.school.diary.model.Teacher;
import org.school.diary.model.User;

import java.util.List;

public interface StudentService {

    public void saveStudent(Student student);

    Student findByUser(User user);

    public List<Student> listStudents();

    Student findById(long id);
}
