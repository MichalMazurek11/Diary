package org.school.diary.model.common;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PersonRelatedWithSchool {

    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String email;
    private String lastName;
    private LocalDate dateBirth;
    private String pesel;

    public PersonRelatedWithSchool(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public PersonRelatedWithSchool(String firstName, String email, String lastName, LocalDate dateBirth, String pesel) {
        this.firstName = firstName;
        this.email = email;
        this.lastName = lastName;
        this.dateBirth = dateBirth;
        this.pesel = pesel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonRelatedWithSchool that = (PersonRelatedWithSchool) o;
        return Objects.equals(id, that.id) && Objects.equals(firstName, that.firstName) && Objects.equals(email, that.email) && Objects.equals(lastName, that.lastName) && Objects.equals(dateBirth, that.dateBirth) && Objects.equals(pesel, that.pesel);
    }

}
