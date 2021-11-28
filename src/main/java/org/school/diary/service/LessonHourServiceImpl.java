package org.school.diary.service;

import lombok.AllArgsConstructor;
import org.school.diary.dao.LessonHourRepository;
import org.school.diary.model.ClassGroup;
import org.school.diary.model.LessonHour;
import org.school.diary.model.Weekday;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LessonHourServiceImpl implements LessonHourService {

    private LessonHourRepository lessonHourRepository;


    @Override
    public void saveLessonHours(List<LessonHour> lessonHours) {
        lessonHourRepository.saveAll(lessonHours);
    }

    @Override
    public List<LessonHour> findAllByClassGroup(ClassGroup classGroup) {
        return lessonHourRepository.findAllByClassGroup(classGroup);
    }

    @Override
    public List<LessonHour> findLessonHourByClassGroupAndWeekday(ClassGroup classGroup, Weekday weekday) {
        return lessonHourRepository.findLessonHourByClassGroupAndWeekday(classGroup,weekday);
    }
}
