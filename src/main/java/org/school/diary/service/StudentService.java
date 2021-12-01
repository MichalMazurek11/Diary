package org.school.diary.service;

import org.school.diary.model.ClassGroup;
import org.school.diary.model.common.PersonRelatedWithSchool;
import org.school.diary.model.common.Student;
import org.school.diary.model.common.User;

import java.util.List;

public interface StudentService {

    public void saveStudent(Student student);

    Student findByUser(User user);

    public List<Student> listStudents();

    Student findById(long id);

    void save(PersonRelatedWithSchool student);

    List<Student> findStudentsByStudentsClassGroup(ClassGroup classGroup);

    List<Student> findByStudentsClassGroup(ClassGroup classGroup);
}
