package org.school.diary.service;

import org.school.diary.model.ClassGroup;
import org.school.diary.model.LessonHour;
import org.school.diary.model.LessonInterval;
import org.school.diary.model.Weekday;
import org.school.diary.model.common.Teacher;

import java.util.List;
import java.util.Map;

public interface LessonHourService {

    void saveLessonHours(List<LessonHour> lessonHours);

    Map<LessonInterval, List<LessonHour>> getLessonPlanForStudents(ClassGroup classGroup);
    Map<LessonInterval, List<LessonHour>> getLessonPlanForTeacher();

    List<LessonHour> findLessonHourByClassGroupAndWeekday(ClassGroup classGroup, Weekday weekday);

    List<LessonHour> findAllByTeacher(Teacher teacher);

    void deleteAll();

    LessonHour getLessonHourById(Integer lessonId);
}
