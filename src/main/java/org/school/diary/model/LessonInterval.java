package org.school.diary.model;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@EqualsAndHashCode
public class LessonInterval {

    @Id
    private long id;

    private LocalTime beginLesson;

    private LocalTime endLesson;


}
