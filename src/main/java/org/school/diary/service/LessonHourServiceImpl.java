package org.school.diary.service;

import lombok.AllArgsConstructor;
import org.school.diary.NotFoundException;
import org.school.diary.dao.LessonHourRepository;
import org.school.diary.model.*;
import org.school.diary.model.common.Teacher;
import org.school.diary.model.enums.StatusTeacher;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static org.school.diary.model.enums.ClassRoomDuty.PE;

@Service
@AllArgsConstructor
public class LessonHourServiceImpl implements LessonHourService {

    private LessonHourRepository lessonHourRepository;
    private WeekdayService weekdayService;
    private TeacherService teacherService;
    private SubjectService subjectService;
    private ClassGroupService classGroupService;
    private LessonIntervalService lessonIntervalService;
    private ClassRoomService classRoomService;

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
        return lessonHourRepository.findLessonHourByClassGroupAndWeekday(classGroup, weekday);
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

    @Override
    public LessonHour findAllByid(int id) {
        return lessonHourRepository.findAllByid(id);
    }

    @Override
    public void createLessonPlan() {

        final int qtyOfLessonsInTheSameTime = 6;
        List<LessonHour> lessonHours = new ArrayList<>();
        List<Subject> subjects = subjectService.listAllSubject();
        List<ClassGroup> classGroups = classGroupService.listClassGroups();
        List<LessonInterval> allIntervals = lessonIntervalService.findAllIntervals();
        List<Weekday> weekdays = weekdayService.findAll();
        List<ClassRoom> classRooms = classRoomService.getAll();
        Random random = new Random();
        List<ClassRoom> peClasses = new ArrayList<>();

        for (int i = 0; i < weekdays.size(); i++) {
            final Weekday weekday = weekdays.get(i);
            for (int j = 0; j < allIntervals.size(); j++) {
                Set<ClassRoom> classRoomsAtTheSameTime = new HashSet<>();
                int peClassesIdx = 0;
                for (int k = 0; k < qtyOfLessonsInTheSameTime; k++) {
                    int idxOfClassRoom = random.nextInt(classRooms.size());
                    ClassRoom chosenClassRoom = classRooms.get(idxOfClassRoom);
                    if (chosenClassRoom.getClassRoomDuty() == PE) {
                        if (peClasses.size() == 2) {
                            chosenClassRoom = peClasses.get(peClassesIdx++);
                        }
                        peClasses.add(chosenClassRoom);
                    }
                    if (classRoomsAtTheSameTime.contains(chosenClassRoom)) {
                        k--;
                        continue;
                    }
                    classRoomsAtTheSameTime.add(chosenClassRoom);

                    int factorOfBreak = random.nextInt(qtyOfLessonsInTheSameTime) + 1;
                    if (factorOfBreak == qtyOfLessonsInTheSameTime) {
                        continue;
                    }
                    Subject subject = subjects.get(random.nextInt(subjects.size() - 1));
                    if (!subject.getName().equals("wychowanie fizyczne") && chosenClassRoom.getClassRoomDuty() == PE) {
                        k--;
                    }
                    List<Teacher> teachers = subject.getTeachers();
                    //sprawdzenie statusu nauyczyciela == good ?
                    teachers =  teachers.stream().filter(teacher -> teacher.getStatusTeacher() == StatusTeacher.AKTYWNY).collect(Collectors.toList());
                    Teacher teacher = teachers.get(random.nextInt(teachers.size()));
                    LessonInterval lessonInterval = allIntervals.get(j);

                    ClassGroup classGroup = classGroups.get(random.nextInt(classGroups.size()));
                    boolean isTeacherAndStudentsGroupAreDuplicatedForAnyLessonHour = lessonHours.stream().anyMatch(lessonHour ->
                            lessonHour.getLessonInterval().equals(lessonInterval) && lessonHour.getWeekday().equals(weekday) &&
                                    (lessonHour.getTeacher().equals(teacher) || lessonHour.getClassGroup().equals(classGroup))
                    );
                    if (isTeacherAndStudentsGroupAreDuplicatedForAnyLessonHour) {
                        k--;
                        continue;
                    }
                    lessonHours.add(new LessonHour(subject, lessonInterval, classGroup, teacher, weekdays.get(i), chosenClassRoom));
                }
            }
        }

        lessonHourRepository.saveAll(lessonHours);
    }


    private TreeMap<LessonInterval, List<LessonHour>> upgradeLessonPlan(List<LessonHour> lessonHoursFromDb) {
        List<Weekday> weekdays = weekdayService.findAll();
        TreeMap<LessonInterval, List<LessonHour>> lessonHoursMap = lessonHoursFromDb.stream().collect(Collectors.groupingBy(LessonHour::getLessonInterval, TreeMap::new, Collectors.toList()));
        for (List<LessonHour> lessonHours : lessonHoursMap.values()) {
            for (Weekday weekday : weekdays) {
                if (lessonHours.stream().noneMatch(lessonHour -> lessonHour.getWeekday().equals(weekday))) {
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
