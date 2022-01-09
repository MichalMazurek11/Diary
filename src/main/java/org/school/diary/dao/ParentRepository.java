package org.school.diary.dao;


import org.school.diary.model.common.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentRepository extends JpaRepository<Parent,Long> {

    Parent findByLogin(String login);


}
