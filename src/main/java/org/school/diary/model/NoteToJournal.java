package org.school.diary.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.school.diary.model.common.Student;
import org.school.diary.model.common.Teacher;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class NoteToJournal {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    private Date date;

    private String type;

    @ManyToOne
    @JoinColumn(name = "classGroup_id", referencedColumnName = "id")
    private ClassGroup classGroup;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;


    public NoteToJournal(long id, String name, Date date, String type, ClassGroup classGroup) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.type = type;
        this.classGroup = classGroup;
    }
}
