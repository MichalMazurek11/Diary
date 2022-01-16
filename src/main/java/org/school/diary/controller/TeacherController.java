package org.school.diary.controller;

import lombok.RequiredArgsConstructor;
import org.school.diary.config.UserPrincipal;
import org.school.diary.model.*;
import org.school.diary.model.common.Student;
import org.school.diary.model.common.Teacher;
import org.school.diary.model.common.User;
import org.school.diary.model.enums.StateAnswaerToHomework;
import org.school.diary.model.enums.Term;
import org.school.diary.model.enums.TypeMark;
import org.school.diary.model.enums.TypeNote;
import org.school.diary.model.wrappers.PresenceWrapperTest;
import org.school.diary.service.*;
import org.school.diary.utils.Index;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Controller
@RequestMapping("/home/teacher/")
public class TeacherController {

    private final MailService mailService;
    private final ClassGroupService classGroupService;
    private final UserService userService;
    private final TeacherService teacherService;
    private final StudentService studentService;
    private final NoteToJournalService noteToJournalService;
    private final LessonHourService lessonHourService;
    private final PresenceService presenceService;
    private final HomeworkService homeworkService;
    private final AnswerToHomeworkService answerToHomeworkService;
    private final MarkService markService;

    //Wubór klasy przy wprowadzeniu uwagi
    @GetMapping("dodaj_uwage")
    public String getNoteToJournalStudent(Model model, @ModelAttribute ClassGroup classGroup) {

        List<ClassGroup> listClassGroups = classGroupService.listClassGroups();

        model.addAttribute("listClassGroups", listClassGroups);
        return "teacher/add-noteToJournal";
    }

    // Wybor ucznia, uwaga/pochwala. i dodanie treści
    @GetMapping("dodaj_uwage/klasa/{id}")
    public String getNoteToJournalStudent2(Model model, @ModelAttribute ClassGroup classGroup) {

        List<TypeNote> typeNoteList = new ArrayList<>();
        typeNoteList.add(TypeNote.UWAGA);
        typeNoteList.add(TypeNote.POCHWAŁA);

        model.addAttribute("noteToJournal", new NoteToJournal());
        model.addAttribute("typeNoteToJournal", typeNoteList);
        model.addAttribute("listStudents", studentService.findStudentsByStudentsClassGroup(classGroup));
        return "teacher/add-noteToJournal-2";

    }

    //Dodanie UWAGI/POCHWALA
    @PostMapping(value = "dodaj_uwage/klasa/{id}")
    public String getNoteToJournalStudent2(@RequestParam Map<String, String> requestParams, @ModelAttribute ClassGroup classGroup, Model model, Principal principal) {

        Teacher teacher = teacherService.findByLogin(principal.getName());

        List<TypeNote> typeNoteList = new ArrayList<>();
        typeNoteList.add(TypeNote.UWAGA);
        typeNoteList.add(TypeNote.POCHWAŁA);

        String studentID = requestParams.get("student2");

        String descriptionNote = requestParams.get("description");

        String type = requestParams.get("type");

        if (studentID.isEmpty() || type.isEmpty() || descriptionNote.isEmpty()) {

            model.addAttribute("messageError", "Wypełnij wszystkie pola");
        } else {
            Student student = studentService.findById(Long.parseLong(studentID));

            Date date = new Date();

            NoteToJournal noteToJournal = new NoteToJournal();
            if (type.equals("UWAGA")) {
                noteToJournal.setTypeNote(TypeNote.UWAGA);
            } else {
                noteToJournal.setTypeNote(TypeNote.POCHWAŁA);
            }
            String typeNoteMail = noteToJournal.getTypeNote()+"";
            noteToJournal.setName(descriptionNote);
            noteToJournal.setTeacher(teacher);
            noteToJournal.setDate(date);
            noteToJournal.setStudent(student);
            noteToJournal.setClassGroup(student.getStudentsClassGroup());
            noteToJournalService.save(noteToJournal);

            String mailParent = student.getParent().getEmail();

            if( Objects.isNull(mailParent) || mailParent.isEmpty()){

            }else{
                LocalDate date2 = LocalDate.now();
                mailService.sendEmail(mailParent,typeNoteMail.toUpperCase(Locale.ROOT)+ " dodano ","Uwaga dnia: "+date2+". "+descriptionNote);
            }


            model.addAttribute("messageSuccess", "Dodano");
            model.addAttribute("noteToJournal", new NoteToJournal());

        }
        model.addAttribute("typeNoteToJournal", typeNoteList);
        model.addAttribute("listStudents", studentService.findStudentsByStudentsClassGroup(classGroup));
        return "teacher/add-noteToJournal-2";
    }

    //Wyświetlenie wszystkich uwag
    @GetMapping("wyswietl_uwagi")
    public String getAll(Model model, Principal principal) {
        Teacher teacher = teacherService.findByLogin(principal.getName());

        model.addAttribute("noteToJournal", new NoteToJournal());
        model.addAttribute("noteToJournalList", noteToJournalService.findAllByTeacher(teacher));
        return "teacher/all-noteToJournal";
    }

    //USUNIĘCIE UWAGI/POCHWALY
    @GetMapping("wyświetl_uwagi/usun/{id}")
    public String deleteNoteToJournal(@PathVariable("id") String id) {


        noteToJournalService.deleteNoteToJournal(Long.parseLong(id));
        return "redirect:/home/teacher/wyswietl_uwagi";
    }

//    //Dodanie oceny widok
//    @GetMapping("dodaj_ocene")
//    public String addMark(Model model,Principal principal) {
//
//        Teacher teacher = teacherService.findByLogin(principal.getName());
//
//        List<LessonHour> lessonHourList = lessonHourService.findAllByTeacher(teacher);
//        Set<ClassGroup> classGroupList = (Set<ClassGroup>) lessonHourList.stream().map(lessonHour -> lessonHour.getClassGroup()).collect(Collectors.toSet());
//
//        List<TypeMark> typeMarkList = new ArrayList<>();
//        typeMarkList.add(TypeMark.INNE);
//        typeMarkList.add(TypeMark.SPRAWDZIAN);
//        typeMarkList.add(TypeMark.AKTYWNOŚĆ);
//        typeMarkList.add(TypeMark.KARTKÓWKA);
//        typeMarkList.add(TypeMark.ODPOWIEDŹ);
//        typeMarkList.add(TypeMark.PRACA_DOMOWA);
//
//        Set<Subject> subjectSet = lessonHourList.stream().map(lessonHour -> lessonHour.getSubject()).collect(Collectors.toSet());
//
//        model.addAttribute("typeMarkList", typeMarkList);
//        model.addAttribute("subjectSet", subjectSet);
//        model.addAttribute("classGroupList", classGroupList);
//        model.addAttribute("mark", new Mark());
//        return "teacher/add-mark";
//    }
//    @PostMapping("dodaj_ocene")
//    public String addMarkToStudent(Model model,Principal principal) {
//
//        Teacher teacher = teacherService.findByLogin(principal.getName());
//
//        List<LessonHour> lessonHourList = lessonHourService.findAllByTeacher(teacher);
//        Set<ClassGroup> classGroupList = (Set<ClassGroup>) lessonHourList.stream().map(lessonHour -> lessonHour.getClassGroup()).collect(Collectors.toSet());
//        Subject subject = subjectService.findById(64);
//        Mark mark = new Mark();
//        ClassGroup classGroup = classGroupService.findById(1);
//
//        Student student = studentService.findByLogin("123");
//
//        mark.setStudent(student);
//        mark.setTeacher(teacher);
//        mark.setSubject(subject);
//        mark.setValue("5");
//        mark.setTypeMark(TypeMark.SPRAWDZIAN);
//        markService.save(mark);
//
//        Set<Subject> subjectSet = lessonHourList.stream().map(lessonHour -> lessonHour.getSubject()).collect(Collectors.toSet());
//
//
//        model.addAttribute("subjectSet", subjectSet);
//        model.addAttribute("classGroupList", classGroupList);
//        model.addAttribute("mark", new Mark());
//        return "teacher/add-mark";
//    }

    //USTAWIENIA
    @GetMapping("ustawienia")
    public String getSettings(Model model) {

        model.addAttribute("teacher", new Teacher());
        return "teacher/settings";
    }


    @PostMapping("ustawienia")
    public String changeSettings(Model model, @AuthenticationPrincipal UserPrincipal userPrincipal, @RequestParam Map<String, String> requestParam) {

        User user = userPrincipal.getUser();

        String password2 = requestParam.get("password2");
        password2 = password2.trim();

        boolean isEmpty = password2.isEmpty();
        int passwordLenght = password2.length();

        if (isEmpty || passwordLenght < 6) {

            model.addAttribute("newPassword", "Nowe hasło musi mieć minimum 6 znaków");
        } else {
            user.setPassword(password2);
            userService.updatePassword(user);
            model.addAttribute("message", "Hasło zostało zmienione");
        }

        return "teacher/settings";
    }

    //plan_lekcji
    @GetMapping("plan_lekcji")
    public String getSchedule(Model model) {

        model.addAttribute("teacherLessons", lessonHourService.getLessonPlanForTeacher());

        return "teacher/teacher_schedule";
    }


    //SPRAWDZIAN
//    @GetMapping("sprawdzian")
//    public String getExams(Model model, Principal principal) {
//
//        Teacher teacher = teacherService.findByLogin(principal.getName());
//        System.out.println("Teacher: " + teacher);
//
//        List<LessonHour> lessonHourList = lessonHourService.findAllByTeacher(teacher);
//
//        System.out.println("Lekcje: " + lessonHourList);
//
//        model.addAttribute("lessonHourList", lessonHourService.findAllByTeacher(teacher));
//        model.addAttribute("lessonHour", new LessonHour());
//        model.addAttribute("exam", new Exam());
//        return "teacher/add-exams";
//    }

    @GetMapping("presence/{id}")
    public String getPresenceOnLesson(Model model, @PathVariable Integer id) {

        List<Presence> presences = new ArrayList<>();
        presences = presenceService.generateEmptyPresencesForStudentsGroup(id);

        //test
        PresenceWrapperTest wrapper = new PresenceWrapperTest();
        wrapper.setPresenceList((ArrayList<Presence>) presences);

//        System.out.println("Get wrapper : " + wrapper);


        model.addAttribute("wrapper", wrapper);
        model.addAttribute("counter", new Index(1));
        model.addAttribute("presences", presences);
        model.addAttribute("presence", new Presence());
        return "teacher/check_presences";
    }

    @RequestMapping(value = "presences/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String savePresences(@PathVariable("id") String id, @ModelAttribute PresenceWrapperTest wrapper, Model model, ArrayList<Presence> presences, @RequestParam Map<String, String> requestParams) {


        System.out.println("Test wrapp: " + wrapper.getPresenceList());
        System.out.println("Test wrapp: " + wrapper);
        System.out.println("presences : " + presences);
        presenceService.saveAll(presences);

        System.out.println(wrapper.getPresenceList() != null ? wrapper.getPresenceList().size() : "null list");
        System.out.println("Wrapper: " + requestParams.get("wrapper"));


        model.addAttribute("wrapper", wrapper);
        model.addAttribute("counter", new Index(1));
        model.addAttribute("presences", presences);
        model.addAttribute("presence", new Presence());
//        return "redirect:/home/teacher/plan_lekcji";
        return "teacher/check_presences";
    }

//    @RequestMapping(value = "sprawdzian/{classGroupId}", method = RequestMethod.GET)
//    public String getExamsAndClassGroup(Model model, Principal principal, @RequestParam(value = "classGroupId", required = false) String classGroupId) {
//
//        System.out.println("klasaID: " + classGroupId);
//
//        Teacher teacher = teacherService.findByLogin(principal.getName());
//
//        model.addAttribute("lessonHourList", lessonHourService.findAllByTeacher(teacher));
//        model.addAttribute("lessonHour", new LessonHour());
//        model.addAttribute("exam", new Exam());
//        return "teacher/add-exams";
//    }

    //widok dodawania nowej pracy domowej
    @GetMapping("lekcja/{id}/dodaj_prace_domowa")
    public String getHomework(@PathVariable("id") String id, Model model, Principal principal) {

        LessonHour lessonHour = lessonHourService.findAllByid(Integer.parseInt(id));

        model.addAttribute("lessonHour", lessonHour);
        return "teacher/add-homework";
    }

    @PostMapping("lekcja/{id}/dodaj_prace_domowa")
    public String addHomework(@PathVariable("id") String id, @RequestParam Map<String, String> requestParams, Model model, Principal principal, @ModelAttribute Homework homework) {

        Teacher teacher = teacherService.findByLogin(principal.getName());
        LessonHour lessonHour = lessonHourService.findAllByid(Integer.parseInt(id));

        ClassGroup classGroup = classGroupService.findById(lessonHour.getClassGroup().getId());
        Subject subject = lessonHour.getSubject();

        String taskDescription = requestParams.get("taskDescription");
        String endDateTaskString = requestParams.get("endDateTask");

        boolean isDateEmpty = endDateTaskString.isEmpty();

        boolean isDateBlank = endDateTaskString.isBlank();

        boolean isTaskDescritpionBlank = taskDescription.isBlank();

        boolean isTaskDescritpionEmpty = taskDescription.isEmpty();

        if (isDateEmpty || isDateBlank || isTaskDescritpionEmpty || isTaskDescritpionBlank) {

            model.addAttribute("messageError", "Wypełnij wszystkie pola");
            model.addAttribute("lessonHour", lessonHour);

        } else {
            LocalDate endDateTask = LocalDate.parse(endDateTaskString);

            homework = new Homework();
            homework.setHomeworkTeacher(teacher);
            homework.setHomeworksClassGroup(classGroup);
            homework.setHomewroksSubject(subject);
            homework.setEndDateTask(endDateTask);
            homework.setTaskDescription(taskDescription);

            LocalDate now = LocalDate.now();
            homework.setCreatedDateTask(now);
            homeworkService.save(homework);

            model.addAttribute("messageSuccess", "Praca domowa została dodana");
            List<AnswerToHomework> answerToHomeworksList = answerToHomeworkService.generateAndSaveEmptyAnswer(homework.getHomeworksClassGroup(), homework);
            model.addAttribute("lessonHour", lessonHour);
        }


        return "teacher/add-homework";
    }

    //wyswietl
    @GetMapping("sprawdz_prace_domowa")
    public String getAllHomework(Model model, Principal principal) {


        Teacher teacher = teacherService.findByLogin(principal.getName());
        List<Homework> homeworkList = homeworkService.findHomeworkByHomeworkTeacher(teacher);

        model.addAttribute("homeworkList", homeworkList);
        return "teacher/check-homework";
    }

    //wyswietl
    @GetMapping("sprawdz_prace_domowa/{id}")
    public String getAllHomeworkStudent(@PathVariable("id") String id, Model model) {

        Homework homework = homeworkService.findById(Long.parseLong(id));

        List<AnswerToHomework> list = answerToHomeworkService.findAllByHomework(homework);
        model.addAttribute("answerToHomework", new AnswerToHomework());
        model.addAttribute("answerToHomeworksList", list);
        return "teacher/check-homework-answers";
    }

    @GetMapping("sprawdz_prace_domowa/{id}/odpowiedz/{answer_id}")
    public String getAnswertudent(@PathVariable("id") String id, @PathVariable("answer_id") String studentId, Model model) {


        AnswerToHomework answerToHomework = answerToHomeworkService.findById(Long.parseLong(studentId));


        model.addAttribute("answerToHomework", answerToHomework);
        return "teacher/check-homework-student-answer";
    }

    @GetMapping("sprawdz_prace_domowa/{id}/odpowiedz/{answer_id}/dodaj_ocene")
    public String getMarkForAnswer(@PathVariable("id") String id, @PathVariable("answer_id") String studentId, Model model) {


        AnswerToHomework answerToHomework = answerToHomeworkService.findById(Long.parseLong(studentId));

        List<String> valueList = new ArrayList<>();
        valueList.add("1");
        valueList.add("1+");
        valueList.add("-2");
        valueList.add("2");
        valueList.add("+2");
        valueList.add("-3");
        valueList.add("3");
        valueList.add("+3");
        valueList.add("-4");
        valueList.add("4");
        valueList.add("+4");
        valueList.add("-5");
        valueList.add("5");
        valueList.add("+5");
        valueList.add("6");

        List<Term> termList = new ArrayList<>();
        termList.add(Term.SEMESTR_I);
        termList.add(Term.SEMESTR_II);

        model.addAttribute("termList", termList);
        model.addAttribute("valueList", valueList);
        model.addAttribute("mark", new Mark());
        model.addAttribute("answerToHomework", answerToHomework);
        return "teacher/add-mark-homework-student-answer";
    }

    @PostMapping("sprawdz_prace_domowa/{id}/odpowiedz/{answer_id}/dodaj_ocene")
    public String addMarkForAnswer(@PathVariable("id") String id, @PathVariable("answer_id") String studentId, @RequestParam Map<String, String> requestParams, Model model, Principal principal) {

        List<String> valueList = new ArrayList<>();
        valueList.add("1");
        valueList.add("1+");
        valueList.add("2-");
        valueList.add("2");
        valueList.add("2+");
        valueList.add("3-");
        valueList.add("3");
        valueList.add("3+");
        valueList.add("4-");
        valueList.add("4");
        valueList.add("4+");
        valueList.add("5-");
        valueList.add("5");
        valueList.add("5+");
        valueList.add("6");

        List<Term> termList = new ArrayList<>();
        termList.add(Term.SEMESTR_I);
        termList.add(Term.SEMESTR_II);

        AnswerToHomework answerToHomework = answerToHomeworkService.findById(Long.parseLong(studentId));

        Teacher teacher = teacherService.findByLogin(principal.getName());

        Student student = answerToHomework.getStudent();

        Subject subject = answerToHomework.getHomework().getHomewroksSubject();

        String term = requestParams.get("term");
        String markString = requestParams.get("mark");

        Term termToSave = Term.valueOf(term);

        Mark mark = new Mark();
        mark.setTeacher(teacher);
        mark.setStudent(student);
        mark.setTypeMark(TypeMark.PRACA_DOMOWA);
        mark.setSubject(subject);
        mark.setValue(markString);
        mark.setTermValue(termToSave);

        markService.save(mark);
        answerToHomework.setStateAnswaerToHomework(StateAnswaerToHomework.SPRAWDZONA);

        answerToHomeworkService.saveAnswer(answerToHomework);

        model.addAttribute("termList", termList);
        model.addAttribute("valueList", valueList);
        model.addAttribute("mark", new Mark());
        model.addAttribute("answerToHomework", answerToHomework);
        return "teacher/add-mark-homework-student-answer";
    }

    //Ocena
    @GetMapping("lekcja/{id}/dodaj_nowa_ocene")
    public String addMarkToStudentView(@PathVariable("id") String id, Model model) {

        List<String> valueList = new ArrayList<>();
        valueList.add("1");
        valueList.add("1+");
        valueList.add("2-");
        valueList.add("2");
        valueList.add("2+");
        valueList.add("3-");
        valueList.add("3");
        valueList.add("3+");
        valueList.add("4-");
        valueList.add("4");
        valueList.add("4+");
        valueList.add("5-");
        valueList.add("5");
        valueList.add("5+");
        valueList.add("6");

        List<Term> termList = new ArrayList<>();
        termList.add(Term.SEMESTR_I);
        termList.add(Term.SEMESTR_II);

        List<TypeMark> typeMarkList = new ArrayList<>();
        typeMarkList.add(TypeMark.SPRAWDZIAN);
        typeMarkList.add(TypeMark.KARTKÓWKA);
        typeMarkList.add(TypeMark.PRACA_DOMOWA);
        typeMarkList.add(TypeMark.AKTYWNOŚĆ);
        typeMarkList.add(TypeMark.ODPOWIEDŹ);
        typeMarkList.add(TypeMark.INNE);

        LessonHour lessonHour = lessonHourService.findAllByid(Integer.parseInt(id));

        List<Student> studentList = studentService.findStudentsByStudentsClassGroup(lessonHour.getClassGroup());


        //wyswietlenie ocen klasy
        Subject subject = lessonHour.getSubject();
        Term term = Term.SEMESTR_I;

        List<Student> studentsToMarks = studentService.findStudentsByStudentsClassGroup(lessonHour.getClassGroup()).stream().sorted(Comparator.comparing(Student::getLastName,String::compareToIgnoreCase)).collect(Collectors.toList());

        HashMap<Student, List<Mark>> hashMap = new HashMap<>();
        for(Student student : studentsToMarks){
            List<Mark> marksList = markService.findAllByStudentAndSubject(student,subject);
            hashMap.put(student,marksList);
        }

        model.addAttribute("hashMap", hashMap);
        model.addAttribute("studentList", studentList);
        model.addAttribute("typeMarkList", typeMarkList);
        model.addAttribute("termList", termList);
        model.addAttribute("valueList", valueList);
        model.addAttribute("mark", new Mark());
        model.addAttribute("lessonHour", lessonHour);
        return "teacher/add-mark-to-student";
    }

    @GetMapping("ocena_z_przedmiotu/edytuj/{id}")
    public String getCurrentMarkTeacher(@PathVariable("id") String id, Model model, Principal principal,@ModelAttribute Mark mark) {

//        Mark mark = markService.findById(Long.parseLong(id));

        List<TypeMark> typeMarkList = new ArrayList<>();
        typeMarkList.add(TypeMark.SPRAWDZIAN);
        typeMarkList.add(TypeMark.KARTKÓWKA);
        typeMarkList.add(TypeMark.PRACA_DOMOWA);
        typeMarkList.add(TypeMark.AKTYWNOŚĆ);
        typeMarkList.add(TypeMark.ODPOWIEDŹ);
        typeMarkList.add(TypeMark.INNE);


        List<String> valueList = new ArrayList<>();
        valueList.add("1");
        valueList.add("1+");
        valueList.add("2-");
        valueList.add("2");
        valueList.add("2+");
        valueList.add("3-");
        valueList.add("3");
        valueList.add("3+");
        valueList.add("4-");
        valueList.add("4");
        valueList.add("4+");
        valueList.add("5-");
        valueList.add("5");
        valueList.add("5+");
        valueList.add("6");

        model.addAttribute("valueList", valueList);
        model.addAttribute("typeMarkList",typeMarkList);
        model.addAttribute("mark",markService.findById(Long.parseLong(id)));
        return "teacher/edit_mark_student";
    }

    @PostMapping("ocena_z_przedmiotu/edytuj/{id}")
    public String markToChange(@PathVariable("id") String id, Model model,@RequestParam Map<String, String> requestParams) {

        Mark mark = markService.findById(Long.parseLong(id));

        String value = requestParams.get("value");
        String tymeMarkString = requestParams.get("typeMark.text");


        TypeMark typeMarkToSave =TypeMark.valueOf(tymeMarkString);

        mark.setTeacher(mark.getTeacher());
        mark.setSubject(mark.getSubject());
        mark.setStudent(mark.getStudent());
        LocalDateTime localDateTime = LocalDateTime.now();
        mark.setTypeMark(typeMarkToSave);
        mark.setValue(value);
        mark.setStudent(mark.getStudent());
        mark.setTermValue(mark.getTermValue());
        mark.setCreatedDateTime(localDateTime);
//
        markService.save(mark);
//        System.out.println("12MarkPo: "+ markToUpdate.getValue());
//        PersonRelatedWithSchool personRelatedWithSchool = new PersonRel atedWithSchool();
//        personRelatedWithSchool.setPesel(user.getPersonRelatedWithSchool().getPesel());
//        personRelatedWithSchool.setFirstName(user.getPersonRelatedWithSchool().getFirstName());
//        personRelatedWithSchool.setLastName(user.getPersonRelatedWithSchool().getLastName());
//        personRelatedWithSchool.setDateBirth(user.getPersonRelatedWithSchool().getDateBirth());
//        personRelatedWithSchool.setEmail(user.getPersonRelatedWithSchool().getEmail());
//        personRelatedWithSchool.setLogin(user.getPersonRelatedWithSchool().getLogin());
//        userToUpdate.setPersonRelatedWithSchool(personRelatedWithSchool);
//
//        userService.updateAccount(userToUpdate);

        List<TypeMark> typeMarkList = new ArrayList<>();
        typeMarkList.add(TypeMark.SPRAWDZIAN);
        typeMarkList.add(TypeMark.KARTKÓWKA);
        typeMarkList.add(TypeMark.PRACA_DOMOWA);
        typeMarkList.add(TypeMark.AKTYWNOŚĆ);
        typeMarkList.add(TypeMark.ODPOWIEDŹ);
        typeMarkList.add(TypeMark.INNE);


        List<String> valueList = new ArrayList<>();
        valueList.add("1");
        valueList.add("1+");
        valueList.add("2-");
        valueList.add("2");
        valueList.add("2+");
        valueList.add("3-");
        valueList.add("3");
        valueList.add("3+");
        valueList.add("4-");
        valueList.add("4");
        valueList.add("4+");
        valueList.add("5-");
        valueList.add("5");
        valueList.add("5+");
        valueList.add("6");



        model.addAttribute("mark", mark);
        model.addAttribute("valueList", valueList);
        model.addAttribute("typeMarkList",typeMarkList);

        return "redirect:/home/teacher/plan_lekcji";

    }





    @PostMapping("lekcja/{id}/dodaj_nowa_ocene")
    public String addMarkToStudent(@PathVariable("id") String id, Model model, Principal principal, @RequestParam Map<String, String> requestParams) {

        List<String> valueList = new ArrayList<>();
        valueList.add("1");
        valueList.add("1+");
        valueList.add("2-");
        valueList.add("2");
        valueList.add("2+");
        valueList.add("3-");
        valueList.add("3");
        valueList.add("3+");
        valueList.add("4-");
        valueList.add("4");
        valueList.add("4+");
        valueList.add("5-");
        valueList.add("5");
        valueList.add("5+");
        valueList.add("6");

        List<Term> termList = new ArrayList<>();
        termList.add(Term.SEMESTR_I);
        termList.add(Term.SEMESTR_II);

        List<TypeMark> typeMarkList = new ArrayList<>();
        typeMarkList.add(TypeMark.SPRAWDZIAN);
        typeMarkList.add(TypeMark.KARTKÓWKA);
        typeMarkList.add(TypeMark.PRACA_DOMOWA);
        typeMarkList.add(TypeMark.AKTYWNOŚĆ);
        typeMarkList.add(TypeMark.ODPOWIEDŹ);
        typeMarkList.add(TypeMark.INNE);

        LessonHour lessonHour = lessonHourService.findAllByid(Integer.parseInt(id));

        List<Student> studentList = studentService.findStudentsByStudentsClassGroup(lessonHour.getClassGroup());

        Teacher teacher = teacherService.findByLogin(principal.getName());

        Subject subject = lessonHour.getSubject();

        String value = requestParams.get("valueList");
        String typeTmp = requestParams.get("typeMarkList");
        String term = requestParams.get("termList");

        TypeMark typeMark = TypeMark.valueOf(typeTmp);

        Term termToSave = Term.valueOf(term);

        String studentFromList = requestParams.get("studentList");

        Student student = studentService.findById(Long.parseLong(studentFromList));


        List<Student> studentsToMarks = studentService.findStudentsByStudentsClassGroup(lessonHour.getClassGroup()).stream().sorted(Comparator.comparing(Student::getLastName,String::compareToIgnoreCase)).collect(Collectors.toList());

        HashMap<Student, List<Mark>> hashMap = new HashMap<>();
        for(Student student1 : studentsToMarks){
            List<Mark> marksList = markService.findAllByStudentAndSubject(student1,subject);
            hashMap.put(student1,marksList);
        }


        if (value.isEmpty() || typeTmp.isEmpty() || term.isEmpty()) {

            model.addAttribute("messageError", "Wypełnij wszystkie pola");
        } else {
            Mark mark = new Mark();

            mark.setTypeMark(typeMark);
            mark.setTeacher(teacher);
            mark.setSubject(subject);
            mark.setValue(value);
            mark.setTermValue(termToSave);
            mark.setStudent(student);

            markService.save(mark);

            String mailParent = student.getParent().getEmail();

            if( Objects.isNull(mailParent) || mailParent.isEmpty()){

            }else{
                LocalDate date2 = LocalDate.now();
                mailService.sendEmail(mailParent,"Dodano ocene "+ date2,"Dodano ocene "+ mark.getValue() + " z "+ mark.getTypeMark().getText() +" .Przedmiot:" + mark.getSubject().getName());
            }




            model.addAttribute("messageSuccess", "Ocena została dodana");
            return "redirect:/home/teacher/lekcja/{id}/dodaj_nowa_ocene";
        }

        model.addAttribute("hashMap",hashMap);
        model.addAttribute("studentList", studentList);
        model.addAttribute("typeMarkList", typeMarkList);
        model.addAttribute("termList", termList);
        model.addAttribute("valueList", valueList);
        model.addAttribute("mark", new Mark());
        model.addAttribute("lessonHour", lessonHour);
        return "teacher/add-mark-to-student";
    }



}
