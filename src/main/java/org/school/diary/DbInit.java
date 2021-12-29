package org.school.diary;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.school.diary.dto.TeacherDTO;
import org.school.diary.dto.UserDTO;
import org.school.diary.model.*;
import org.school.diary.model.common.*;
import org.school.diary.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.school.diary.model.ClassRoomDuty.PE;

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
    private final RoleService roleService;
    private final UserService userService;
    private final ClassRoomService classRoomService;
    private final StudentService studentService;
    private final ParentService parentService;

    @Override
    public void run(String... args) throws Exception {
        createRoles();
        createClassGroups(Arrays.asList("1A", "2B", "3C", "4B", "2C", "4G", "2D"));
        createClassRooms();
        createTeachers();
        createSubjects();
        createStudentAndTeacherAndParent();
        createLessonIntervals();
        createWeekdays();
        createLessonPlan();
        createDirector();

    }


    private void  createRoles(){
        roleService.saveAllRoles(Arrays.asList(new Role(1L,"TEACHER"),
                new Role(2L,"PARENT"),
                new Role(3L,"STUDENT"),
                new Role(4L,"DIRECTOR")));

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

    private void createClassRooms() {
        Random random = new Random();
        final int maxFloorNumber = 3;
        final int maxSecondNumber = 2;
        final int maxLastNumber = 9;
        final int classQty = 40;
        int peClassesQt = 2;
        Set<ClassRoom> classRooms = new HashSet<>();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i <= classQty; i++) {
            int floorNumber = random.nextInt(maxFloorNumber);
            int secondNumber = random.nextInt(maxSecondNumber);
            int lastNumber = random.nextInt(maxLastNumber);
            String className = stringBuilder.append(floorNumber).append(secondNumber).append(lastNumber).toString();
            ClassRoomDuty classRoomDuty = null;
            boolean peClassesOverload = true;
            while (peClassesOverload){
                int classRoomDutyIdx = random.nextInt(ClassRoomDuty.values().length);
                ClassRoomDuty chosenClassroomDuty = ClassRoomDuty.values()[classRoomDutyIdx];
                if (chosenClassroomDuty == PE){
                    if (peClassesQt==0){
                        continue;
                    }
                    peClassesQt--;
                }
                classRoomDuty = chosenClassroomDuty;
                peClassesOverload=false;
            }
            classRooms.add(new ClassRoom(className,classRoomDuty));
            stringBuilder.setLength(0);
        }
        classRoomService.addClassRooms(classRooms);

    }

    private void createLessonIntervals() {
        final int lessonTime = 45;
        final int[] breaks = new int[]{5, 10, 20};
//        List<Integer> givenList = Arrays.asList(4,5,6,7,8,9);
//        Random rand = new Random();
//        int randomElement = givenList.get(rand.nextInt(givenList.size()));
        final Random random = new Random();
        List<LessonInterval> lessonIntervals = new ArrayList<>();
        LocalTime tempTime = LocalTime.of(8, 00);
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

    private void createStudentAndTeacherAndParent() {


        Student student = new Student();
        student.setFirstName("Michał");
        student.setLastName("Mazurek");
        student.setEmail("123@o2.pl");
        student.setPesel("123");
        student.setLogin("123");
        student.setDateBirth(LocalDate.parse("1998-05-05"));

        ClassGroup classGroup = classGroupService.findById(1L);
        student.setStudentsClassGroup(classGroup);
        studentService.saveStudent(student);

        User user3 = new User();
        Role role1 = roleService.findRoleByName("STUDENT");
        user3.setRoles(Collections.singleton(role1));
        user3.setPassword("123");
        user3.setPersonRelatedWithSchool(student);
        userService.save(user3);

        Parent parent = new Parent();
        parent.setLogin(student.getPesel()+"r");
        parentService.saveParent(parent);

        User user4 = new User();
        Role role = roleService.findRoleByName("PARENT");
        user4.setRoles(Collections.singleton(role));
        user4.setPassword("123");
        user4.setPersonRelatedWithSchool(parent);
        userService.save(user4);


        Set<Subject> subjects = subjectService.listAllSubject().stream().limit(1).collect(Collectors.toSet());
        TeacherDTO teacherDTO = TeacherDTO.builder()
                .firstName("Maciej")
                .lastName("Stańczak")
                .email("1234@o2.pl")
                .pesel("1234")
                .password("123")
                .login("1234").build();
        teacherService.saveTeacher(LocalDate.parse("1988-02-02"),teacherDTO,subjects);

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
        director.setLogin("98609736754");
        directorService.save(director);

        User user = new User();
//        Role directorRole = roleRepository.findAll().stream().filter(role -> role.getName().equalsIgnoreCase("director")).findFirst().orElseThrow(() -> new NullPointerException());
//        user.setRoles(Collections.singleton(directorRole));
        Role role3 = roleService.findRoleByName("DIRECTOR");
        user.setRoles(Collections.singleton(role3));
        user.setPersonRelatedWithSchool(director);
        user.setPassword("123");
        userService.save(user);


        //moj dyrektor Michał zeby szybciej sie logowac
        Director director2 = new Director();
        director2.setFirstName("Katarzyna");
        director2.setLastName("Laksander");
        director2.setPesel("98609736754");
        director2.setDateBirth(LocalDate.parse("1980-01-01"));
        director2.setEmail("12345@o2.pl");
        director2.setLogin("12345");
        directorService.save(director2);

        User user4 = new User();
        Role role2 = roleService.findRoleByName("DIRECTOR");
        user4.setRoles(Collections.singleton(role2));
        user4.setPassword("123");
        user4.setPersonRelatedWithSchool(director2);
        userService.save(user4);

    }

    private void createLessonPlan() {
        final int qtyOfLessonsInTheSameTime = 5;
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
                    if (chosenClassRoom.getClassRoomDuty() == PE){
                        if (peClasses.size()==2){
                            chosenClassRoom = peClasses.get(peClassesIdx++);
                        }
                        peClasses.add(chosenClassRoom);
                    }
                    if (classRoomsAtTheSameTime.contains(chosenClassRoom)){
                        k--;
                        continue;
                    }
                    classRoomsAtTheSameTime.add(chosenClassRoom);

                    int factorOfBreak = random.nextInt(qtyOfLessonsInTheSameTime) + 1;
                    if (factorOfBreak == qtyOfLessonsInTheSameTime) {
                        continue;
                    }
                    Subject subject = subjects.get(random.nextInt(subjects.size() - 1));
                    if (!subject.getName().equals("wychowanie fizyczne")&&chosenClassRoom.getClassRoomDuty() == PE){
                        k--;
                    }
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
                    lessonHours.add(new LessonHour(subject, lessonInterval, classGroup, teacher, weekdays.get(i),chosenClassRoom));
                }
            }
        }
       lessonHourService.saveLessonHours(lessonHours);
    }
}
