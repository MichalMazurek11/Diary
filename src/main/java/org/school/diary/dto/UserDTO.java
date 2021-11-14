package org.school.diary.dto;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {

    @NotBlank(message = "Pole nie może byc puste")
    private String firstName;

    @NotBlank(message = "Pole nie może byc puste")
    private String lastName;

    @NotBlank(message = "Pole nie może byc puste")
    @Email(message = "Musisz podac email")
    private String email;

    @Size(min=1, max=20, message = "Haslo musi mieć minimum 1 znak")
    private String password;

    @Pattern(regexp = "^[0-9]{9}$", message = "Podaj PESEL")
    private String pesel;

    @NotBlank(message = "Wybierz date urodzin")
    private String birthDate;

    @NotBlank(message = "Wybierz role")
    private String personRole;


    @Override
    public String toString() {
        return "UserDTO{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", pesel='" + pesel + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", personRole='" + personRole + '\'' +
                '}';
    }

}
