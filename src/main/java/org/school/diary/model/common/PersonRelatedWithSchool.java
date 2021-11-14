package org.school.diary.model.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PersonRelatedWithSchool {

    @Id
    @GeneratedValue
    private UUID id;
    private String firstName;
    private String email;
    private String lastName;
    private LocalDate dateBirth;
    private String pesel;

    public PersonRelatedWithSchool(String firstName, String email, String lastName, LocalDate dateBirth, String pesel) {
        this.firstName = firstName;
        this.email = email;
        this.lastName = lastName;
        this.dateBirth = dateBirth;
        this.pesel = pesel;
    }
}
