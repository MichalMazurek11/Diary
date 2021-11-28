package org.school.diary.controller;

import org.school.diary.model.ClassGroup;
import org.school.diary.model.common.Student;
import org.school.diary.service.ClassGroupService;
import org.school.diary.service.StudentService;
import org.school.diary.service.TeacherService;
import org.school.diary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TeacherController {

    @Autowired
    ClassGroupService classGroupService;

    @Autowired
    UserService userService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    StudentService studentService;


    @GetMapping("/home/teacher/dodaj_uwage")
    public String getNoteToJournalStudent(Model model) {
        //    ModelAndView mv = new ModelAndView("director/add-user-to-classgroup-form");


      //  model.addAttribute("student", new Student());
      //  model.addAttribute("classGroup", new ClassGroup());
        model.addAttribute("listClassGroups",classGroupService.listClassGroups());
     //   model.addAttribute("liststudents",studentService.listStudents());
        return "teacher/add-noteToJournal-form";
    }


}
