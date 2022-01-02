package org.school.diary.dao;


import org.school.diary.model.common.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query("from User u " +
            "join PersonRelatedWithSchool prws on u.personRelatedWithSchool.id=prws.id " +
            "where prws.email=:email")
    User findByEmail(String email);


    @Query("from User u " +
            "join PersonRelatedWithSchool prws on u.personRelatedWithSchool.id=prws.id " +
            "where prws.login=:login")
    User findByLogin(String login);

    @Query("from User u where u.id=:id")
    User findById(long id);

    User findUserById(long id);

    @Query("from User u " +
            "join PersonRelatedWithSchool prws on u.personRelatedWithSchool.id=prws.id " +
            "where prws.id=:id")
    User test(long id);


}
