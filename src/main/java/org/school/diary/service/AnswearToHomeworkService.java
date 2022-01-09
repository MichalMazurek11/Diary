package org.school.diary.service;

import org.school.diary.model.*;
import org.school.diary.model.common.Student;



import java.util.List;


public interface AnswearToHomeworkService {


    List<AnswearToHomework> findAll();

    List<AnswearToHomework> generateAndSaveEmptyAnswear(ClassGroup classGroup,  Homework homework);

    List<AnswearToHomework> findAllByHomework(Homework homework);

    void saveAllAnswear(List<AnswearToHomework> answearToHomeworks);

    AnswearToHomework findById(long id );

    AnswearToHomework findByHomeworkAndStudent(Homework homework, Student student);

    void saveAnswear(AnswearToHomework answearToHomework);
}
