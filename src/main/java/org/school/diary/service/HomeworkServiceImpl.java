package org.school.diary.service;

import lombok.AllArgsConstructor;
import org.school.diary.dao.HomeworkRepository;
import org.school.diary.model.ClassGroup;
import org.school.diary.model.Homework;
import org.school.diary.model.common.Teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeworkServiceImpl implements HomeworkService {

    @Autowired
    HomeworkRepository homeworkRepository;

    @Override
    public void save(Homework homework) {
        homeworkRepository.save(homework);
    }

    @Override
    public List<Homework> findHomeworkByHomeworksClassGroup(ClassGroup classGroup) {
        return homeworkRepository.findHomeworkByHomeworksClassGroup(classGroup);
    }

    @Override
    public List<Homework> findHomeworkByHomeworkTeacher(Teacher teacher) {
        return homeworkRepository.findHomeworkByHomeworkTeacher(teacher);
    }

    @Override
    public Homework findById(long id) {
        return homeworkRepository.findById(id);
    }
}
