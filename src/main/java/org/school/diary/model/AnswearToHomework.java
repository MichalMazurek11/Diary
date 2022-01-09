package org.school.diary.model;

import lombok.*;
import org.school.diary.model.common.Student;
import org.school.diary.model.enums.StateAnswaerToHomework;

import javax.persistence.*;


@NoArgsConstructor
@Getter
@Setter
@Entity
@RequiredArgsConstructor
public class AnswearToHomework {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(length = 3000)
    private String answearForHomework;

    @NonNull
    @Enumerated(EnumType.STRING)
    private StateAnswaerToHomework stateAnswaerToHomework;


    @ManyToOne
    @JoinColumn(name = "homework_id", referencedColumnName = "id")
    private Homework homework;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;


    public AnswearToHomework(@NonNull String answearForHomework, @NonNull StateAnswaerToHomework stateAnswaerToHomework, Homework homework, Student student) {
        this.answearForHomework = answearForHomework;
        this.stateAnswaerToHomework = stateAnswaerToHomework;
        this.homework = homework;
        this.student = student;
    }

    @Override
    public String toString() {
        return "AnswearToHomework{" +
                "id=" + id +
                ", answearForHomework='" + answearForHomework + '\'' +
                ", stateAnswaerToHomework=" + stateAnswaerToHomework +
                ", homework=" + homework +
                ", student=" + student +
                '}';
    }
}
