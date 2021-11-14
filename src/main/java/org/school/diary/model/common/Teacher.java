package org.school.diary.model.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.school.diary.model.Mark;
import org.school.diary.model.Subject;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Teacher extends PersonRelatedWithSchool{

    @OneToMany(mappedBy = "teacher")
    private Set<Mark> marksTeacher = new HashSet<>();

    @OneToMany()
    private Set<Subject> subjects;
}
