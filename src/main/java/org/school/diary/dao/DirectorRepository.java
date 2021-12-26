package org.school.diary.dao;


import org.school.diary.model.common.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorRepository extends JpaRepository<Director,Long> {

    Director findById(long id);

    Director findByLogin(String login);
}
