package org.school.diary.dao;


import org.school.diary.model.Mark;
import org.school.diary.model.Subject;
import org.school.diary.model.common.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject,Long> {
    //Znajdz nauczycielka
    Optional<Teacher> findByTeachersContains(Teacher teacher);

    Subject findById(long id);

    Subject findByName(String name);
}
