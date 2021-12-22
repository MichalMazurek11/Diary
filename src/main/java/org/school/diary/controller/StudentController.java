package org.school.diary.controller;

import org.school.diary.model.Announcement;
import org.school.diary.model.ClassGroup;
import org.school.diary.model.Weekday;
import org.school.diary.model.common.Student;
import org.school.diary.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

    @Autowired
    AnnouncementService announcementService;

    @Autowired
    NoteToJournalService noteToJournalService;

    //UWAGI POCHWALY
    @GetMapping("/home/student/uwagi")
    public String getNotes(Model model, Principal principal) {
       Student student =  studentService.findByEmail(principal.getName());

        model.addAttribute("noteToJournalList", noteToJournalService.findAllByStudent(student));
        return "student/get-noteToJournals";
    }

    //WYSWIETLENIE PANELU OCEN STUDENTA
    @GetMapping("/home/student/uczen/oceny")
    public String getStudentMarks(Model model) {

        return "/student/get-student-marks";
    }



    //WYSWIETLENIE PANELU OCEN STUDENTA
    @GetMapping("/home/student/plan_lekcji")
    public String getSchedule(Model model) {

     ClassGroup classGroup =  classGroupService.findById(1);
   //  LessonHour lessonHour = (LessonHour) lessonHourService.findAllByClassGroup(classGroup);
    // System.out.println("Classgroup: " + lessonHour.getWeekday().getDayName() + lessonHour.getLessonInterval().getBeginLesson() + lessonHour.getSubject().getName());

        Weekday wtorek = new Weekday();
        wtorek =  weekdayService.findWeekdayByDayName("wtorek");

//        Weekday poniedzialek = new Weekday();
//        poniedzialek =  weekdayService.findWeekdayByDayName("poniedziałek");

        Weekday PIĄTEK = new Weekday();
        PIĄTEK =  weekdayService.findWeekdayByDayName("PIĄTEK");

        model.addAttribute("lessonGroupMap",lessonHourService.findAllByClassGroup(classGroup));
        model.addAttribute("lessonGroupListWTO",lessonHourService.findLessonHourByClassGroupAndWeekday(classGroup,wtorek));
        model.addAttribute("lessonGroupListPIA",lessonHourService.findLessonHourByClassGroupAndWeekday(classGroup,PIĄTEK));


        return "/student/get-schedule";
    }
    //OGLOSZENIA
    @GetMapping("/home/ogloszenia")
    public String getAnnouncement(Model model) {

        model.addAttribute("Announcement", new Announcement());
        model.addAttribute("AnnouncementList", sortAnnouncementbyDate(announcementService.listAnnouncements()));
        return "student/get-announcements";
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

}
