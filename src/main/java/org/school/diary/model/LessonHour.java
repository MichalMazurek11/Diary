package org.school.diary.model;

import lombok.*;
import org.school.diary.model.common.Teacher;

import javax.persistence.*;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Entity
public class LessonHour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    private Subject subject;

    @NonNull
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "lesson_interval_id", referencedColumnName = "id")
    private LessonInterval lessonInterval;

    @NonNull
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "class_group_id", referencedColumnName = "id")
    private ClassGroup classGroup;

    @NonNull
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Teacher teacher;

    @NonNull
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "day_of_week_id", referencedColumnName = "id")
    private Weekday weekday;

    @NonNull
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "class_room_id", referencedColumnName = "id")
    private ClassRoom classRoom;


    @Override
    public String toString() {
        return "LessonHour{" +
                "id=" + id +
                ", subject=" + subject +
                ", lessonInterval=" + lessonInterval +
                ", classGroup=" + classGroup +
                ", teacher=" + teacher +
                ", weekday=" + weekday +
                '}';
    }
}
