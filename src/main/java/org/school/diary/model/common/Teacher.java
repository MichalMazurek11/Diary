package org.school.diary.model.common;

import lombok.*;
import org.school.diary.model.Exam;
import org.school.diary.model.Mark;
import org.school.diary.model.NoteToJournal;
import org.school.diary.model.Subject;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Teacher extends PersonRelatedWithSchool{

    public Teacher(String firstName, String lastName) {
        super(firstName, lastName);
    }

    @OneToMany(mappedBy = "teacher")
    private Set<Mark> marksTeacher = new HashSet<>();

    @ManyToMany(mappedBy = "teachers")
    private Set<Subject> subjects;

    @OneToMany(mappedBy = "teacher")
    private Set<Exam> examsTeacher = new HashSet<>();

    @OneToMany(mappedBy = "teacher")
    private Set<NoteToJournal> noteToJournalsTeacher = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(getId(), teacher.getId()) && Objects.equals(getEmail(), teacher.getEmail());
    }


}
