package org.school.diary.model.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.school.diary.model.Announcement;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Director extends PersonRelatedWithSchool{

    @OneToMany(mappedBy = "directorsAnnouncement")
    private Set<Announcement> announcements = new HashSet<>();
}
