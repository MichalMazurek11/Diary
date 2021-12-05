package org.school.diary.controller;


import org.school.diary.model.ClassGroup;
import org.school.diary.model.Subject;
import org.school.diary.model.common.Student;
import org.school.diary.model.common.Teacher;
import org.school.diary.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping
public class DirectorController {


    @Autowired
    ClassGroupService classGroupService;

    @Autowired
    UserService userService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    StudentService studentService;

    @Autowired
    SubjectService subjectService;


    @GetMapping("/home/rejestracja_uzytkownikow")
    public String requestsRegisterUsers(Principal prin, Model model) {


        return "/director/requestRegisterUsers";
    }


    //NAUCZYCIEL

    //WYSWIETLENEI PANELU DODAWANIA NAUCZYCIELA
    @GetMapping("/home/dodaj_nauczyciela")
    public String getTeachers(Model model) {
        ModelAndView mv = new ModelAndView("director/add-teacher");



        model.addAttribute("listSubjects",sortSubjectByName(subjectService.listAllSubject()));
        model.addAttribute("teacher", new Teacher());
        return "director/add-teacher";
    }

    @PostMapping("/home/dodaj_nauczyciela")
    public String addTeachers(@RequestParam("subject") Set<Subject> subjectSet,@RequestParam  Map<String, String> requestParam, @ModelAttribute Teacher teacher, Model model) {

        subjectSet.forEach(topic -> System.out.println("Przedmiot: "+topic.getName()));

        String dateString = requestParam.get("dateBirth2");
        LocalDate localDate = LocalDate.parse(dateString);
        teacherService.saveTeacher(teacher,localDate,subjectSet);



        model.addAttribute("listSubjects", sortSubjectByName(subjectService.listAllSubject()));
        model.addAttribute("teacher", new Teacher());
        return "director/add-teacher";
    }
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
    @GetMapping("/home/dodaj_wychowawce")
    public String getSupervisorClassgroup(Model model) {

        model.addAttribute("listTeachers", teacherService.listTeachers());
        model.addAttribute("listClassGroup",sortClassGroupByName(classGroupService.listClassGroups()));
        return "director/add-supervisor-to-classgroup";
    }

    @PostMapping("/home/dodaj_wychowawce")
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
    @GetMapping("/home/dodaj_klase")
    public ModelAndView getClassGroups(Model model) {
        ModelAndView mv = new ModelAndView("director/add-classgroup");


        model.addAttribute("classGroup", new ClassGroup());
        return mv;
    }

    //DODANIE NOWEJ KLASY
    @PostMapping("/home/dodaj_klase")
    public String addClassGroups(@RequestParam("supervisor") Teacher supervisor, Model model, ClassGroup classGroup) {

        System.out.println("supervisor: "+ supervisor.getFirstName() + " " + supervisor.getLastName());

        classGroupService.addClassGroup(classGroup);

        return "/director/add-classgroup";

    }

    //WYSWIETLENIE PANELU DODAWANIE UCZNIA DO KLASY
    @GetMapping("/home/dodaj_ucznia_do_klasy")
    public String getClassGroupsUser(Model model) {


        model.addAttribute("student", new Student());
        model.addAttribute("classGroup", new ClassGroup());
        model.addAttribute("listClassGroups",classGroupService.listClassGroups());
        model.addAttribute("liststudents",studentService.listStudents());
        return "director/add-user-to-classgroup";
    }


    //WYSWIETLENIE PANELU DODAWANIE UCZNIA DO KLASY
    @PostMapping("/home/dodaj_ucznia/{studentId}/do_klasy/{classGroupId}")
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
        return "director/add-user-to-classgroup";
    }



}
