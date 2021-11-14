package org.school.diary.model.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;


@AllArgsConstructor
@Getter
@Setter
@Entity
public class Director extends PersonRelatedWithSchool{

}
