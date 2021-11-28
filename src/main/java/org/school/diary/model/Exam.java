package org.school.diary.model;

import lombok.*;
import org.school.diary.model.common.Student;
import org.school.diary.model.common.Teacher;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Exam {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    private Date date;


    @ManyToOne
    @JoinColumn(name = "classgroup_id", referencedColumnName = "id")
    private ClassGroup classGroup;

    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    private Subject subject;

}
