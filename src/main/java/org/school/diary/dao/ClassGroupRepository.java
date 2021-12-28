package org.school.diary.dao;

import org.school.diary.model.ClassGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ClassGroupRepository extends JpaRepository<ClassGroup,Long> {

    ClassGroup findByName(String name);

    ClassGroup findById(long id);


//    @Query(value =  "SELECT c FROM ClassGroup c WHERE c. = ?1")
//    ClassGroup findClassGroupBys
}
