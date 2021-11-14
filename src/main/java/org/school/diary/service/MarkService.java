package org.school.diary.service;

import org.school.diary.model.Mark;
import org.school.diary.model.common.Student;
import org.school.diary.model.Subject;

import java.util.List;

public interface MarkService {


    public void save(Mark mark);

    List<Mark> findAllByStudentAndSubject(Student student, Subject subject);

//    public void deleteMark(long id);
}
