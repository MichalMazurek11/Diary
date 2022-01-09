package org.school.diary.model;


import lombok.*;
import org.school.diary.model.common.Director;

import javax.persistence.*;
import java.time.LocalDate;

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

    public enum StatePresence {
        OBECNY, NIEOBECNY, SPÓŹNIONY, USPRAWIEDLIWIONY
    }
}


