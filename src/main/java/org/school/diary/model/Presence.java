package org.school.diary.model;


import lombok.*;
import org.school.diary.model.common.Student;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Presence {

    @Id
    @GeneratedValue
    private long id;

    @Enumerated(EnumType.STRING)
    private StatePresence statePresence;

    private Date dateOfPresence;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    private Subject subject;
}
