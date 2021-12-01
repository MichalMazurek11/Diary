package org.school.diary.dao;

import org.school.diary.model.ClassRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRoomRepository extends JpaRepository<ClassRoom,Integer> {
}
