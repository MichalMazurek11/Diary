package org.school.diary.service;

import org.school.diary.model.ClassGroup;
import org.school.diary.model.LessonHour;
import org.school.diary.model.Weekday;
import org.school.diary.model.common.User;

import java.util.List;
import java.util.Set;

public interface LessonHourService {

    void saveLessonHours(List<LessonHour> lessonHours);

    List<LessonHour> findAllByClassGroup(ClassGroup classGroup); ;

    List<LessonHour> findLessonHourByClassGroupAndWeekday(ClassGroup classGroup, Weekday weekday);
}
