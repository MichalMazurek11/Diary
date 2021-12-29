package org.school.diary.controller;

import org.school.diary.dto.UserDTO;
import org.school.diary.model.Announcement;
import org.school.diary.model.ClassGroup;
import org.school.diary.model.Weekday;
import org.school.diary.model.common.Student;
import org.school.diary.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping
public class StudentController {

    @Autowired
    PasswordEncoder passwordEncoder;

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
        Student student = studentService.findByEmail(principal.getName());

        model.addAttribute("noteToJournalList", noteToJournalService.findAllByStudent(student));
        return "student/note_to_journals";
    }

    //WYSWIETLENIE PANELU OCEN STUDENTA
    @GetMapping("/home/student/uczen/oceny")
    public String getStudentMarks(Model model) {

        return "student/student_marks";
    }


    //WYSWIETLENIE PANELU OCEN STUDENTA
    @GetMapping("/home/student/plan_lekcji")
    public String getSchedule(Model model) {

        ClassGroup classGroup = classGroupService.findById(1);


        Weekday wtorek = new Weekday();
        wtorek = weekdayService.findWeekdayByDayName("wtorek");

        Weekday PIĄTEK = new Weekday();
        PIĄTEK = weekdayService.findWeekdayByDayName("PIĄTEK");

        model.addAttribute("lessonGroupMap", lessonHourService.getLessonPlanForStudents(classGroup));
        model.addAttribute("lessonGroupListWTO", lessonHourService.findLessonHourByClassGroupAndWeekday(classGroup, wtorek));
        model.addAttribute("lessonGroupListPIA", lessonHourService.findLessonHourByClassGroupAndWeekday(classGroup, PIĄTEK));


        return "student/student_schedule";
    }

    //OGLOSZENIA
    @GetMapping("/home/ogloszenia")
    public String getAnnouncement(Model model) {

        model.addAttribute("Announcement", new Announcement());
        model.addAttribute("AnnouncementList", sortAnnouncementbyDate(announcementService.listAnnouncements()));
        return "student/announcements";
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

    //USTAWIENIA
    @GetMapping("/home/student/ustawienia")
    public String getSettings(Model model) {


        model.addAttribute("userDTO", new UserDTO());
        return "student/settings";
    }

    //USTAWIENIA
    @PostMapping("/home/student/ustawienia")
    public String saveSettings(@Valid @ModelAttribute("userDTO") UserDTO userDTO, BindingResult bindingResult, Model model, Principal principal) {


        System.out.println("Haslo:" + userDTO.getPassword());
        Student student = studentService.findByEmail(principal.getName());

        if (bindingResult.hasErrors()) {

            return "student/settings";
        } else {
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        }


        model.addAttribute("userDTO", new UserDTO());
        return "student/settings";
    }


}
