package org.school.diary.service;

import lombok.AllArgsConstructor;
import org.school.diary.dao.LessonHourRepository;
import org.school.diary.model.ClassGroup;
import org.school.diary.model.LessonHour;
import org.school.diary.model.LessonInterval;
import org.school.diary.model.Weekday;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LessonHourServiceImpl implements LessonHourService {

    private LessonHourRepository lessonHourRepository;


    @Override
    public void saveLessonHours(List<LessonHour> lessonHours) {
        lessonHourRepository.saveAll(lessonHours);
    }

    @Override
    public Map<LessonInterval, List<LessonHour>> findAllByClassGroup(ClassGroup classGroup) {
        return lessonHourRepository.findAllByClassGroup(classGroup).stream().collect(Collectors.groupingBy(LessonHour::getLessonInterval,TreeMap::new, Collectors.toList()));
    }

    @Override
    public List<LessonHour> findLessonHourByClassGroupAndWeekday(ClassGroup classGroup, Weekday weekday) {
        return lessonHourRepository.findLessonHourByClassGroupAndWeekday(classGroup,weekday);
    }
}
