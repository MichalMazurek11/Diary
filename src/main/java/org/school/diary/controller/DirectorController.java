package org.school.diary.controller;


import org.school.diary.model.ClassGroup;
import org.school.diary.model.common.Student;
import org.school.diary.model.common.Teacher;
import org.school.diary.service.ClassGroupService;
import org.school.diary.service.ClassRoomService;
import org.school.diary.service.StudentService;
import org.school.diary.service.TeacherService;
import org.school.diary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Map;

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


    @GetMapping("/home/rejestracja_uzytkownikow")
    public String requestsRegisterUsers(Principal prin, Model model) {




        return "/director/requestRegisterUsers";
    }


    //NAUCZYCIEL

    //WYSWIETLENEI PANELU DODAWANIA NAUCZYCIELA
    @GetMapping("/home/dodaj_nauczyciela")
    public ModelAndView addTeachers(Model model) {
        ModelAndView mv = new ModelAndView("director/add-teacher-form");


        model.addAttribute("teacher", new Teacher());
        return mv;
    }

    //KLASA

    //WYSWIETLENIE PANELU DODAWANIA KLASY
    @GetMapping("/home/dodaj_klase")
    public ModelAndView getClassGroups(Model model) {
        ModelAndView mv = new ModelAndView("director/add-classgroup-form");


        model.addAttribute("classGroup", new ClassGroup());
        return mv;
    }

    //DODANIE NOWEJ KLASY
    @PostMapping("/home/dodaj_klase")
    public String addClassGroups(Model model, ClassGroup classGroup) {


        classGroupService.addClassGroup(classGroup);

        return "/director/add-classgroup-form";

    }

    //WYSWIETLENIE PANELU DODAWANIE UCZNIA DO KLASY
    @GetMapping("/home/dodaj_ucznia_do_klasy")
    public String getClassGroupsUser(Model model) {
    //    ModelAndView mv = new ModelAndView("director/add-user-to-classgroup-form");


        model.addAttribute("student", new Student());
        model.addAttribute("classGroup", new ClassGroup());
        model.addAttribute("listClassGroups",classGroupService.listClassGroups());
        model.addAttribute("liststudents",studentService.listStudents());
        return "director/add-user-to-classgroup-form";
    }


    //WYSWIETLENIE PANELU DODAWANIE UCZNIA DO KLASY
    @PostMapping("/home/dodaj_ucznia/{studentId}/do_klasy/{classGroupId}")
    public String addUserToClassGroup(@RequestParam Map<String, String> requestParams,Model model ) {
        //    ModelAndView mv = new ModelAndView("director/add-user-to-classgroup-form");
//        System.out.println("StudentID : "  + requestParams.get("studentId") );
//        System.out.println("ClassGroupId : "  + requestParams.get("classGroupId") );

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
        return "director/add-user-to-classgroup-form";
    }



}
