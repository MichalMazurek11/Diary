package org.school.diary.model;

import lombok.*;
import org.school.diary.model.enums.ClassRoomDuty;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class ClassRoom {

    @Id
    @GeneratedValue
    private int id;
    @NonNull
    private String className;
    @NonNull
    @Enumerated(EnumType.STRING)
    private ClassRoomDuty classRoomDuty;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassRoom classRoom = (ClassRoom) o;
        return id == classRoom.id && className.equals(classRoom.className) && classRoomDuty == classRoom.classRoomDuty;
    }
}
