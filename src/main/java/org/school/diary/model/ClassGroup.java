package org.school.diary.model;

import lombok.*;
import org.school.diary.model.common.Student;
import org.school.diary.model.common.Teacher;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class ClassGroup {

    @Id
    @GeneratedValue
    private long id;

    @NonNull
    private String name;

    @OneToOne()
    @JoinColumn(name = "supervisor_id", referencedColumnName = "id")
    private Teacher supervisor;

    @OneToMany(mappedBy = "studentsClassGroup")
    private Set<Student> students = new HashSet<>();


    @Override
    public String toString() {
        return "ClassGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", supervisor=" + supervisor +
                ", students=" + students +
                '}';
    }
}
