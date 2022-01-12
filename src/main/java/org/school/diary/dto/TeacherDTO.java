package org.school.diary.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.school.diary.model.Mark;
import org.school.diary.model.Subject;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;



@NoArgsConstructor
@AllArgsConstructor
@Data
public class TeacherDTO {


    @NotBlank(message = "Pole nie może byc puste")
    private String firstName;

    @Size(min=6,  message = "Haslo musi mieć minimum 6 znaków")
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





}
