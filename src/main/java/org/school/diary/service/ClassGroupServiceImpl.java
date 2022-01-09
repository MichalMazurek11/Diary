package org.school.diary.service;

import lombok.AllArgsConstructor;
import org.school.diary.dao.ClassGroupRepository;
import org.school.diary.dao.StudentRepository;
import org.school.diary.model.ClassGroup;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
@AllArgsConstructor
public class ClassGroupServiceImpl implements ClassGroupService {

    private final ClassGroupRepository classGroupRepository;
//    private final StudentRepository studentRepository;


    @Override
    public void addClassGroup(ClassGroup classGroup) {
            classGroupRepository.save(classGroup);
    }

    @Override
    public List<ClassGroup> listClassGroups() {
        return classGroupRepository.findAll();
    }

    @Override
    public ClassGroup findById(long id) {
        return classGroupRepository.findById(id);
    }

    @Override
    public void saveClassGroups(Set<ClassGroup> classGroups) {
        classGroupRepository.saveAll(classGroups);
    }

    @Override
    public void deleteClassGroup(long classGroup) {
        classGroupRepository.deleteById(classGroup);
    }


}
