package org.school.diary.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClassGroup {

    @Id
    @GeneratedValue
    private long id;


    private String name;

    @OneToOne(cascade = CascadeType.REFRESH)
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
