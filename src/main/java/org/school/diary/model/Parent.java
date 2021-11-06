package org.school.diary.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Parent extends User{

//    @Id
//    @GeneratedValue
//    private long id;

    private String firstName;
    private String lastName;
    private Date dateBirth;
    private String phoneNumber;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    private User user;


    @OneToMany(mappedBy = "parent")
    private Set<Student> students = new HashSet<>();
}
