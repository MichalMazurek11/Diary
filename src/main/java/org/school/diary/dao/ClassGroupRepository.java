package org.school.diary.dao;

import org.school.diary.model.ClassGroup;
import org.school.diary.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClassGroupRepository extends JpaRepository<ClassGroup,Long> {


    ClassGroup findById(long id);
}
