package org.school.diary.service;

import lombok.AllArgsConstructor;
import org.school.diary.dao.LessonIntervalRepository;
import org.school.diary.model.ClassGroup;
import org.school.diary.model.LessonInterval;
import org.school.diary.model.Subject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LessonIntervalServiceImpl implements LessonIntervalService {

    private final ClassGroupService classGroupService;
    private final SubjectService subjectService;
    private final LessonIntervalRepository lessonIntervalRepository;


    public void createWeeklyPlan() {
        List<ClassGroup> classGroups = classGroupService.listClassGroups();
        List<Subject> subjects = subjectService.listAllSubject();


        //ENUM OD GODZIN ?

    }

    @Override
    public void saveLessonIntervals(List<LessonInterval> lessonIntervals) {
        lessonIntervalRepository.saveAll(lessonIntervals);
    }

    @Override
    public List<LessonInterval> findAllIntervals() {
        return lessonIntervalRepository.findAll();
    }
}

