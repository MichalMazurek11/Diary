package org.school.diary.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.school.diary.model.Exam;
import org.school.diary.model.Mark;
import org.school.diary.model.Subject;

import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;



@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TeacherDTO {


    private Long id;
    @NotBlank(message = "Pole nie może byc puste")
    private String firstName;

    @Size(min=1, max=20, message = "Haslo musi mieć minimum 1 znak")
    private String password;

    private String login;

    private String email;

    @NotBlank(message = "Pole nie może byc puste")
    private String lastName;


    private LocalDate dateBirth;

    @Pattern(regexp = "^[0-9]{11}$", message = "Podaj PESEL")
    private String pesel;


    private Set<Mark> marksTeacher = new HashSet<>();

    private Set<Subject> subjects;


    private Set<Exam> examsTeacher = new HashSet<>();


}
