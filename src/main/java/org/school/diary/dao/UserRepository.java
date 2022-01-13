package org.school.diary.dao;


import org.school.diary.model.common.PersonRelatedWithSchool;
import org.school.diary.model.common.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


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

    @Query("from User u " +
            "join PersonRelatedWithSchool prws on u.personRelatedWithSchool.id=prws.id " +
            "where prws.id=:id")
    User test(long id);

    User findByPersonRelatedWithSchool(PersonRelatedWithSchool personRelatedWithSchool);

    boolean existsUserByPersonRelatedWithSchoolEmail(String email);

    boolean existsUserByPersonRelatedWithSchoolPesel(String pesel);


//    @Query("SELECT COUNT(c)> 0 FROM User c WHERE c.personRelatedWithSchool.login = :pid")
//    Boolean existContractForPerson(@Param("pid") String personId);

    boolean existsByPersonRelatedWithSchool_Pesel(String pesel);
//    Query("select if(exists(select * from user where user.id = 10) ,1,0"))
}
