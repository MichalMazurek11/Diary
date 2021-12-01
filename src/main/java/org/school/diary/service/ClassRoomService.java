package org.school.diary.service;

import org.school.diary.model.ClassRoom;

import java.util.Collection;
import java.util.List;

public interface ClassRoomService {

    void addClassRooms(Collection<ClassRoom> classRooms);
    void addClassRoom(ClassRoom classRoom);
    List<ClassRoom> getAll();

}
