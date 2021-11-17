package org.school.diary.service;

import lombok.AllArgsConstructor;
import org.school.diary.dao.LessonHourRepository;
import org.school.diary.model.LessonHour;
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
}
