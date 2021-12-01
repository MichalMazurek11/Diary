package org.school.diary.service;

import lombok.RequiredArgsConstructor;
import org.school.diary.dao.ClassGroupRepository;
import org.school.diary.dao.ClassRoomRepository;
import org.school.diary.dao.StudentRepository;
import org.school.diary.model.ClassGroup;
import org.school.diary.model.ClassRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class ClassRoomServiceImpl implements ClassRoomService {

    private final ClassRoomRepository classRoomRepository;

    @Override
    public void addClassRooms(Collection<ClassRoom> classRooms) {
        classRoomRepository.saveAll(classRooms);
    }

    @Override
    public void addClassRoom(ClassRoom classRoom) {
       classRoomRepository.save(classRoom);
    }

    @Override
    public List<ClassRoom> getAll() {
        return classRoomRepository.findAll();
    }
}
