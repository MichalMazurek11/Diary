package org.school.diary.model.common;

import lombok.*;
import org.school.diary.model.Exam;
import org.school.diary.model.Mark;
import org.school.diary.model.Subject;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Teacher extends PersonRelatedWithSchool{

    public Teacher(String firstName, String lastName) {
        super(firstName, lastName);
    }

    @OneToMany(mappedBy = "teacher")
    private Set<Mark> marksTeacher = new HashSet<>();

    @ManyToMany(mappedBy = "teachers")
    private Set<Subject> subjects;

    @OneToMany(mappedBy = "teacher")
    private Set<Exam> examsTeacher = new HashSet<>();
}
