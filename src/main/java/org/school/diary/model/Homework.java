package org.school.diary.model;


import lombok.*;
import org.school.diary.model.common.Student;
import org.school.diary.model.common.Teacher;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Homework {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 3000)
    private String taskDescription;

    private LocalDate createdDateTask;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDateTask;

    @ManyToOne
    @JoinColumn(name = "classGroup_id", referencedColumnName = "id")
    private ClassGroup homeworksClassGroup;

    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    private Subject homewroksSubject;

    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Teacher homeworkTeacher;

    @OneToMany(mappedBy = "homework")
    private Set<AnswerToHomework> answerToHomeworkSet= new HashSet<>();


    @Override
    public String toString() {
        return "Homework{" +
                "id=" + id +
                ", taskDescription='" + taskDescription + '\'' +
                ", createdDateTask=" + createdDateTask +
                ", endDateTask=" + endDateTask +
                ", homeworksClassGroup=" + homeworksClassGroup +
                ", homewroksSubject=" + homewroksSubject +
                ", homeworkTeacher=" + homeworkTeacher +
                ", answerToHomeworkSet=" + answerToHomeworkSet +
                '}';
    }
}
