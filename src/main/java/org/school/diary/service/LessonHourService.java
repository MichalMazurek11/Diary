package org.school.diary.service;

import org.school.diary.model.ClassGroup;
import org.school.diary.model.LessonHour;

import java.util.List;
import java.util.Set;

public interface LessonHourService {

    void saveLessonHours(List<LessonHour> lessonHours);
}
