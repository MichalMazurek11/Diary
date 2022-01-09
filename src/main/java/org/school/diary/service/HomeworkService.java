package org.school.diary.service;

import org.school.diary.model.ClassGroup;
import org.school.diary.model.Homework;
import org.school.diary.model.Role;
import org.school.diary.model.common.Teacher;

import java.util.List;

public interface HomeworkService {

    void save(Homework homework);

    List<Homework> findHomeworkByHomeworksClassGroup(ClassGroup classGroup);

    List<Homework> findHomeworkByHomeworkTeacher(Teacher teacher);

    Homework findById(long id);
}
