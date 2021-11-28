package org.school.diary.controller;

import lombok.NonNull;
import org.school.diary.model.ClassGroup;
import org.school.diary.model.LessonHour;
import org.school.diary.model.Weekday;
import org.school.diary.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @Autowired
    LessonHourService lessonHourService;

    @Autowired
    WeekdayService weekdayService;


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



    //WYSWIETLENIE PANELU OCEN STUDENTA
    @GetMapping("/home/uczen/plan_lekcji")
    public String getSchedule(Model model) {

     ClassGroup classGroup =  classGroupService.findById(1);
   //  LessonHour lessonHour = (LessonHour) lessonHourService.findAllByClassGroup(classGroup);
    // System.out.println("Classgroup: " + lessonHour.getWeekday().getDayName() + lessonHour.getLessonInterval().getBeginLesson() + lessonHour.getSubject().getName());

        Weekday wtorek = new Weekday();
        wtorek =  weekdayService.findWeekdayByDayName("wtorek");

        Weekday poniedzialek = new Weekday();
        poniedzialek =  weekdayService.findWeekdayByDayName("poniedziałek");

        model.addAttribute("lessonGroupList",lessonHourService.findAllByClassGroup(classGroup));
        model.addAttribute("lessonGroupListWTO",lessonHourService.findLessonHourByClassGroupAndWeekday(classGroup,wtorek));
        return "/user/get-schedule";
    }


}
