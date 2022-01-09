package org.school.diary.model;

import lombok.*;
import org.school.diary.model.common.Student;
import org.school.diary.model.common.Teacher;
import org.school.diary.model.enums.TypeNote;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class NoteToJournal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Date date;

    @NonNull
    @Enumerated(EnumType.STRING)
    private TypeNote typeNote;

    @ManyToOne
    @JoinColumn(name = "classGroup_id", referencedColumnName = "id")
    private ClassGroup classGroup;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Teacher teacher;



}
