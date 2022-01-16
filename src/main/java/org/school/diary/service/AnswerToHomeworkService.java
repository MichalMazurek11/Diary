package org.school.diary.service;

import org.school.diary.model.*;
import org.school.diary.model.common.Student;



import java.util.List;


public interface AnswerToHomeworkService {


    List<AnswerToHomework> findAll();

    List<AnswerToHomework> generateAndSaveEmptyAnswer(ClassGroup classGroup,  Homework homework);

    List<AnswerToHomework> findAllByHomework(Homework homework);

    void saveAllAnswer(List<AnswerToHomework> answerToHomeworks);

    AnswerToHomework findById(long id );

    AnswerToHomework findByHomeworkAndStudent(Homework homework, Student student);

    void saveAnswer(AnswerToHomework answerToHomework);
}
