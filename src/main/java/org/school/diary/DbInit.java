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
//        createRoles();
//        createClassGroups(Arrays.asList("1A", "2B", "3C", "4B", "2C", "4G", "2D"));
//        createClassRooms();
//        createTeachers();
//        createSubjects();
//        createStudentAndTeacherAndParent();
//        createLessonIntervals();
//        createWeekdays();
//        createLessonPlan();
//        createDirector();
//        students();


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

        student.setParent(parent);
        studentService.saveStudent(student);


        Set<Subject> subjects = subjectService.listAllSubject().stream().limit(2).collect(Collectors.toSet());
        TeacherDTO teacherDTO = TeacherDTO.builder()
                .firstName("Maciej")
                .lastName("Stańczak")
                .email("1234@o2.pl")
                .pesel("1234")
                .password("123")
                .login("1234").build();
        teacherService.saveTeacher(LocalDate.parse("1988-02-02"),teacherDTO,subjects);

    }

    private void students() {
        //student1
        Student weronika = new Student();
        weronika.setFirstName("Weronika");
        weronika.setLastName("Witucka");
        weronika.setEmail("weronika@o2.pl");
        weronika.setPesel("92011091621");
        weronika.setLogin("92011091621");
        weronika.setDateBirth(LocalDate.parse("1998-05-05"));

        ClassGroup classGroup1 = classGroupService.findById(1L);
        weronika.setStudentsClassGroup(classGroup1);
        studentService.saveStudent(weronika);

        User userWeronika = new User();
        Role role1 = roleService.findRoleByName("STUDENT");
        userWeronika.setRoles(Collections.singleton(role1));
        userWeronika.setPassword("123");
        userWeronika.setPersonRelatedWithSchool(weronika);
        userService.save(userWeronika);

        Parent parentWeronika = new Parent();
        parentWeronika.setLogin(weronika.getPesel()+"r");
        parentService.save(parentWeronika);

        User userParentWeronika = new User();
        Role role2 = roleService.findRoleByName("PARENT");
        userParentWeronika.setRoles(Collections.singleton(role2));
        userParentWeronika.setPassword("123");
        userParentWeronika.setPersonRelatedWithSchool(parentWeronika);
        userService.save(userParentWeronika);

        weronika.setParent(parentWeronika);
        studentService.saveStudent(weronika);

        //student2
        Student adam = new Student();
        adam.setFirstName("Adam");
        adam.setLastName("Krawczyk");
        adam.setEmail("Adam@o2.pl");
        adam.setPesel("93101397276");
        adam.setLogin("93101397276");
        adam.setDateBirth(LocalDate.parse("1998-07-05"));

        ClassGroup classGroup2 = classGroupService.findById(2L);
        adam.setStudentsClassGroup(classGroup2);
        studentService.saveStudent(adam);

        User userAdam = new User();
        Role role3 = roleService.findRoleByName("STUDENT");
        userAdam.setRoles(Collections.singleton(role3));
        userAdam.setPassword("123");
        userAdam.setPersonRelatedWithSchool(adam);
        userService.save(userAdam);

        Parent parentAdam = new Parent();
        parentAdam.setLogin(adam.getPesel()+"r");
        parentService.save(parentAdam);

        User userParentAdam = new User();
        Role role4 = roleService.findRoleByName("PARENT");
        userParentAdam.setRoles(Collections.singleton(role4));
        userParentAdam.setPassword("123");
        userParentAdam.setPersonRelatedWithSchool(parentAdam);
        userService.save(userParentAdam);

        adam.setParent(parentAdam);
        studentService.saveStudent(adam);

        //student3
        Student ola = new Student();
        ola.setFirstName("Ola");
        ola.setLastName("Kucharska");
        ola.setEmail("Ola@o2.pl");
        ola.setPesel("98032241944");
        ola.setLogin("98032241944");
        ola.setDateBirth(LocalDate.parse("1998-05-06"));

        ClassGroup classGroup3 = classGroupService.findById(3L);
        ola.setStudentsClassGroup(classGroup3);
        studentService.saveStudent(ola);

        User userOla = new User();
        Role role5 = roleService.findRoleByName("STUDENT");
        userOla.setRoles(Collections.singleton(role5));
        userOla.setPassword("123");
        userOla.setPersonRelatedWithSchool(ola);
        userService.save(userOla);

        Parent parentOla = new Parent();
        parentOla.setLogin(ola.getPesel()+"r");
        parentService.save(parentOla);

        User userParentOla = new User();
        Role role6 = roleService.findRoleByName("PARENT");
        userParentOla.setRoles(Collections.singleton(role6));
        userParentOla.setPassword("123");
        userParentOla.setPersonRelatedWithSchool(parentOla);
        userService.save(userParentOla);

        ola.setParent(parentOla);
        studentService.saveStudent(ola);

        //student4
        Student krzysztof = new Student();
        krzysztof.setFirstName("Krzysztof");
        krzysztof.setLastName("Wiśniewski");
        krzysztof.setEmail("Krzysztof@o2.pl");
        krzysztof.setPesel("04280198554");
        krzysztof.setLogin("04280198554");
        krzysztof.setDateBirth(LocalDate.parse("1998-01-06"));

        ClassGroup classGroup4 = classGroupService.findById(4L);
        krzysztof.setStudentsClassGroup(classGroup4);
        studentService.saveStudent(krzysztof);

        User userKrzysztof = new User();
        Role role7 = roleService.findRoleByName("STUDENT");
        userKrzysztof.setRoles(Collections.singleton(role7));
        userKrzysztof.setPassword("123");
        userKrzysztof.setPersonRelatedWithSchool(krzysztof);
        userService.save(userKrzysztof);

        Parent parentKrzysztof = new Parent();
        parentKrzysztof.setLogin(krzysztof.getPesel()+"r");
        parentService.save(parentKrzysztof);

        User userParentKrzysztof = new User();
        Role role8 = roleService.findRoleByName("PARENT");
        userParentKrzysztof.setRoles(Collections.singleton(role8));
        userParentKrzysztof.setPassword("123");
        userParentKrzysztof.setPersonRelatedWithSchool(parentKrzysztof);
        userService.save(userParentKrzysztof);

        krzysztof.setParent(parentKrzysztof);
        studentService.saveStudent(krzysztof);

        //student5
        Student mikołaj = new Student();
        mikołaj.setFirstName("Mikołaj");
        mikołaj.setLastName("Lysek");
        mikołaj.setEmail("Mikołaj@o2.pl");
        mikołaj.setPesel("90080263424");
        mikołaj.setLogin("90080263424");
        mikołaj.setDateBirth(LocalDate.parse("1998-01-02"));

        ClassGroup classGroup5 = classGroupService.findById(5L);
        mikołaj.setStudentsClassGroup(classGroup5);
        studentService.saveStudent(mikołaj);

        User userMikołaj = new User();
        Role role9 = roleService.findRoleByName("STUDENT");
        userMikołaj.setRoles(Collections.singleton(role9));
        userMikołaj.setPassword("123");
        userMikołaj.setPersonRelatedWithSchool(mikołaj);
        userService.save(userMikołaj);

        Parent parentMikołaj = new Parent();
        parentMikołaj.setLogin(mikołaj.getPesel()+"r");
        parentService.save(parentMikołaj);

        User userParentMikołaj = new User();
        Role role10 = roleService.findRoleByName("PARENT");
        userParentMikołaj.setRoles(Collections.singleton(role10));
        userParentMikołaj.setPassword("123");
        userParentMikołaj.setPersonRelatedWithSchool(parentMikołaj);
        userService.save(userParentMikołaj);

        mikołaj.setParent(parentMikołaj);
        studentService.saveStudent(mikołaj);

        //student6
        Student ada = new Student();
        ada.setFirstName("Ada");
        ada.setLastName("Kucharczyk");
        ada.setEmail("Ada@o2.pl");
        ada.setPesel("99081436516");
        ada.setLogin("99081436516");
        ada.setDateBirth(LocalDate.parse("1998-01-02"));

        ClassGroup classGroup6 = classGroupService.findById(6L);
        ada.setStudentsClassGroup(classGroup6);
        studentService.saveStudent(ada);

        User userAda = new User();
        Role role11 = roleService.findRoleByName("STUDENT");
        userAda.setRoles(Collections.singleton(role11));
        userAda.setPassword("123");
        userAda.setPersonRelatedWithSchool(ada);
        userService.save(userAda);

        Parent parentAda = new Parent();
        parentAda.setLogin(ada.getPesel()+"r");
        parentService.save(parentAda);

        User userParentAda = new User();
        Role role12 = roleService.findRoleByName("PARENT");
        userParentAda.setRoles(Collections.singleton(role12));
        userParentAda.setPassword("123");
        userParentAda.setPersonRelatedWithSchool(parentAda);
        userService.save(userParentAda);

        ada.setParent(parentAda);
        studentService.saveStudent(ada);


        //student7
        Student kacper = new Student();
        kacper.setFirstName("Kacper");
        kacper.setLastName("Skalski");
        kacper.setEmail("Kacper@o2.pl");
        kacper.setPesel("91081326527");
        kacper.setLogin("91081326527");
        kacper.setDateBirth(LocalDate.parse("1998-01-12"));

        ClassGroup classGroup7 = classGroupService.findById(7L);
        kacper.setStudentsClassGroup(classGroup7);
        studentService.saveStudent(kacper);

        User userKacper = new User();
        Role role13 = roleService.findRoleByName("STUDENT");
        userKacper.setRoles(Collections.singleton(role13));
        userKacper.setPassword("123");
        userKacper.setPersonRelatedWithSchool(kacper);
        userService.save(userKacper);

        Parent parentKacper = new Parent();
        parentKacper.setLogin(kacper.getPesel()+"r");
        parentService.save(parentKacper);

        User userParentKacper = new User();
        Role role14 = roleService.findRoleByName("PARENT");
        userParentKacper.setRoles(Collections.singleton(role14));
        userParentKacper.setPassword("123");
        userParentKacper.setPersonRelatedWithSchool(parentKacper);
        userService.save(userParentKacper);


        kacper.setParent(parentKacper);
        studentService.saveStudent(kacper);
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
