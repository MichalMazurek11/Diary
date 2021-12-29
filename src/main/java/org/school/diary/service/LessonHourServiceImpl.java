package org.school.diary.service;

import lombok.AllArgsConstructor;
import org.school.diary.NotFoundException;
import org.school.diary.dao.LessonHourRepository;
import org.school.diary.model.ClassGroup;
import org.school.diary.model.LessonHour;
import org.school.diary.model.LessonInterval;
import org.school.diary.model.Weekday;
import org.school.diary.model.common.Teacher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LessonHourServiceImpl implements LessonHourService {

    private LessonHourRepository lessonHourRepository;
    private WeekdayService weekdayService;
    private TeacherService teacherService;


    @Override
    public void saveLessonHours(List<LessonHour> lessonHours) {
        lessonHourRepository.saveAll(lessonHours);
    }

    @Override
    public Map<LessonInterval, List<LessonHour>> getLessonPlanForStudents(ClassGroup classGroup) {
        List<LessonHour> allByClassGroup = lessonHourRepository.findAllByClassGroup(classGroup);
        return upgradeLessonPlan(allByClassGroup);
    }

    @Override
    public Map<LessonInterval, List<LessonHour>> getLessonPlanForTeacher() {
        Teacher teacher = teacherService.findTeacher();
        List<LessonHour> teachersLesson = lessonHourRepository.findAll().stream().filter(lessonHour -> lessonHour.getTeacher().getId().equals(teacher.getId())).collect(Collectors.toList());
        return upgradeLessonPlan(teachersLesson);
    }

    @Override
    public List<LessonHour> findLessonHourByClassGroupAndWeekday(ClassGroup classGroup, Weekday weekday) {
        return lessonHourRepository.findLessonHourByClassGroupAndWeekday(classGroup,weekday);
    }

    @Override
    public List<LessonHour> findAllByTeacher(Teacher teacher) {
        return lessonHourRepository.findAllByTeacher(teacher);
    }

    @Override
    public void deleteAll() {
        lessonHourRepository.deleteAll();
    }

    @Override
    public LessonHour getLessonHourById(Integer lessonId) {
        return lessonHourRepository.findById(lessonId).orElseThrow(() -> new NotFoundException("Given lesson not exists"));
    }

    private TreeMap<LessonInterval, List<LessonHour>> upgradeLessonPlan(List<LessonHour> lessonHoursFromDb){
        List<Weekday> weekdays = weekdayService.findAll();
        TreeMap<LessonInterval, List<LessonHour>> lessonHoursMap = lessonHoursFromDb.stream().collect(Collectors.groupingBy(LessonHour::getLessonInterval, TreeMap::new, Collectors.toList()));
        for (List<LessonHour> lessonHours : lessonHoursMap.values()) {
            for (Weekday weekday : weekdays) {
                if (lessonHours.stream().noneMatch(lessonHour -> lessonHour.getWeekday().equals(weekday))){
                    LessonHour lessonHour = new LessonHour();
                    lessonHour.setWeekday(weekday);
                    lessonHours.add(lessonHour);
                }
                lessonHours.sort((o1, o2) -> Math.toIntExact(o1.getWeekday().getId() - o2.getWeekday().getId()));
            }
        }
        return lessonHoursMap;
    }
}
