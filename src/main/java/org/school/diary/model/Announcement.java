package org.school.diary.model;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

}
