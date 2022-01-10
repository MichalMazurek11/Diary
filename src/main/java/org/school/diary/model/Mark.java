package org.school.diary.model;


import lombok.*;
import org.school.diary.model.common.Student;
import org.school.diary.model.common.Teacher;
import org.school.diary.model.enums.Term;
import org.school.diary.model.enums.TypeMark;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Mark {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 3+, 4- itp
    private String value;

    @NonNull
    @Enumerated(EnumType.STRING)
    private Term termValue;


    //rodzaj kartkowka/sprawdzian itp
    @NonNull
    @Enumerated(EnumType.STRING)
    private TypeMark typeMark;

    private LocalDateTime createdDateTime = LocalDateTime.now();

    private String proposedMark ;

    private String markForTermTwo ;


    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    private Subject subject;


    public Mark(String value, @NonNull Term termValue, @NonNull TypeMark typeMark, LocalDateTime createdDateTime, Student student, Teacher teacher, Subject subject) {
        this.value = value;
        this.termValue = termValue;
        this.typeMark = typeMark;
        this.createdDateTime = createdDateTime;
        this.student = student;
        this.teacher = teacher;
        this.subject = subject;
    }

//    @Override
//    public String toString() {
//        return "Mark{" +
//                "id=" + id +
//                ", value='" + value + '\'' +
//                ", termValue=" + termValue +
//                ", typeMark=" + typeMark +
//                ", createdDateTime=" + createdDateTime +
//                ", proposedMark='" + proposedMark + '\'' +
//                ", markForTermTwo='" + markForTermTwo + '\'' +
//                ", student=" + student +
//                ", teacher=" + teacher +
//                ", subject=" + subject +
//                '}';
//    }
}
