package org.school.diary.service;

import org.school.diary.dto.UserDTO;
import org.school.diary.model.common.Student;
import org.school.diary.model.common.Teacher;
import org.school.diary.model.common.User;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserService {


    public void save(User user);

    public List<User> listUsers();

    void saveNewUser(UserDTO userDTO);

//    User findUserById(long id);

    public void updatePassword(User user);

    public void updateAccount(User user);

    User findById(long id);

    User test(long id);



//    Boolean existContractForPerson(@Param("pid") String personId);
}
