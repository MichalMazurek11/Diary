package org.school.diary.model.common;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.school.diary.model.common.PersonRelatedWithSchool;
import org.school.diary.model.common.Student;
import org.school.diary.model.common.User;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Parent extends PersonRelatedWithSchool {


    @OneToMany(mappedBy = "parent")
    private Set<Student> students = new HashSet<>();
}
