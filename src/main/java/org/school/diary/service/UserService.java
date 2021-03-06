package org.school.diary.service;

import org.school.diary.model.Director;
import org.school.diary.model.User;

import java.util.List;

public interface UserService {


    public User findByEmail(String email);

    public void save(User user);

    public List<User> listUsers();
}
