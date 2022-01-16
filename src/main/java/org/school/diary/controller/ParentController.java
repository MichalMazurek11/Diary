package org.school.diary.controller;

import lombok.RequiredArgsConstructor;
import org.school.diary.config.UserPrincipal;
import org.school.diary.model.AnswerToHomework;
import org.school.diary.model.ClassGroup;
import org.school.diary.model.Homework;
import org.school.diary.model.common.Parent;
import org.school.diary.model.common.Student;
import org.school.diary.model.common.User;
import org.school.diary.service.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class ParentController {

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
    private final NoteToJournalService noteToJournalService;
    private final HomeworkService homeworkService;
    private final AnswerToHomeworkService answerToHomeworkService;

    //USTAWIENIA
    @GetMapping("/home/parent/ustawienia")
    public String getSettings(Model model) {


        model.addAttribute("user", new User());
        return "parent/settings";
    }
    @PostMapping("/home/parent/ustawienia")
    public String saveSettings(@RequestParam Map<String, String> requestParam, Model model, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userPrincipal.getUser();
        String password2 = requestParam.get("password2");
        password2 = password2.trim();
        int passwordLenght = password2.length();

        System.out.println("lenght " + passwordLenght);
        if (passwordLenght < 6 || password2.isEmpty()) {

            model.addAttribute("newPassword", "Nowe hasło musi mieć minimum 6 znaków");
        } else {
            user.setPassword(password2);
            userService.updatePassword(user);
            model.addAttribute("message", "Hasło zostało zmienione");
        }


        return "parent/settings";
    }

    //plan lekcji
    @GetMapping("/home/parent/plan_lekcji")
    public String getScheduleStudent(Model model, Principal principal) {

        Parent parent = parentService.findByLogin(principal.getName());

        Student student = studentService.findByParent(parent);
        ClassGroup classGroup = classGroupService.findById(student.getStudentsClassGroup().getId());

        model.addAttribute("lessonGroupMap", lessonHourService.getLessonPlanForStudents(classGroup));
        return "parent/schedule";
    }

    //uwagi
    @GetMapping("/home/parent/uwagi")
    public String getNotes(Model model, Principal principal) {

        Parent parent = parentService.findByLogin(principal.getName());

        Student student = studentService.findByParent(parent);


        model.addAttribute("noteToJournalList", noteToJournalService.findAllByStudent(student));
        return "parent/notes";
    }

    @GetMapping("/home/parent/praca_domowa")
    public String getAllHomeworks(Model model, Principal principal) {
        Parent parent = parentService.findByLogin(principal.getName());

        Student student = studentService.findByParent(parent);

        ClassGroup classGroup = classGroupService.findById(student.getStudentsClassGroup().getId());
        List<Homework> homeworkList = homeworkService.findHomeworkByHomeworksClassGroup(classGroup);

        model.addAttribute("homeworkList", homeworkList);
        return "parent/homeworks";
    }


    @GetMapping("/home/parent/praca_domowa/{id}/odpowiedz")
    public String getHomeworkAnswer(@PathVariable("id") String id, Model model, Principal principal) {

        Homework homework = homeworkService.findById(Long.parseLong(id));

        Parent parent = parentService.findByLogin(principal.getName());

        Student student = studentService.findByParent(parent);

        AnswerToHomework answerToHomework = answerToHomeworkService.findByHomeworkAndStudent(homework, student);

        model.addAttribute("answerToHomework", answerToHomework);
        return "parent/homework_answer";
    }


    //zmiana hasla

    @GetMapping("/home/parent/zmiana_hasla")
    public String changeEmailParent(Model model, Principal principal,@ModelAttribute Parent parent) {

        parent = parentService.findByLogin(principal.getName());


        model.addAttribute("parent", parent);
        return "parent/edit-parent-account";
    }

    @PostMapping("/home/parent/zmiana_hasla")
    public String AccountChanges( @ModelAttribute Parent parent,Principal principal, Model model) {
        String mailToSave = parent.getEmail();
        boolean checkIfEmailExists = userService.existsUserByEmail(mailToSave);



        if(mailToSave.isEmpty() || checkIfEmailExists){
            if(mailToSave.isEmpty()){
                model.addAttribute("messageError1", "Podany email jest pusty");
            }else{
                model.addAttribute("messageError2", "Podany email już jest zajęty");
            }
            return "parent/edit-parent-account";
        }else{
            parent = parentService.findByLogin(principal.getName());
            parent.setEmail(mailToSave);

            parentService.save(parent);

            return "redirect:/home/parent/plan_lekcji";
        }

    }






}
