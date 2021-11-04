package org.school.diary.service;

import org.school.diary.dao.TeacherRepository;
import org.school.diary.model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService{

    @Autowired
    TeacherRepository teacherRepository;

    @Override
    public void addTeacher(Teacher teacher) {
        teacherRepository.save(teacher);
    }

    @Override
    public List<Teacher> listTeachers() {
        return teacherRepository.findAll();
    }
}
