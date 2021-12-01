package org.school.diary.controller;


import org.school.diary.service.ClassRoomService;
import org.school.diary.service.StudentService;
import org.school.diary.service.TeacherService;
import org.school.diary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class UserController {


    @Autowired
    ClassRoomService classGroupService;

    @Autowired
    UserService userService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    StudentService studentService;

}
