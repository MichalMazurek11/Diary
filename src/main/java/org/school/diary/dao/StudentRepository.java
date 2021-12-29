package org.school.diary.dao;


import org.school.diary.model.ClassGroup;
import org.school.diary.model.common.Student;
import org.school.diary.model.common.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student,UUID> {

    Student findById(long id);

//    Student findByUser(User user);

//    @Query("SELECT n FROM Student n WHERE n.id= ?1")
//    Student findOneByUuid(UUID studentUuid);

    List<Student> findStudentsByStudentsClassGroup(ClassGroup classGroup);

    List<Student> findByStudentsClassGroup(ClassGroup classGroup);

//    Student findStudentById(UUID uuid);

    Student findByEmail(String email);

    Student findByLogin(String login);
}
