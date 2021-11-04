package org.school.diary.service;

import org.school.diary.dao.ClassGroupRepository;
import org.school.diary.dao.StudentRepository;
import org.school.diary.model.ClassGroup;
import org.school.diary.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
public class ClassGroupServiceImpl implements ClassGroupService {

    @Autowired
    private ClassGroupRepository classGroupRepository;

    @Autowired
    StudentRepository studentRepository;


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


}
