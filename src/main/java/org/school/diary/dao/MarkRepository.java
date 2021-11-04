package org.school.diary.dao;


import org.school.diary.model.Mark;
import org.school.diary.model.Student;
import org.school.diary.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarkRepository extends JpaRepository<Mark,Long> {


    List<Mark> findAllByStudentAndSubject(Student student, Subject subject);
}
