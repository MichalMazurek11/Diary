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
public class AnswerToHomework {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(length = 3000)
    private String answerForHomework;

    @NonNull
    @Enumerated(EnumType.STRING)
    private StateAnswaerToHomework stateAnswaerToHomework;


    @ManyToOne
    @JoinColumn(name = "homework_id", referencedColumnName = "id")
    private Homework homework;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;


    public AnswerToHomework(@NonNull String answerForHomework, @NonNull StateAnswaerToHomework stateAnswaerToHomework, Homework homework, Student student) {
        this.answerForHomework = answerForHomework;
        this.stateAnswaerToHomework = stateAnswaerToHomework;
        this.homework = homework;
        this.student = student;
    }

    @Override
    public String toString() {
        return "AnswerToHomework{" +
                "id=" + id +
                ", answerForHomework='" + answerForHomework + '\'' +
                ", stateAnswaerToHomework=" + stateAnswaerToHomework +
                ", homework=" + homework +
                ", student=" + student +
                '}';
    }
}
