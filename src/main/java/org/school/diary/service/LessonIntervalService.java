package org.school.diary.service;

import org.school.diary.model.LessonInterval;

import java.util.List;

public interface LessonIntervalService {

    void saveLessonIntervals(List<LessonInterval> lessonIntervals);

    List<LessonInterval> findAllIntervals();
}
