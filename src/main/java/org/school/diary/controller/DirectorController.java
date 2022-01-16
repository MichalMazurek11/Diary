package org.school.diary.controller;


import lombok.RequiredArgsConstructor;
import org.school.diary.config.UserPrincipal;
import org.school.diary.dto.ClassGroupDTO;
import org.school.diary.dto.TeacherDTO;
import org.school.diary.dto.UserDTO;
import org.school.diary.model.*;
import org.school.diary.model.common.*;
import org.school.diary.model.enums.StatusTeacher;
import org.school.diary.service.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.util.*;

@RequestMapping
@RequiredArgsConstructor
@Controller
public class DirectorController {

    private final MailService mailService;
    private final ClassGroupService classGroupService;
    private final UserService userService;
    private final TeacherService teacherService;
    private final StudentService studentService;
    private final LessonHourService lessonHourService;
    private final SubjectService subjectService;
    private final AnnouncementService announcementService;
    private final DirectorService directorService;
    private final RoleService roleService;
    private final ParentService parentService;

    //WYSWIETLENIE OGLOSZEN
    @GetMapping("/home/director/dodaj_ogloszenie")
    public String getAnnouncement(Model model) {

        model.addAttribute("Announcement", new Announcement());
        model.addAttribute("AnnouncementList", sortAnnouncementbyDate(announcementService.listAnnouncements()));
        return "director/add-announcement";
    }
    //DODANIE OGLOSZENIA
    @PostMapping("/home/director/dodaj_ogloszenie")
    public String addAnnouncement(Announcement announcement, Model model, Principal principal) {

        if(announcement.getText().isEmpty()){

            model.addAttribute("messageError", "Pole nie może być puste");
        }else{
            LocalDate localDate = LocalDate.now();
            Director director = directorService.findByLogin(principal.getName());
            announcement.setDirectorsAnnouncement(director);
            announcement.setDateTime(localDate);
            announcementService.saveAnnouncement(announcement);
        }

        model.addAttribute("AnnouncementList", sortAnnouncementbyDate(announcementService.listAnnouncements()));
        return "director/add-announcement";
    }

    //USUNIECIE OGLOSZENIA
    @GetMapping("home/director/usun_ogloszenie/{id}")
    public String deleteAnnouncement(@PathVariable("id") String id, Model model) {
        announcementService.deleteAnnouncement(Integer.parseInt(id));

        model.addAttribute("AnnouncementList", sortAnnouncementbyDate(announcementService.listAnnouncements()));
        return "redirect:/home/director/dodaj_ogloszenie";
    }





    //NAUCZYCIEL
    //WYSWIETLENEI PANELU DODAWANIA NAUCZYCIELA
    @GetMapping("/home/director/dodaj_nauczyciela")
    public String getTeachers(Model model) {


        model.addAttribute("listSubjects", sortSubjectByName(subjectService.listAllSubject()));
        model.addAttribute("teacherDTO", new TeacherDTO());
        return "director/add-teacher";
    }

    //Dodanie nauczyciela
    @PostMapping("/home/director/dodaj_nauczyciela")
    public String addTeachers(@Valid @ModelAttribute TeacherDTO teacherDTO, BindingResult bindingResult, @RequestParam(value = "subjects", required = false) Set<Subject> subjectSet, @RequestParam Map<String, String> requestParam, Model model) {

       String dateToParse = requestParam.get("dateBirth2");

       boolean isSetIsNull = Objects.equals(null,subjectSet);

        if (bindingResult.hasErrors() || dateToParse.isEmpty() || isSetIsNull) {

            if(dateToParse.isEmpty()){
                model.addAttribute("messageError", "Pole nie może być puste");
            } else if (isSetIsNull) {
                model.addAttribute("messageError2", "Pole nie może być puste");
            }
            model.addAttribute("listSubjects", sortSubjectByName(subjectService.listAllSubject()));

            return "director/add-teacher";
        }else{

            boolean isPeselExists = userService.existsUserByPesel(teacherDTO.getPesel());
            boolean isEmailExists = userService.existsUserByEmail(teacherDTO.getEmail());

            if(isPeselExists || isEmailExists){
                if(isEmailExists){
                    model.addAttribute("messageEmail", "Taki email już istnieje!");
                }else {
                    model.addAttribute("messagePESEL", "Taki PESEL już istnieje!");
                }

            }else {

                LocalDate birthDate = LocalDate.parse(dateToParse);
//            subjectSet.forEach(topic -> System.out.println("Przedmiot: " + topic.getName()));
                teacherService.saveTeacher(birthDate, teacherDTO, subjectSet);
            }
        }

        model.addAttribute("listSubjects", sortSubjectByName(subjectService.listAllSubject()));
        model.addAttribute("teacherDTO", new TeacherDTO());
        return "director/add-teacher";
    }
    //USUNIECIE NAUCZYCIELA

    @GetMapping("/home/director/nauczyciel")
    public String deleteTeachersView(Model model, @ModelAttribute Teacher teacher) {


        model.addAttribute("teacherList", teacherService.listTeachers());
        return "director/delete-teacher";
    }

    @GetMapping("/home/director/usun_nauczyciela/{id}")
    public String deleteTeachers(@PathVariable("id") String id, Model model, @ModelAttribute Teacher teacher) {


        Teacher teacher2 = teacherService.findById(Long.parseLong(id));

        if(teacher2.getStatusTeacher() == StatusTeacher.AKTYWNY){
            teacher2.setStatusTeacher(StatusTeacher.NIEAKTYWNY);
        }else{
            teacher2.setStatusTeacher(StatusTeacher.AKTYWNY);
        }

        teacherService.save(teacher2);

      //  teacherService.deleteTeacher(Long.parseLong(id));
        model.addAttribute("teacher", new Teacher());
        model.addAttribute("teacherList", teacherService.listTeachers());
        return "director/delete-teacher";
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
        model.addAttribute("listClassGroup", sortClassGroupByName(classGroupService.listClassGroups()));
        return "director/add-supervisor-to-classgroup";
    }

    @PostMapping("/home/director/dodaj_wychowawce")
    public String addSupervisorClassgroup(Model model, ClassGroup classGroup) {

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
    public String addClassGroups(@ModelAttribute ClassGroup classGroup,@RequestParam Map<String, String> requestParam,Model model) {

        String nameClass = requestParam.get("name");

        if ( nameClass.isEmpty()){

            model.addAttribute("messageError", "Pole nie może być puste");
            return "/director/add-classgroup";
        }else{

            classGroupService.addClassGroup(classGroup);
        }
        return "/director/add-classgroup";
    }

    //WYSWIETLENIE PANELU DODAWANIE UCZNIA DO KLASY
    @GetMapping("/home/director/dodaj_ucznia_do_klasy")
    public String getClassGroupsUser(Model model) {


        model.addAttribute("student", new Student());
        model.addAttribute("classGroup", new ClassGroup());
        model.addAttribute("listClassGroups", classGroupService.listClassGroups());
        model.addAttribute("liststudents", studentService.listStudents());
        return "director/add-student-to-classgroup";
    }


    //WYSWIETLENIE PANELU DODAWANIE UCZNIA DO KLASY
    @PostMapping("/home/director/dodaj_ucznia/do_klasy/")
    public String addUserToClassGroup(@RequestParam Map<String, String> requestParams, Model model) {

        String studentId2 = requestParams.get("studentId");
        Student student = studentService.findById(Long.parseLong(studentId2));

        String classGroupId2 = requestParams.get("classGroupId");
        ClassGroup classGroup = classGroupService.findById(Long.parseLong(classGroupId2));


        System.out.println("studentId2: " + studentId2);
        System.out.println("classGroupId2: " + classGroupId2);

        student.setStudentsClassGroup(classGroup);
        studentService.saveStudent(student);

        model.addAttribute("student", new Student());
        model.addAttribute("classGroup", new ClassGroup());
        model.addAttribute("listClassGroups", classGroupService.listClassGroups());
        model.addAttribute("liststudents", studentService.listStudents());
        return "director/add-student-to-classgroup";
    }

    //usuniecie klasy
    @GetMapping("/home/director/usun_klase")
    public String deleteClassGroupView(Model model) {


        model.addAttribute("classGroup", new ClassGroup());
        model.addAttribute("listClassGroups", classGroupService.listClassGroups());
        return "director/delete-classgroup";
    }

    @GetMapping("/home/director/usun_klase/{id}")
    public String deleteClassGroup(@PathVariable("id") String id, Model model) {


        classGroupService.deleteClassGroup(Long.parseLong(id));

        model.addAttribute("listClassGroups", classGroupService.listClassGroups());
        return "redirect:/home/director/usun_klase";
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
    public String signUpStudentsAndParents(Model model) {

        model.addAttribute("userDTO", new UserDTO());
        return "director/add-student-and-parent";
    }

    //PRZYCISK REJESTRACJI
    @PostMapping("/home/director/rejestracja_ucznia_i_rodzica")
    public String AddStudenAndParent(@Valid @ModelAttribute("userDTO") UserDTO userDTO, BindingResult bindingResult, @RequestParam Map<String, String> requestParam, Model model) {


        String password2 = requestParam.get("password2");

//        boolean isExists = userService.existContractForPerson(password2);

//        System.out.println("isExists: "+ isExists);


        int lenghtParentPassword = password2.length();

        if (bindingResult.hasErrors() || password2.isEmpty() || lenghtParentPassword<6) {


            if(password2.isEmpty() || lenghtParentPassword<6){
                model.addAttribute("message", "Hasło rodzica musi mieć minimum 6 znaków");
            }

            return "director/add-student-and-parent";
        } else {
            String checkPesel = userDTO.getPesel();
            boolean isPeselExists =userService.existsUserByPesel(checkPesel);
            if(isPeselExists){
                model.addAttribute("messagePESEL", "Taki PESEL już istnieje!");
            }else {

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
                parent.setLogin(userDTO.getPesel() + "r");
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
    public String AccountChangesView(@PathVariable("id") String id, @ModelAttribute User user, Model model) {


        model.addAttribute("user", userService.findById(Long.parseLong(id)));
        return "director/edit-account";
    }

    //KONTO ZMIANA DANYCH
    @PostMapping("/home/director/konto/{id}")
    public String AccountChanges(@PathVariable("id") String id, @ModelAttribute User user, Model model) {

        User userToUpdate = userService.findById(Long.parseLong(id)); //znaleziony user po ID

        PersonRelatedWithSchool personRelatedWithSchool = userToUpdate.getPersonRelatedWithSchool();
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
    public String AccountPasswordChangesView(@PathVariable(value = "id") String id, @ModelAttribute User user, Model model) {


        model.addAttribute("user", userService.findById(Long.parseLong(id)));
        return "director/edit-account-password";
    }

    @PostMapping("/home/director/konto/edytuj_haslo/{id}")
    public String AccountPasswordChanges(@PathVariable(value = "id") String id, @ModelAttribute User user, @RequestParam Map<String, String> requestParam, Model model) {

        User user2 = userService.findById(Long.parseLong(id));
        String password2 = requestParam.get("password2");

        if (password2.equals("") || password2.length() < 6 ) {

            model.addAttribute("password2", "Nowe hasło musi mieć minimum 6 znaków");
        } else {
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
    public String SettingDirectorChangePassword(Model model, @AuthenticationPrincipal UserPrincipal userPrincipal, @RequestParam Map<String, String> requestParam) {

        User user = userPrincipal.getUser();
        String password2 = requestParam.get("password2");
        password2 = password2.trim();
        int passwordLenght = password2.length();


        if ( password2.isEmpty() || passwordLenght < 6) {

            model.addAttribute("newPassword", "Nowe hasło musi mieć minimum 6 znaków");
        } else {
            user.setPassword(password2);
            userService.updatePassword(user);
            model.addAttribute("message", "Hasło zostało zmienione");
        }

        return "director/settings";
    }

    //GENEROWANIE PLANU LEKCJi
    @GetMapping("/home/director/generuj_plan_lekcji")
    public String lessonPlan(@ModelAttribute ClassGroup classGroup, Model model) {


        model.addAttribute("listClassGroups", classGroupService.listClassGroups());
        return "director/create-lessonPlan";
    }

    @PostMapping("/home/director/generuj_plan_lekcji")
    public String createlessonPlanforClass(Model model) {

        lessonHourService.deleteAll();
        lessonHourService.createLessonPlan();

        model.addAttribute("classGroup", new ClassGroup());
        model.addAttribute("listClassGroups", classGroupService.listClassGroups());
        return "director/create-lessonPlan";
    }


}
