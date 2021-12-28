package org.school.diary.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Getter
@Setter
public class Weekday {

    @Id
    private long id;

    private String dayName;


}


