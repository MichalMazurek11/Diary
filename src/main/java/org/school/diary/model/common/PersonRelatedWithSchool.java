package org.school.diary.model.common;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PersonRelatedWithSchool {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String firstName;
    private String login;

    @Column(unique=true)
    private String email;
    private String lastName;
    private LocalDate dateBirth;
    @Column(unique=true)
    private String pesel;

    public PersonRelatedWithSchool(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }



//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        PersonRelatedWithSchool that = (PersonRelatedWithSchool) o;
//        return Objects.equals(id, that.id) && Objects.equals(firstName, that.firstName) && Objects.equals(email, that.email) && Objects.equals(lastName, that.lastName) && Objects.equals(dateBirth, that.dateBirth) && Objects.equals(pesel, that.pesel);
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonRelatedWithSchool that = (PersonRelatedWithSchool) o;
        return Objects.equals(id, that.id) && Objects.equals(firstName, that.firstName) && Objects.equals(login, that.login) && Objects.equals(email, that.email) && Objects.equals(lastName, that.lastName) && Objects.equals(dateBirth, that.dateBirth) && Objects.equals(pesel, that.pesel);
    }

//    @Override
//    public String toString() {
//        return "PersonRelatedWithSchool{" +
//                "id=" + id +
//                ", firstName='" + firstName + '\'' +
//                ", login='" + login + '\'' +
//                ", email='" + email + '\'' +
//                ", lastName='" + lastName + '\'' +
//                ", dateBirth=" + dateBirth +
//                ", pesel='" + pesel + '\'' +
//                '}';
//    }
}


