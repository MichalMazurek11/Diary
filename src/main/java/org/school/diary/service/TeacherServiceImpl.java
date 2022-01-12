package org.school.diary.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.TypeMismatchException;
import org.school.diary.config.UserPrincipal;
import org.school.diary.dao.SubjectRepository;
import org.school.diary.dao.TeacherRepository;
import org.school.diary.dao.UserRepository;
import org.school.diary.dto.TeacherDTO;
import org.school.diary.model.Role;
import org.school.diary.model.Subject;
import org.school.diary.model.common.PersonRelatedWithSchool;
import org.school.diary.model.common.Teacher;
import org.school.diary.model.common.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService{


    @Autowired
    TeacherService teacherService;

    @Autowired
    UserService userService;

    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;
    private final SubjectService subjectService;
    private final RoleService roleService;
    private final UserRepository userRepository;

//    @Override
//    public void saveTeacher(Teacher teacher, LocalDate birthDate, Set<Subject> subjectSet) {
//        Set<Subject> matchesSubjects = subjectService.listAllSubject().stream().filter(subjectSet::contains).collect(Collectors.toSet());
//        Subject subject = subjectService.listAllSubject().get(0);
//        teacher.setDateBirth(birthDate);
//        teacher.setSubjects(Collections.singleton(subject));
//        teacherRepository.save(teacher);
//        //
//        subject.getTeachers().add(teacher);
//       // subject.setTeachers(subject.getTeachers().add(teacher));
//        subjectService.saveSubjects(Collections.singleton(subject));
//    }


    @Override
    public List<Teacher> listTeachers() {
        return teacherRepository.findAll();
    }

    @Override
    public void save(PersonRelatedWithSchool teacher) {
        teacherRepository.save((Teacher) teacher);
    }

    @Override
    public void saveTest(Teacher teacher) {
        teacherRepository.save(teacher);
    }

    @Override
    public void saveAllTeachers(Set<Teacher> teachers) {
        teacherRepository.saveAll(teachers);
    }

    @Override
    public List<Teacher> getTeachers() {
        return teacherRepository.findAll();
    }

    @Override
    public Teacher findByEmail(String email) {
        return teacherRepository.findByEmail(email);
    }

    @Override
    public void deleteTeacher(long teacherId) {
        teacherRepository.deleteById(teacherId);
    }

    @Override
    public Teacher findByLogin(String login) {
        return teacherRepository.findByLogin(login);
    }

    @Override
    public Teacher findTeacher() {
        PersonRelatedWithSchool probablyTeacher = ((UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getPersonRelatedWithSchool();
        if (probablyTeacher instanceof Teacher){
            return (Teacher) probablyTeacher;
        }
        throw new TypeMismatchException("Zalogowana osoba nie jest nauczycielem");
    }

    @Override
    public Teacher findById(long id) {
        return teacherRepository.findById(id);
    }

    @Override
    public void saveTeacher(LocalDate birthDate, TeacherDTO teacherDTO, Set<Subject> subjectSet) {

        Teacher teacher = new Teacher();
        teacher.setLogin(teacherDTO.getPesel());
        if(teacherDTO.getEmail().isEmpty()){
            teacher.setEmail(null);
        }else{
            teacher.setEmail(teacherDTO.getEmail());
        }
        teacher.setFirstName(teacherDTO.getFirstName());
        teacher.setLastName(teacherDTO.getLastName());
        teacher.setDateBirth(birthDate);
        teacher.setPesel(teacherDTO.getPesel());
//        teacher.setSubjects(subjectSet);
//
//        System.out.println("id ucznia: "+teacher.getId());
//        System.out.println("id2: "+teacher.getNoteToJournalsTeacher());
        teacherService.save(teacher);
        System.out.println("id ucznia: "+teacher.getId());
        System.out.println("id2: "+teacher.getNoteToJournalsTeacher());

        Set<Subject> foundedSubjects = subjectService.listAllSubject().stream().filter(o -> subjectSet.stream().anyMatch(subject -> subject.getName().equals(o.getName()))).collect(Collectors.toSet());
        foundedSubjects.forEach(subject -> subject.getTeachers().add(teacher));
        subjectService.saveSubjects(foundedSubjects);
        User user = new User();
        Role role1 = roleService.findRoleByName("TEACHER");
        user.setRoles(Collections.singleton(role1));
        user.setPassword(teacherDTO.getPassword());
        user.setPersonRelatedWithSchool(teacher);
        userService.save(user);


    }

}
