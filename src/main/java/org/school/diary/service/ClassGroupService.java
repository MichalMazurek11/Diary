package org.school.diary.service;

import org.school.diary.model.ClassGroup;
import org.school.diary.model.Student;
import org.school.diary.model.User;

import java.util.List;
import java.util.Set;

public interface ClassGroupService {

    public void addClassGroup(ClassGroup classGroup);

    public List<ClassGroup> listClassGroups();

    ClassGroup findById(long id);
}
