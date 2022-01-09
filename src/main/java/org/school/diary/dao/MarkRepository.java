package org.school.diary.dao;


import org.school.diary.model.Mark;
import org.school.diary.model.common.Student;
import org.school.diary.model.Subject;
import org.school.diary.model.enums.Term;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarkRepository extends JpaRepository<Mark,Long> {


    List<Mark> findAllByStudentAndSubject(Student student, Subject subject);

    List<Mark> findAllByStudentAndSubjectAndTermValue(Student student, Subject subject, Term term);



    List<Mark> findAllByStudent(Student student);

    List<Mark> findAllBySubject(Subject subject);

    Mark findById(long id );

}
