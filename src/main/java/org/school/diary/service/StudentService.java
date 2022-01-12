package org.school.diary.service;

import org.school.diary.model.ClassGroup;
import org.school.diary.model.common.Parent;
import org.school.diary.model.common.PersonRelatedWithSchool;
import org.school.diary.model.common.Student;
import org.school.diary.model.common.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface StudentService {

    public void saveStudent(Student student);

    Student findByUser(User user);

    public List<Student> listStudents();


    Student findById(long id);

    void save(PersonRelatedWithSchool student);

    List<Student> findStudentsByStudentsClassGroup(ClassGroup classGroup);

    List<Student> findByFirstLetter(@Param("firstLetter") String letter);

    Set<Student> generateListOfStudentsBasedOnLesson(Integer lessonId);

    Student findByLogin(String login);

    Student findByParent(Parent parent);
}
