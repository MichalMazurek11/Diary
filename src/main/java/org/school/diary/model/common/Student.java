package org.school.diary.model.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.school.diary.model.ClassGroup;
import org.school.diary.model.Mark;
import org.school.diary.model.Parent;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Student extends PersonRelatedWithSchool{

    @ManyToOne
    @JoinColumn(name = "classGroup_id", referencedColumnName = "id")
    private ClassGroup studentsClassGroup;

    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private Parent parent;

    @OneToMany(mappedBy = "student")
    private Set<Mark> marksStudent = new HashSet<>();


}
