package org.school.diary.dao;


import org.school.diary.model.AnswearToHomework;
import org.school.diary.model.Homework;
import org.school.diary.model.common.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswearToHomeworkRepository extends JpaRepository<AnswearToHomework,Long> {


    @Override
    List<AnswearToHomework> findAll();

    List<AnswearToHomework> findAllByHomework(Homework homework);

    AnswearToHomework findById(long id );

    AnswearToHomework findByHomeworkAndStudent(Homework homework, Student student);
}
