package org.school.diary.model;


import lombok.*;
import org.school.diary.model.common.Student;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
@RequiredArgsConstructor
public class Presence {

    @Id
    @GeneratedValue
    private long id;

    @NonNull
    @Enumerated(EnumType.STRING)
    private StatePresence state;

    @NonNull
    private LocalDateTime dateOfPresence;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "lesson_hour_id", referencedColumnName = "id")
    private LessonHour lessonHour;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Presence presence = (Presence) o;
        return student.getPesel().equals(presence.student.getPesel()) && lessonHour.equals(presence.lessonHour);
    }
}
