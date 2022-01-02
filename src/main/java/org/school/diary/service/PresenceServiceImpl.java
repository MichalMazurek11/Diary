package org.school.diary.service;

import lombok.RequiredArgsConstructor;
import org.school.diary.dao.PresenceRepository;
import org.school.diary.model.*;
import org.school.diary.model.common.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class PresenceServiceImpl implements PresenceService {


    private final PresenceRepository presenceRepository;
    private final StudentService studentService;
    private final LessonHourService lessonHourService;
    private final LessonIntervalService lessonIntervalService;

    @Override
    public List<Presence> findAll() {
        return presenceRepository.findAll();
    }

    @Override
    public List<Presence> generateEmptyPresencesForStudentsGroup(Integer lessonId) {
        LessonHour lessonHour = lessonHourService.getLessonHourById(lessonId);
        ClassGroup classGroup = lessonHour.getClassGroup();
        LocalDateTime now = LocalDateTime.of(2021,12,31,12,0);
        LocalTime nowTime = now.toLocalTime();
        LessonInterval currentLessonInterval = lessonIntervalService.findAllIntervals().stream().filter(interval -> interval.getBeginLesson().compareTo(nowTime) < 0 && interval.getEndLesson().compareTo(nowTime) > 0).findFirst().orElseThrow();


        List<Presence> presences = presenceRepository.findByStudentStudentsClassGroupIdAndDateOfPresenceEquals(classGroup.getId(), currentLessonInterval.getBeginLesson().atDate(LocalDate.now()));
        if (presences.size()==0){
            return generateEmptyPresences(lessonHour,classGroup,currentLessonInterval);
        }
        return presences;
    }

    @Override
    public void saveAll(List<Presence> presences) {
        presenceRepository.saveAll(presences);
    }

    private List<Presence> generateEmptyPresences(LessonHour lessonHour, ClassGroup classGroup, LessonInterval currentLessonInterval) {
        List<Presence> presences = new ArrayList<>();
        StatePresence state = StatePresence.NIEOBECNY;
        LocalDateTime currentLessonDateTime = currentLessonInterval.getEndLesson().atDate(LocalDate.now());
        classGroup.getStudents().forEach(student -> presences.add(new Presence(state,currentLessonDateTime,student, lessonHour)));
        return presences;
    }

}
