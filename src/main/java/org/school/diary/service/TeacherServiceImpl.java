package org.school.diary.service;

import lombok.RequiredArgsConstructor;
import org.school.diary.dao.TeacherRepository;
import org.school.diary.model.common.PersonRelatedWithSchool;
import org.school.diary.model.common.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService{


    private final TeacherRepository teacherRepository;


    @Override
    public void addTeacher(Teacher teacher) {

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

}
