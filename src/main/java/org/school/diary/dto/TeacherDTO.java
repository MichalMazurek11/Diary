package org.school.diary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.school.diary.model.Exam;
import org.school.diary.model.Mark;
import org.school.diary.model.Subject;

import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;



@NoArgsConstructor
@AllArgsConstructor
@Data
public class TeacherDTO {


    private Long id;

    private String firstName;

    @Size(min=1, max=20, message = "Haslo musi mieć minimum 1 znak")
    private String password;

    private String login;

    private String email;

    private String lastName;

    private LocalDate dateBirth;

    private String pesel;

    @OneToMany(mappedBy = "teacher")
    private Set<Mark> marksTeacher = new HashSet<>();

    @ManyToMany(mappedBy = "teachers")
    private Set<Subject> subjects;

    @OneToMany(mappedBy = "teacher")
    private Set<Exam> examsTeacher = new HashSet<>();


}
