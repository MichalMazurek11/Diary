package org.school.diary.service;

import org.school.diary.dto.UserDTO;
import org.school.diary.model.common.User;

import java.util.List;

public interface UserService {


    public void save(User user);

    public List<User> listUsers();

    void saveNewUser(UserDTO userDTO);

}
