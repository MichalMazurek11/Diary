package org.school.diary.dao;

import org.school.diary.model.ClassGroup;
import org.school.diary.model.Homework;
import org.school.diary.model.common.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomeworkRepository extends JpaRepository<Homework,Long> {

    @Override
    List<Homework> findAll();

    List<Homework> findHomeworkByHomeworksClassGroup(ClassGroup classGroup);

    List<Homework> findHomeworkByHomeworkTeacher(Teacher teacher);

    Homework findById(long id);
}

