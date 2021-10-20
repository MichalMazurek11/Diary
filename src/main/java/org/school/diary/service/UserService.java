package org.school.diary.service;

import org.school.diary.model.Director;
import org.school.diary.model.User;

public interface UserService {


    public User findByEmail(String email);

    public void save(User user);
}
