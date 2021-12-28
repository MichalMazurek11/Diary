package org.school.diary.service;

import lombok.AllArgsConstructor;
import org.school.diary.dao.LessonHourRepository;
import org.school.diary.model.ClassGroup;
import org.school.diary.model.LessonHour;
import org.school.diary.model.LessonInterval;
import org.school.diary.model.Weekday;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LessonHourServiceImpl implements LessonHourService {

    private LessonHourRepository lessonHourRepository;
    private WeekdayService weekdayService;


    @Override
    public void saveLessonHours(List<LessonHour> lessonHours) {
        lessonHourRepository.saveAll(lessonHours);
    }

    @Override
    public Map<LessonInterval, List<LessonHour>> findAllByClassGroup(ClassGroup classGroup) {
        List<Weekday> weekdays = weekdayService.findAll();
        TreeMap<LessonInterval, List<LessonHour>> collect = lessonHourRepository.findAllByClassGroup(classGroup).stream().collect(Collectors.groupingBy(LessonHour::getLessonInterval, TreeMap::new, Collectors.toList()));
        for (List<LessonHour> lessonHours : collect.values()) {
            for (Weekday weekday : weekdays) {
                if (lessonHours.stream().noneMatch(lessonHour -> lessonHour.getWeekday().equals(weekday))){
                    LessonHour lessonHour = new LessonHour();
                    lessonHour.setWeekday(weekday);
                    lessonHours.add(lessonHour);
                }
                lessonHours.sort((o1, o2) -> Math.toIntExact(o1.getWeekday().getId() - o2.getWeekday().getId()));
            }
        }
      return collect;
    }

    @Override
    public List<LessonHour> findLessonHourByClassGroupAndWeekday(ClassGroup classGroup, Weekday weekday) {
        return lessonHourRepository.findLessonHourByClassGroupAndWeekday(classGroup,weekday);
    }
}
