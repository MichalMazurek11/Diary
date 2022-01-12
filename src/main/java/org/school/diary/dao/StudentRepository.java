package org.school.diary.dao;


import org.school.diary.model.ClassGroup;
import org.school.diary.model.common.Parent;
import org.school.diary.model.common.Student;
import org.school.diary.model.common.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    Student findById(long id);

    List<Student> findStudentsByStudentsClassGroup(ClassGroup classGroup);

    Student findByParent(Parent parent);

    Student findByLogin(String login);

    @Query("Select e from Student e WHERE e.lastName like CONCAT(:firstLetter,'%')")
    List<Student> findByFirstLetter(@Param("firstLetter") String letter);

}



