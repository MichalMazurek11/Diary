package org.school.diary.service;


import org.school.diary.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{


    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public void save(User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        userRepository.save(user);
    }

    @Override
    public List<User> listUsers() {
        return null;
//        return userRepository.findAll();
    }
}
