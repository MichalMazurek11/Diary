package org.school.diary.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.school.diary.model.common.Student;
import org.school.diary.model.common.Teacher;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Mark {


    @Id
    @GeneratedValue
    private long id;

    // 3+, 4- itp
    private String value;

    //oceny pierwszy semestr
    private String valueTermOne;
    //oceny drugi semestr
    private String valueTermTwo;

    //rodzaj kartkowka/sprawdzian itp
    private String type;


    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    private Subject subject;



}
