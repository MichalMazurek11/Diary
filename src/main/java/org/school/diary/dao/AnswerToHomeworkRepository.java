package org.school.diary.dao;


import org.school.diary.model.AnswerToHomework;
import org.school.diary.model.Homework;
import org.school.diary.model.common.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerToHomeworkRepository extends JpaRepository<AnswerToHomework,Long> {


    @Override
    List<AnswerToHomework> findAll();

    List<AnswerToHomework> findAllByHomework(Homework homework);

    AnswerToHomework findById(long id );

    AnswerToHomework findByHomeworkAndStudent(Homework homework, Student student);
}
