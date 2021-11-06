package org.school.diary.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class LessonPlan {

    @Id
    @GeneratedValue
    private long id;

    private LocalDateTime startLesson;

    private LocalDateTime endLesson;

    @OneToOne
    private ClassGroup classGroup;

    @OneToOne
    private Subject subject;


}
