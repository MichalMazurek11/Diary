package org.school.diary.controller;


import org.school.diary.config.UserPrincipal;
import org.school.diary.dto.ClassGroupDTO;
import org.school.diary.dto.TeacherDTO;
import org.school.diary.dto.UserDTO;
import org.school.diary.model.*;
import org.school.diary.model.common.*;
import org.school.diary.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Path;
import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.school.diary.model.ClassRoomDuty.PE;

@Controller
@RequestMapping
public class DirectorController {


    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ClassGroupService classGroupService;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    StudentService studentService;

    @Autowired
    SubjectService subjectService;

    @Autowired
    AnnouncementService announcementService;

    @Autowired
    ParentService parentService;

    @Autowired
    DirectorService directorService;

    @Autowired
    ClassRoomService classRoomService;

    @Autowired
    WeekdayService weekdayService;

    @Autowired
    LessonHourService lessonHourService;

    @Autowired
    LessonIntervalService lessonIntervalService;


    @GetMapping("/home/director/rejestracja_uzytkownikow")
    public String requestsRegisterUsers(Principal prin, Model model) {


        return "/director/requestRegisterUsers";
    }


    //NAUCZYCIEL

    //WYSWIETLENEI PANELU DODAWANIA NAUCZYCIELA
    @GetMapping("/home/director/dodaj_nauczyciela")
    public String getTeachers(Model model) {


        model.addAttribute("listSubjects",sortSubjectByName(subjectService.listAllSubject()));
        model.addAttribute("teacherDTO", new TeacherDTO());
        return "director/add-teacher";
    }
    //Dodanie nauczyciela
    @PostMapping("/home/director/dodaj_nauczyciela")
    public String addTeachers(@Valid @ModelAttribute TeacherDTO teacherDTO,BindingResult bindingResult, @RequestParam(value = "subjects",required = false) Set<Subject> subjectSet,@RequestParam  Map<String, String> requestParam, Model model) {


        LocalDate birthDate = LocalDate.parse(requestParam.get("dateBirth2"));
        if(bindingResult.hasErrors()){
            model.addAttribute("listSubjects", sortSubjectByName(subjectService.listAllSubject()));
//            model.addAttribute("message", "Pole nie może być puste");

            return "director/add-teacher";
        }
        subjectSet.forEach(topic -> System.out.println("Przedmiot: "+topic.getName()));
        teacherService.saveTeacher(birthDate,teacherDTO, subjectSet);

        model.addAttribute("listSubjects", sortSubjectByName(subjectService.listAllSubject()));
        model.addAttribute("teacherDTO", new TeacherDTO());
        return "director/add-teacher";
    }
    //USUNIECIE NAUCZYCIELA

    @GetMapping("/home/director/nauczyciel")
    public String deleteTeachersView(Model model) {



        model.addAttribute("teacherList", teacherService.listTeachers());
        return "director/delete-teacher";
    }

    @GetMapping("/home/director/usun_nauczyciela/{id}")
    public String deleteTeachers(@PathVariable("id") String id, Model model) {



        teacherService.deleteTeacher(Long.parseLong(id));model.addAttribute("teacher", new Teacher());
        model.addAttribute("teacherList", teacherService.listTeachers());
        return "redirect:/director/delete-teacher";
    }


//
//    @GetMapping("admin/category-form/delete-Category/{categoryId}")
//    public String deleteCategory(@PathVariable("categoryId")String categoryId) {
//        ModelAndView mv = new ModelAndView("admin/category-form");
//
//        categoryService.deleteCategory(Long.parseLong(categoryId));
//        mv.addObject("categoryList", sortByName(categoryService.listCategory()));
//        return "redirect:/admin/category-form";
//    }




    //KLASA
    public List<Subject> sortSubjectByName(List<Subject> tmp) {

        Collections.sort(tmp, new Comparator<Subject>() {

            @Override
            public int compare(Subject a1, Subject a2) {
                return a1.getName().compareTo(a2.getName());
            }

        });
        return tmp;
    }
    //WYSWIETLENIE PANELU DODAWANIA KLASY
    @GetMapping("/home/director/dodaj_wychowawce")
    public String getSupervisorClassgroup(Model model) {

        model.addAttribute("listTeachers", teacherService.listTeachers());
        model.addAttribute("listClassGroup",sortClassGroupByName(classGroupService.listClassGroups()));
        return "director/add-supervisor-to-classgroup";
    }

    @PostMapping("/home/director/dodaj_wychowawce")
    public String addSupervisorClassgroup(Model model,ClassGroup classGroup) {

        classGroupService.addClassGroup(classGroup);

        model.addAttribute("listTeachers", teacherService.listTeachers());
        model.addAttribute("listClassGroup", sortClassGroupByName(classGroupService.listClassGroups()));
        return "director/add-supervisor-to-classgroup";
    }

    public List<ClassGroup> sortClassGroupByName(List<ClassGroup> tmp) {

        Collections.sort(tmp, new Comparator<ClassGroup>() {

            @Override
            public int compare(ClassGroup a1, ClassGroup a2) {
                return a1.getName().compareTo(a2.getName());
            }

        });
        return tmp;
    }


    //WYSWIETLENIE PANELU DODAWANIA KLASY
    @GetMapping("/home/director/dodaj_klase")
    public String getClassGroups(Model model) {

        model.addAttribute("classGroupDTO", new ClassGroupDTO());
        return "director/add-classgroup";
    }

    //DODANIE NOWEJ KLASY
    @PostMapping("/home/director/dodaj_klase")
    public String addClassGroups(@Valid ClassGroupDTO classGroupDTO, BindingResult bindingResult,@ModelAttribute ClassGroup classGroup, Model model) {

        if (bindingResult.hasErrors()) {
            return "/director/add-classgroup";
        }

        classGroupService.addClassGroup(classGroup);
        return "/director/add-classgroup";

    }

    //WYSWIETLENIE PANELU DODAWANIE UCZNIA DO KLASY
    @GetMapping("/home/director/dodaj_ucznia_do_klasy")
    public String getClassGroupsUser(Model model) {


        model.addAttribute("student", new Student());
        model.addAttribute("classGroup", new ClassGroup());
        model.addAttribute("listClassGroups",classGroupService.listClassGroups());
        model.addAttribute("liststudents",studentService.listStudents());
        return "director/add-student-to-classgroup";
    }





    //WYSWIETLENIE PANELU DODAWANIE UCZNIA DO KLASY
    @PostMapping("/home/director/dodaj_ucznia/{studentId}/do_klasy/{classGroupId}")
    public String addUserToClassGroup(@RequestParam Map<String, String> requestParams,Model model ) {

        String studentId = requestParams.get("studentId");
        Student student = studentService.findById(Long.parseLong(studentId));

        String classGroupId = requestParams.get("classGroupId");
        ClassGroup classGroup = classGroupService.findById(Long.parseLong(classGroupId));

        System.out.println("studentId: " + studentId);
        System.out.println("classGroupId: " + classGroupId);

        student.setStudentsClassGroup(classGroup);
        studentService.saveStudent(student);

        model.addAttribute("student", new Student());
        model.addAttribute("classGroup", new ClassGroup());
        model.addAttribute("listClassGroups",classGroupService.listClassGroups());
        model.addAttribute("liststudents",studentService.listStudents());
        return "director/add-student-to-classgroup";
    }

    //usuniecie klasy
    @GetMapping("/home/director/usun_klase")
    public String deleteClassGroupView(Model model) {



        model.addAttribute("classGroup", new ClassGroup());
        model.addAttribute("listClassGroups",classGroupService.listClassGroups());
        return "director/delete-classgroup";
    }

    @GetMapping("/home/director/usun_klase/{id}")
    public String deleteClassGroup(@PathVariable("id") String id, Model model) {


        classGroupService.deleteClassGroup(Long.parseLong(id));

        model.addAttribute("listClassGroups",classGroupService.listClassGroups());
        return "redirect:/home/director/usun_klase";
    }







    //WYSWIETLENIE OGLOSZEN
    @GetMapping("/home/director/dodaj_ogloszenie")
    public String getAnnouncement(Model model) {

        model.addAttribute("Announcement", new Announcement());
        model.addAttribute("AnnouncementList", sortAnnouncementbyDate(announcementService.listAnnouncements()));
        return "director/add-announcement";
    }
    @PostMapping("/home/director/dodaj_ogloszenie")
    public String addAnnouncement(Announcement announcement, Model model,Principal principal) {

        LocalDate localDate = LocalDate.now();
        Director director = directorService.findByLogin(principal.getName());
        announcement.setDirectorsAnnouncement(director);
        announcement.setDateTime(localDate);
        announcementService.saveAnnouncement(announcement);

        model.addAttribute("AnnouncementList", sortAnnouncementbyDate(announcementService.listAnnouncements()));
        return "director/add-announcement";
    }

    //USUNIECIE OGLOSZENIA
    @GetMapping("home/director/usun_ogloszenie/{id}")
    public String deleteAnnouncement(@PathVariable("id")String id, Model model) {
        ModelAndView mv = new ModelAndView("admin/category-form");

        announcementService.deleteAnnouncement(Integer.parseInt(id));

        model.addAttribute("AnnouncementList", sortAnnouncementbyDate(announcementService.listAnnouncements()));
        return "redirect:/home/director/dodaj_ogloszenie";
    }

    public List<Announcement> sortAnnouncementbyDate(List<Announcement> tmp) {

        Collections.sort(tmp, new Comparator<Announcement>() {

            @Override
            public int compare(Announcement a1, Announcement a2) {
                return a2.getDateTime().compareTo(a1.getDateTime());
            }

        });
        return tmp;
    }

    //REJESTRACJA UCZNIA
    @GetMapping("/home/director/rejestracja_ucznia_i_rodzica")
    public String signUp(Model model) {

        model.addAttribute("userDTO", new UserDTO());
        return "director/add-student-and-parent";
    }

    //PRZYCISK REJESTRACJI
    @PostMapping("/home/director/rejestracja_ucznia_i_rodzica")
    public String signup(@Valid @ModelAttribute("userDTO") UserDTO userDTO, BindingResult bindingResult,@RequestParam  Map<String, String> requestParam, Model model){


        String password2 = requestParam.get("password2");

        if(bindingResult.hasErrors() || password2.equals("")){
            model.addAttribute("message", "Pole nie może być puste");

            return "director/add-student-and-parent";
        }else{

            Student student = new Student();
            student.setLogin(userDTO.getPesel());
            student.setFirstName(userDTO.getFirstName());
            student.setLastName(userDTO.getLastName());
            student.setPesel(userDTO.getPesel());
            student.setDateBirth(LocalDate.parse(userDTO.getBirthDate()));

            studentService.saveStudent(student);

            User user = new User();
            Role role1 = roleService.findRoleByName("STUDENT");
            user.setRoles(Collections.singleton(role1));
            user.setPassword(userDTO.getPassword());
            user.setPersonRelatedWithSchool(student);
            userService.save(user);

            Parent parent = new Parent();
            parent.setLogin(userDTO.getPesel()+"r");
            parentService.save(parent);

            User user2 = new User();
            Role role2 = roleService.findRoleByName("PARENT");
            user2.setRoles(Collections.singleton(role2));
            user2.setPassword(password2);
            user2.setPersonRelatedWithSchool(parent);
            userService.save(user2);

            student.setParent(parent);
            studentService.saveStudent(student);


        }
        model.addAttribute("userDTO", new UserDTO());
        return "director/add-student-and-parent";       // przekierowanie na adres metodą GET
    }



    //LISTA WSZYSTKICH KONT
    @GetMapping("/home/director/konta")
    public String Account(Model model) {

        model.addAttribute("userList", userService.listUsers());
        return "director/edit-user-accounts";
    }

    //KONTO ID
    @GetMapping("/home/director/konto/{id}")
    public String AccountChangesView(@PathVariable("id") String id,@ModelAttribute User user, Model model) {


        model.addAttribute("user", userService.findById(Long.parseLong(id)));
        return "director/edit-account";
    }

    //KONTO ZMIANA DANYCH
    @PostMapping("/home/director/konto/{id}")
    public String AccountChanges(@PathVariable("id") String id,@ModelAttribute User user, Model model) {

        User userToUpdate = userService.findById(Long.parseLong(id));

        PersonRelatedWithSchool personRelatedWithSchool = new PersonRelatedWithSchool();
        personRelatedWithSchool.setPesel(user.getPersonRelatedWithSchool().getPesel());
        personRelatedWithSchool.setFirstName(user.getPersonRelatedWithSchool().getFirstName());
        personRelatedWithSchool.setLastName(user.getPersonRelatedWithSchool().getLastName());
        personRelatedWithSchool.setDateBirth(user.getPersonRelatedWithSchool().getDateBirth());
        personRelatedWithSchool.setEmail(user.getPersonRelatedWithSchool().getEmail());
        personRelatedWithSchool.setLogin(user.getPersonRelatedWithSchool().getLogin());
        userToUpdate.setPersonRelatedWithSchool(personRelatedWithSchool);

        userService.updateAccount(userToUpdate);

        model.addAttribute("user", userService.findById(Long.parseLong(id)));
      return "redirect:/home/director/konta";

    }
    //LISTA KONT - ZMIANA HASLA
    @GetMapping("/home/director/konto/edytuj_haslo/{id}")
    public String AccountPasswordChangesView(@PathVariable(value = "id") String id,@ModelAttribute User user,  Model model) {


        model.addAttribute("user", userService.findById(Long.parseLong(id)));
        return "director/edit-account-password";
    }

    @PostMapping("/home/director/konto/edytuj_haslo/{id}")
    public String AccountPasswordChanges( @PathVariable(value = "id") String id,@ModelAttribute User user, @RequestParam  Map<String, String> requestParam, Model model) {

        User user2 = userService.findById(Long.parseLong(id));
        String password2 = requestParam.get("password2");

        if(password2.equals("")){

            model.addAttribute("password2", "Nowe hasło nie może być puste");
        }else {
            user2.setPassword(password2);
            userService.updatePassword(user2);
            model.addAttribute("message", "Hasło zostało zmienione");
        }

        return "director/edit-account-password";
    }

    //USTAWIENIA KONTA - ZMIANA HASLA
    @GetMapping("/home/director/ustawienia")
    public String SettingDirector() {

        return "director/settings";
    }

    @PostMapping("/home/director/ustawienia/edytuj_haslo")
    public String SettingDirectorChangePassword(Model model,@AuthenticationPrincipal UserPrincipal userPrincipal,@RequestParam Map<String, String> requestParam) {

        User user = userPrincipal.getUser();
        String password2 = requestParam.get("password2");

            if(password2.equals("")){

                model.addAttribute("newPassword", "Nowe hasło nie może być puste");
            }else {
                user.setPassword(password2);
                userService.updatePassword(user);
                model.addAttribute("message", "Hasło zostało zmienione");
            }


        return "director/settings";
    }

    //GENEROWANIE PLANU LEKCJi
    @GetMapping("/home/director/generuj_plan_lekcji")
    public String lessonPlan(@ModelAttribute ClassGroup classGroup, Model model,@RequestParam Map<String, String> requestParam) {

        model.addAttribute("listClassGroups",classGroupService.listClassGroups());
//        Student student = studentService.findByLogin(principal.getName());
//        ClassGroup classGroup = classGroupService.findById(student.getStudentsClassGroup().getId());
        String name = requestParam.get("classGroup");

        System.out.println("klasa wybrana: " + name);
//        model.addAttribute("lessonGroupMap", lessonHourService.getLessonPlanForStudents(classGroup));


        return "director/create-lessonPlan";
    }

    @PostMapping("/home/director/generuj_plan_lekcji")
    public String createlessonPlanforClass(Model model) {

        lessonHourService.deleteAll();
        createLessonPlan();

        System.out.println("asa");
        model.addAttribute("classGroup", new ClassGroup());
        model.addAttribute("listClassGroups",classGroupService.listClassGroups());
        return "director/create-lessonPlan";
    }


    private void createLessonPlan() {
        final int qtyOfLessonsInTheSameTime = 6 ;
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
