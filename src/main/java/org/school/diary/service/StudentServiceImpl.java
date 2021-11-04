package org.school.diary.service;

import org.school.diary.dao.StudentRepository;
import org.school.diary.dao.UserRepository;
import org.school.diary.model.Student;
import org.school.diary.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentRepository studentRepository;


    @Override
    public void saveStudent(Student student) {
         studentRepository.save(student);
    }

    @Override
    public Student findByUser(User user) {
        return studentRepository.findByUser(user);
    }

    @Override
    public List<Student> listStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student findById(long id) {
        return studentRepository.findById(id);
    }
}
