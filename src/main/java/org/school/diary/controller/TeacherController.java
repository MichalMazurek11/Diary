package org.school.diary.controller;

import lombok.RequiredArgsConstructor;
import org.school.diary.dto.ClassGroupDTO;
import org.school.diary.dto.TeacherDTO;
import org.school.diary.model.*;
import org.school.diary.model.common.Student;
import org.school.diary.model.common.Teacher;
import org.school.diary.model.wrappers.PresencesWrapper;
import org.school.diary.service.*;
import org.school.diary.utils.Index;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/home/teacher/")
public class TeacherController {


    private final ClassGroupService classGroupService;
    private final UserService userService;
    private final TeacherService teacherService;
    private final StudentService studentService;
    private final NoteToJournalService noteToJournalService;
    private final LessonHourService lessonHourService;
    private final PresenceService presenceService;

    //pierwszy krok tutaj nauczyciel wybiera klase
    @GetMapping("dodaj_uwage")
    public String getNoteToJournalStudent(Model model) {


        model.addAttribute("classGroupDTO", new ClassGroupDTO());
        model.addAttribute("classGroup", new ClassGroup());// ?
        model.addAttribute("listClassGroups",classGroupService.listClassGroups());
        return "teacher/add-noteToJournal";
    }
    // drugi krok w ktorym nauczyciel po wybraniu klasy, wybiera ucznia, uwage/pochwale i dodaje tresc do niej
    @RequestMapping( value = "dodaj_uwage/classGroup/{id}", method = RequestMethod.GET)
    public String getNoteToJournalStudent2(@Valid ClassGroupDTO classGroupDTO, BindingResult bindingResult,@RequestParam(value = "id",required = false)String id, Model model) {
        System.out.println("ClassGroupID: "+ id);
        List<String> typeNoteToJournal = new ArrayList<>();
        typeNoteToJournal.add("uwaga".toUpperCase(Locale.ROOT));
        typeNoteToJournal.add("pochwała".toUpperCase(Locale.ROOT));


        if (bindingResult.hasErrors()) {
            model.addAttribute("message", "Pole nie może być puste");
            model.addAttribute("listClassGroups",classGroupService.listClassGroups());
            return "teacher/add-noteToJournal";
        }else {
            ClassGroup classGroup = classGroupService.findById(Long.parseLong(id));

            model.addAttribute("noteToJournal", new NoteToJournal());
            model.addAttribute("typeNoteToJournal", typeNoteToJournal);
            model.addAttribute("classGroup", new ClassGroup());
            model.addAttribute("listStudents", studentService.findStudentsByStudentsClassGroup(classGroup));
            return "teacher/add-noteToJournal-2";
        }
    }

    //KIEDY CHCE DODAC UWAGE/POCHWALE DO BAZY DANYCH POSTMAPPING
    @PostMapping( value = "dodaj_uwage/classGroup")
    public String getNoteToJournalStudent2(@RequestParam Map<String, String> requestParams, NoteToJournal noteToJournal, Model model,Principal principal) {

        Teacher teacher = teacherService.findByEmail(principal.getName());
        String studentID = requestParams.get("student2");

        Student student = studentService.findById(Long.parseLong(studentID));


        Date date = new Date();
        noteToJournal.setTeacher(teacher);
        noteToJournal.setDate(date);
        noteToJournal.setStudent(student);
        noteToJournalService.save(noteToJournal);

        model.addAttribute("noteToJournal", new NoteToJournal());
            return "/home/home_page";
        }
    @GetMapping("wyswietl")
    public String getAll(Model model, Principal principal) {


        Teacher teacher = teacherService.findByEmail(principal.getName());

        model.addAttribute("noteToJournal", new NoteToJournal());
        model.addAttribute("noteToJournalList", noteToJournalService.findAllByTeacher(teacher));
        return "teacher/all-noteToJournal";
    }



    @GetMapping("wyswietl/usun/{id}")
    public String deleteNoteToJournal(@PathVariable("id")String id) {


        noteToJournalService.deleteNoteToJournal(Long.parseLong(id));

        return "redirect:/home/teacher/wyswietl";
    }

    //USTAWIENIA
    @GetMapping("ustawienia")
    public String getSettings(Model model) {


        model.addAttribute("teacherDTO", new TeacherDTO());
        return "teacher/settings";
    }

    @GetMapping("plan_lekcji")
    public String getSchedule(Model model) {

        model.addAttribute("teacherLessons", lessonHourService.getLessonPlanForTeacher());

        return "teacher/teacher_schedule";
    }

    //SPRAWDZIAN
    @GetMapping("sprawdzian")
    public String getExams(Model model,Principal principal) {

        Teacher teacher =teacherService.findByLogin(principal.getName());
        System.out.println("Teacher: "+ teacher);

        List<LessonHour> lessonHourList = lessonHourService.findAllByTeacher(teacher);

        System.out.println("Lekcje: "+ lessonHourList );

        model.addAttribute("lessonHourList", lessonHourService.findAllByTeacher(teacher));
        model.addAttribute("lessonHour", new LessonHour());
        model.addAttribute("exam", new Exam());
        return "teacher/add-exams";
    }
    @GetMapping("presence/{id}")
    public String getPresenceOnLesson(Model model,@PathVariable Integer id) {
        List<Presence> presences = presenceService.generateEmptyPresencesForStudentsGroup(id);
        model.addAttribute("counter",new Index(1));
        model.addAttribute("presences", presences);
        return "teacher/check_presences";
    }

    @RequestMapping(value = "presences", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String savePresences(Model model, ArrayList<Presence> presences) {
        presenceService.saveAll(presences);
        model.addAttribute("counter",new Index(1));
        model.addAttribute("presences", presences);
        return "teacher/check_presences";
    }

    @RequestMapping( value = "sprawdzian/{classGroupId}", method = RequestMethod.GET)
    public String getExamsAndClassGroup(Model model,Principal principal,@RequestParam(value = "classGroupId",required = false)String classGroupId) {

        System.out.println("klasaID: "+ classGroupId);

        Teacher teacher =teacherService.findByLogin(principal.getName());
        System.out.println("Teacher: "+ teacher);

        List<LessonHour> lessonHourList = lessonHourService.findAllByTeacher(teacher);

        System.out.println("Lekcje: "+ lessonHourList );

        model.addAttribute("lessonHourList", lessonHourService.findAllByTeacher(teacher));
        model.addAttribute("lessonHour", new LessonHour());
        model.addAttribute("exam", new Exam());
        return "teacher/add-exams";
    }




}
