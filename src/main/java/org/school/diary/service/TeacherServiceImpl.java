package org.school.diary.service;

import lombok.RequiredArgsConstructor;
import org.school.diary.dao.SubjectRepository;
import org.school.diary.dao.TeacherRepository;
import org.school.diary.model.Subject;
import org.school.diary.model.common.PersonRelatedWithSchool;
import org.school.diary.model.common.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService{


    private final TeacherRepository teacherRepository;

    private final  SubjectService subjectService;

    private final SubjectRepository subjectRepository;

//    @Override
//    public void saveTeacher(Teacher teacher, LocalDate birthDate, Set<Subject> subjectSet) {
//        Set<Subject> matchesSubjects = subjectService.listAllSubject().stream().filter(subjectSet::contains).collect(Collectors.toSet());
//        Subject subject = subjectService.listAllSubject().get(0);
//        teacher.setDateBirth(birthDate);
//        teacher.setSubjects(Collections.singleton(subject));
//        teacherRepository.save(teacher);
//        //
//        subject.getTeachers().add(teacher);
//       // subject.setTeachers(subject.getTeachers().add(teacher));
//        subjectService.saveSubjects(Collections.singleton(subject));
//    }


    @Override
    public void saveTeacher(Teacher teacher) {
        teacherRepository.save(teacher);
    }

    @Override
    public List<Teacher> listTeachers() {
        return teacherRepository.findAll();
    }

    @Override
    public void save(PersonRelatedWithSchool teacher) {
        teacherRepository.save((Teacher) teacher);
    }

    @Override
    public void saveAllTeachers(Set<Teacher> teachers) {
        teacherRepository.saveAll(teachers);
    }

    @Override
    public List<Teacher> getTeachers() {
        return teacherRepository.findAll();
    }

    @Override
    public Teacher findByEmail(String email) {
        return teacherRepository.findByEmail(email);
    }

    @Override
    public void deleteTeacher(long teacherId) {
        teacherRepository.deleteById(teacherId);
    }

}
