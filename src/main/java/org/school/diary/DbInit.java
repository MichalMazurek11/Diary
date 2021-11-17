package org.school.diary;

import lombok.RequiredArgsConstructor;
import org.school.diary.model.*;
import org.school.diary.model.common.Director;
import org.school.diary.model.common.PersonRelatedWithSchool;
import org.school.diary.model.common.Teacher;
import org.school.diary.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class DbInit implements CommandLineRunner {

    private final DirectorService directorService;
    private final ClassGroupService classGroupService;
    private final SubjectService subjectService;
    private final TeacherService teacherService;
    private final LessonIntervalService lessonIntervalService;
    private final WeekdayService weekdayService;
    private final LessonHourService lessonHourService;

    @Override
    public void run(String... args) throws Exception {
        createStudents();
        createClassGroups(Arrays.asList("1A", "2B", "3C", "4B", "2C", "4G", "2D"));
        createTeachers();
        createSubjects();
        createLessonIntervals();
        createWeekdays();
        createLessonPlan();
        createDirector();

    }

    private void createWeekdays() {
        weekdayService.saveAllWeekdays(Arrays.asList(
                new Weekday(1, "poniedziałek"),
                new Weekday(2, "wtorek"),
                new Weekday(3, "środa"),
                new Weekday(4, "czwartek"),
                new Weekday(5, "piątek")
        ));
    }

    private void createLessonIntervals() {
        final int lessonTime = 45;
        final int[] breaks = new int[]{5, 10, 20};
        final Random random = new Random();
        List<LessonInterval> lessonIntervals = new ArrayList<>();
        LocalTime tempTime = LocalTime.of(7, 45);
        for (int i = 1; i < 10; i++) {
            lessonIntervals.add(new LessonInterval(i, tempTime, tempTime.plusMinutes(lessonTime)));
            tempTime = tempTime.plusMinutes(lessonTime);
            tempTime = tempTime.plusMinutes(breaks[random.nextInt(breaks.length)]);
        }

        lessonIntervalService.saveLessonIntervals(lessonIntervals);

    }

    private void createTeachers() {
        HashMap<String, String> firstNameAndLastName = new HashMap<>();
        firstNameAndLastName.put("Maciej", "Wasilewski");
        firstNameAndLastName.put("Kewin", "Kozłowski");
        firstNameAndLastName.put("Gniewomir", "Czarnecki");
        firstNameAndLastName.put("Ludwik", "Sobczak");
        firstNameAndLastName.put("Rafał", "Kaźmierczak");
        firstNameAndLastName.put("Gustaw", "Kowalski");
        firstNameAndLastName.put("Marek", "Laskowska");
        firstNameAndLastName.put("Juliusz", "Krupa");
        firstNameAndLastName.put("Piotr", "Mróz");
        firstNameAndLastName.put("Gracjan", "Krajewska");
        firstNameAndLastName.put("Marcel", "Andrzejewski");
        firstNameAndLastName.put("Julian", "Kozłowski");
        firstNameAndLastName.put("Kamil", "Szewczyk");
        firstNameAndLastName.put("Kajetan", "Sawicki");
        firstNameAndLastName.put("Bogumił", "Lewandowski");

        Set<Teacher> teachers = firstNameAndLastName.entrySet().stream()
                .map(entry -> new Teacher(entry.getKey(), entry.getValue()))
                .collect(Collectors.toSet());
        teacherService.saveAllTeachers(teachers);
    }

    private void createStudents() {

    }

    private void createClassGroups(List<String> classNames) {
        Set<ClassGroup> classGroups = classNames.stream()
                .map(ClassGroup::new)
                .collect(Collectors.toSet());
        classGroupService.saveClassGroups(classGroups);
    }

    private void createSubjects() {
        Set<Subject> subjects = Stream.of(
                        "historia",
                        "informatyka",
                        "język angielski",
                        "język polski",
                        "matematyka",
                        "muzyka",
                        "plastyka",
                        "biologia",
                        "technika",
                        "wychowanie do życia w rodzinie",
                        "wychowanie fizyczne",
                        "zajęcia z wychowawcą")
                .map(subject -> Subject.builder().name(subject).build())
                .collect(Collectors.toSet());
        HashMap<Teacher, Integer> teachersUsingQty = new HashMap<>();
        teacherService.getTeachers().stream().forEach(teacher -> teachersUsingQty.put(teacher, 2));
        subjects.forEach(subject -> subject.setTeachers(getRandomTeachers(teachersUsingQty)));


        subjectService.saveSubjects(subjects);
    }

    private List<Teacher> getRandomTeachers(HashMap<Teacher, Integer> teachersUsingQty) {
        Random random = new Random();
        Teacher[] keys = teachersUsingQty.keySet().toArray(Teacher[]::new);
        List<Teacher> teachers = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            int decision = random.nextInt(teachersUsingQty.size());
            Teacher teacher = keys[decision];
            if (teachersUsingQty.get(teacher) <= 0 || teachers.contains(teacher)) {
                i--;
                continue;
            }
            teachers.add(teacher);
        }
        return teachers;
    }

    private void createDirector() {
        Director director = new Director();
        director.setFirstName("Mariusz");
        director.setLastName("Mroczek");
        director.setPesel("98609736754");
        director.setDateBirth(LocalDate.parse("1980-01-01"));
        director.setEmail("dyrektor@wp.pl");
        directorService.save(director);
    }

    private void createLessonPlan() {
        final int qtyOfLessonsInTheSameTime = 9;
        List<LessonHour> lessonHours = new ArrayList<>();
        List<Subject> subjects = subjectService.listAllSubject();
        List<ClassGroup> classGroups = classGroupService.listClassGroups();
        List<LessonInterval> allIntervals = lessonIntervalService.findAllIntervals();
        List<Weekday> weekdays = weekdayService.findAll();
        Random random = new Random();
        for (int i = 0; i < weekdays.size(); i++) {
            final Weekday weekday = weekdays.get(i);
            for (int j = 0; j < allIntervals.size(); j++) {
                for (int k = 0; k < qtyOfLessonsInTheSameTime; k++) {
                    int factorOfBreak = random.nextInt(qtyOfLessonsInTheSameTime) + 1;
                    if (factorOfBreak == qtyOfLessonsInTheSameTime) {
                        continue;
                    }
                    Subject subject = subjects.get(random.nextInt(subjects.size() - 1));
                    List<Teacher> teachers = subject.getTeachers();
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
                    lessonHours.add(new LessonHour(subject, lessonInterval, classGroup, teacher, weekdays.get(i)));
                }
            }
        }
       lessonHourService.saveLessonHours(lessonHours);
    }
}
