package org.school.diary.service;

import org.school.diary.model.Mark;
import org.school.diary.model.common.Student;
import org.school.diary.model.Subject;
import org.school.diary.model.enums.Term;

import java.util.List;

public interface MarkService {


    public void save(Mark mark);

    List<Mark> findAllByStudentAndSubject(Student student, Subject subject);

    List<Mark> findAllByStudent(Student student);

//    public void deleteMark(long id);

    List<Mark> findAllBySubject(Subject subject);

    Mark findById(long id );


    List<Mark> findAllByStudentAndSubjectAndTermValue(Student student, Subject subject, Term term);
}


