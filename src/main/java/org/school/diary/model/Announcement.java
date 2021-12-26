package org.school.diary.model;


import lombok.*;
import org.school.diary.model.common.Director;
import org.school.diary.model.common.Parent;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Announcement {

    @Id
    @GeneratedValue
    private int id;

    @Column(length = 1000)
    private String text;

    private LocalDate dateTime;

    @ManyToOne
    @JoinColumn(name = "director_id", referencedColumnName = "id")
    private Director directorsAnnouncement;
}


