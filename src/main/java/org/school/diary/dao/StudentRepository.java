package org.school.diary.dao;


import org.school.diary.model.ClassGroup;
import org.school.diary.model.common.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    Student findById(long id);

//    Student findByUser(User user);

    List<Student> findStudentsByStudentsClassGroup(ClassGroup classGroup);

    List<Student> findByStudentsClassGroup(ClassGroup classGroup);
}
