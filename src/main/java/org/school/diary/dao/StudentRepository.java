package org.school.diary.dao;


import org.school.diary.model.Student;
import org.school.diary.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    Student findById(long id);

//    Student findByUser(User user);
}
