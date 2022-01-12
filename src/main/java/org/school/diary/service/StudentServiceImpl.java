package org.school.diary.service;

import lombok.AllArgsConstructor;
import org.school.diary.dao.StudentRepository;
import org.school.diary.model.ClassGroup;
import org.school.diary.model.LessonHour;
import org.school.diary.model.common.Parent;
import org.school.diary.model.common.PersonRelatedWithSchool;
import org.school.diary.model.common.Student;
import org.school.diary.model.common.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {


    private final StudentRepository studentRepository;
    private final LessonHourService lessonHourService;

    @Override
    public void save(PersonRelatedWithSchool student) {
         studentRepository.save((Student) student);
    }

    @Override
    public List<Student> findStudentsByStudentsClassGroup(ClassGroup classGroup) {
        return studentRepository.findStudentsByStudentsClassGroup(classGroup);
    }

    @Override
    public List<Student> findByFirstLetter(String letter) {
        return studentRepository.findByFirstLetter(letter);
    }


    @Override
    public Set<Student> generateListOfStudentsBasedOnLesson(Integer lessonId) {
        LessonHour lessonHour = lessonHourService.getLessonHourById(lessonId);
        return lessonHour.getClassGroup().getStudents();
    }

    @Override
    public Student findByLogin(String login) {
        return studentRepository.findByLogin(login);
    }

    @Override
    public Student findByParent(Parent parent) {
        return studentRepository.findByParent(parent);
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

//    @Override
//    public Student findStudentById(UUID uuid) {
//        return studentRepository.findStudentById(uuid);
//    }

    @Override
    public List<Student> listStudents() {
        return studentRepository.findAll();
    }



//    @Override
//    public Student findOneByUuid(UUID studentUuid) {
//        return studentRepository.findOneByUuid(studentUuid);
//    }

    @Override
    public Student findById(long id) {
        return studentRepository.findById(id);
    }
}
