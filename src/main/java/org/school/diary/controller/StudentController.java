package org.school.diary.controller;

import org.school.diary.model.ClassGroup;
import org.school.diary.model.Student;
import org.school.diary.model.User;
import org.school.diary.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class StudentController {

    @Autowired
    ClassGroupService classGroupService;

    @Autowired
    UserService userService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    StudentService studentService;

    @Autowired
    MarkService markService;


    //WYSWIETLENIE PANELU OCEN STUDENTA
    @GetMapping("/home/uczen/oceny")
    public String getStudentMarks(Model model, Authentication auth) {
        //ModelAndView mv = new ModelAndView("student/get-student-marks");

        String login = auth.getName();
        System.out.println("LOGIN: "+ login);
//        User user = userService.findByEmail(login);
//        Student student = studentService.findByUser(user);


        return "/student/get-student-marks";
    }



}
