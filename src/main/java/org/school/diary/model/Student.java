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
public class Student {

    @Id
    @GeneratedValue
    private long id;

    private String firstName;
    private String lastName;
    private Date dateBirth;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "classGroup_id", referencedColumnName = "id")
    private ClassGroup studentsClassGroup;

    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private Parent parent;

    @OneToMany(mappedBy = "student")
    private Set<Mark> marksStudent = new HashSet<>();


}
