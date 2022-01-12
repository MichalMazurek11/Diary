package org.school.diary.controller;

import lombok.RequiredArgsConstructor;
import org.school.diary.config.UserPrincipal;
import org.school.diary.model.*;
import org.school.diary.model.common.Student;
import org.school.diary.model.common.User;
import org.school.diary.model.enums.StateAnswaerToHomework;
import org.school.diary.model.enums.Term;
import org.school.diary.service.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
@RequestMapping
public class StudentController {

    private final ClassGroupService classGroupService;
    private final UserService userService;
    private final StudentService studentService;
    private final LessonHourService lessonHourService;
    private final SubjectService subjectService;
    private final AnnouncementService announcementService;
    private final NoteToJournalService noteToJournalService;
    private final MarkService markService;
    private final HomeworkService homeworkService;
    private final AnswearToHomeworkService answearToHomeworkService;


    //UWAGI POCHWALY

    @GetMapping("/home/student/uwagi")
    public String getNotes(Model model, Principal principal) {
        Student student = studentService.findByLogin(principal.getName());
        List<Student> studentList = studentService.findByFirstLetter("Ma");

        System.out.println("1lista: "+ studentList);

        model.addAttribute("noteToJournalList", noteToJournalService.findAllByStudent(student));
        return "student/note_to_journals";
    }

    //WYSWIETLENIE PANELU OCEN STUDENTA
    @GetMapping("/home/student/oceny/semestr_I")
    public String getStudentMarksTermOne(Model model, Principal principal) {

        Student student = studentService.findByLogin(principal.getName());

        List<Mark> markList = markService.findAllByStudent(student);

        List<Subject> subjectList = subjectService.listAllSubject().stream().sorted(Comparator.comparing(Subject::getName,String::compareToIgnoreCase)).collect(Collectors.toList());
        Term term = Term.SEMESTR_I;
        HashMap<Subject, List<Mark>> hashMap = new HashMap<>();
        for(Subject subject : subjectList){
            List<Mark> marksList = markService.findAllByStudentAndSubject(student,subject);
            hashMap.put(subject,marksList);
        }

//        TreeMap<Subject,  List<Mark>> foo = new TreeMap(hashMap);
//        Map<Subject, List<Mark>> sortedMap = new TreeMap<>();
//        sortedMap.putAll(hashMap);
        model.addAttribute("hashMap",hashMap);
        model.addAttribute("subjectList", subjectList);
        model.addAttribute("markList", markList);
        return "student/marks_term_one";
    }
    @GetMapping("/home/student/oceny/semestr_II")
    public String getStudentMarksTermTwo(Model model, Principal principal) {

        Student student = studentService.findByLogin(principal.getName());

        List<Mark> markList = markService.findAllByStudent(student);

        List<Subject> subjectList = subjectService.listAllSubject().stream().sorted(Comparator.comparing(Subject::getName,String::compareToIgnoreCase)).collect(Collectors.toList());
        Term term = Term.SEMESTR_II;
        HashMap<Subject, List<Mark>> hashMap = new HashMap<>();
        for(Subject subject : subjectList){
            List<Mark> marksList = markService.findAllByStudentAndSubjectAndTermValue(student,subject,term);
            hashMap.put(subject,marksList);
        }

//        TreeMap<Subject,  List<Mark>> foo = new TreeMap(hashMap);
//        Map<Subject, List<Mark>> sortedMap = new TreeMap<>();
//        sortedMap.putAll(hashMap);
        model.addAttribute("hashMap",hashMap);
        model.addAttribute("subjectList", subjectList);
        model.addAttribute("markList", markList);
        return "student/marks_term_two";
    }




    @GetMapping("/home/student/ocena_z_przedmiotu/{id}")
    public String getCurrentMark(@PathVariable("id") String id, Model model, Principal principal) {

        Mark mark = markService.findById(Long.parseLong(id));

        model.addAttribute("mark",mark);
        return "student/mark_subject";
    }



    //WYSWIETLENIE PANELU lekcji
    @GetMapping("/home/student/plan_lekcji")
    public String getSchedule(Model model, Principal principal) {

        Student student = studentService.findByLogin(principal.getName());
        ClassGroup classGroup = classGroupService.findById(student.getStudentsClassGroup().getId());

        model.addAttribute("lessonGroupMap", lessonHourService.getLessonPlanForStudents(classGroup));
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


        model.addAttribute("user", new User());
        return "student/settings";
    }

    //USTAWIENIA
    @PostMapping("/home/student/ustawienia")
    public String saveSettings(@RequestParam Map<String, String> requestParam, Model model, @AuthenticationPrincipal UserPrincipal userPrincipal) {

        User user = userPrincipal.getUser();
        String password2 = requestParam.get("password2");
        password2 = password2.trim();
        int passwordLenght = password2.length();

        if (passwordLenght < 6 ) {

            model.addAttribute("newPassword", "Nowe hasło musi mieć minimum 6 znaków");
        } else {
            user.setPassword(password2);
            userService.updatePassword(user);
            model.addAttribute("message", "Hasło zostało zmienione");
        }


        return "student/settings";
    }

    //prace_domowe
    @GetMapping("/home/student/prace_domowe")
    public String getAllHomeworks(Model model, Principal principal) {

        Student student = studentService.findByLogin(principal.getName());
        ClassGroup classGroup = classGroupService.findById(student.getStudentsClassGroup().getId());
        List<Homework> homeworkList = homeworkService.findHomeworkByHomeworksClassGroup(classGroup);

        model.addAttribute("homeworkList", homeworkList);
        return "student/all_homeworks";
    }
    //prace_domowe

    @GetMapping("/home/student/praca_domowa/{id}/dodaj_odpowiedz")
    public String getHomeworkAnswear(@PathVariable("id") String id, Model model, Principal principal) {

        Homework homework = homeworkService.findById(Long.parseLong(id));

        Student student = studentService.findByLogin(principal.getName());

        AnswearToHomework answearToHomework = answearToHomeworkService.findByHomeworkAndStudent(homework, student);

        model.addAttribute("answearToHomework", answearToHomework);
        return "student/homework_answear";
    }

    @PostMapping("/home/student/praca_domowa/{id}/dodaj_odpowiedz")
    public String addHomeworkAnswear(@PathVariable("id") String id, Model model, Principal principal, @ModelAttribute AnswearToHomework answearToHomework) {

        Homework homework = homeworkService.findById(Long.parseLong(id));

        Student student = studentService.findByLogin(principal.getName());

        LocalDate nowDate = LocalDate.now();
        LocalDate endDate = homework.getEndDateTask();

        boolean isEnableToSentAnswear = nowDate.isBefore(endDate);

        AnswearToHomework answearToHomework2 = answearToHomeworkService.findByHomeworkAndStudent(homework, student);
        boolean answaer = Objects.equals(answearToHomework2.getStateAnswaerToHomework(), StateAnswaerToHomework.NIEODDANA);

        //jesli praca jest nieoddana to
        if (answaer) {

            if (answearToHomework.getAnswearForHomework().isEmpty()) {

                model.addAttribute("messageError2", "Nie możesz wysłać pustej odpowiedzi");
                model.addAttribute("answearToHomework", answearToHomework2);
                return "student/homework_answear";
            } else {


                //sprawdz czy data dzisiejsza jest przed terminem oddania pracy
                if (isEnableToSentAnswear || nowDate.isEqual(endDate)) {

                    answearToHomework2.setAnswearForHomework(answearToHomework.getAnswearForHomework());
                    answearToHomework2.setStateAnswaerToHomework(StateAnswaerToHomework.WYSŁANA);
                    answearToHomeworkService.saveAnswear(answearToHomework2);

                    model.addAttribute("answearToHomework", answearToHomework2);
                    return "redirect:/home/student/prace_domowe";
                } else {

                    model.addAttribute("messageError", "Nie możesz wysłać odpowiedzi po terminie");
                    model.addAttribute("answearToHomework", answearToHomework2);
                    return "student/homework_answear";
                }
            }
        } else {


            model.addAttribute("answearToHomework", answearToHomework2);
            return "student/homework_answear";
        }
    }


}
