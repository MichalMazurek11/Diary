package org.school.diary.service;

import lombok.AllArgsConstructor;
import org.school.diary.model.ClassGroup;
import org.school.diary.model.Subject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LessonPlanServiceImpl implements LessonPlanService{

    private final ClassGroupService classGroupService;
    private final SubjectService subjectService;


    @Override
    public void createWeeklyPlan() {
        List<ClassGroup> classGroups = classGroupService.listClassGroups();

        List<Subject> subjects = subjectService.listAllSubject();

        //ENUM OD GODZIN ?

    }
}

