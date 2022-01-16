package org.school.diary.service;

import org.school.diary.dto.UserDTO;
import org.school.diary.model.common.PersonRelatedWithSchool;
import org.school.diary.model.common.User;

import java.util.List;

public interface UserService {


    public void save(User user);

    public List<User> listUsers();

    User saveNewUser(UserDTO userDTO);

//    User findUserById(long id);

    public void updatePassword(User user);

    public void updateAccount(User user);

    User findById(long id);

    User test(long id);

    User findByPersonRelatedWithSchool(PersonRelatedWithSchool personRelatedWithSchool);

    boolean existsUserByEmail(String email);

    boolean existsUserByPesel(String pesel);

//    Boolean existContractForPerson(@Param("pid") String personId);
}
