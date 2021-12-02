package org.school.diary.service;

import org.school.diary.dao.StudentRepository;
import org.school.diary.model.ClassGroup;
import org.school.diary.model.common.PersonRelatedWithSchool;
import org.school.diary.model.common.Student;
import org.school.diary.model.common.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentRepository studentRepository;


    @Override
    public void save(PersonRelatedWithSchool student) {
         studentRepository.save((Student) student);
    }

    @Override
    public List<Student> findStudentsByStudentsClassGroup(ClassGroup classGroup) {
        return studentRepository.findStudentsByStudentsClassGroup(classGroup);
    }

    @Override
    public List<Student> findByStudentsClassGroup(ClassGroup classGroup) {
        return studentRepository.findByStudentsClassGroup(classGroup);
    }

    @Override
    public void saveStudent(Student student) {
        studentRepository.save(student);
    }

    @Override
    public Student findByUser(User user) {
//        return studentRepository.findByUser(user);
    return null;
    }

    @Override
    public Student findStudentById(UUID uuid) {
        return studentRepository.findStudentById(uuid);
    }

    @Override
    public List<Student> listStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student findOneByUuid(UUID studentUuid) {
        return studentRepository.findOneByUuid(studentUuid);
    }

    @Override
    public Student findById(long id) {
        return studentRepository.findById(id);
    }
}
