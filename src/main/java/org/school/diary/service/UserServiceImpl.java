package org.school.diary.service;


import lombok.RequiredArgsConstructor;
import org.school.diary.dao.RoleRepository;
import org.school.diary.dao.UserRepository;
import org.school.diary.dto.UserDTO;
import org.school.diary.mappers.SignedUserMapper;
import org.school.diary.model.Parent;
import org.school.diary.model.Role;
import org.school.diary.model.common.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final SignedUserMapper signedUserMapper;
    private final RoleRepository roleRepository;

    private final TeacherService teacherService;
    private final ParentService parentService;
    private final StudentService studentService;
    private final DirectorService directorService;


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

    @Override
    public void saveNewUser(UserDTO userDTO) {
        List<Role> roles = roleRepository.findAll();
        Role userRole = roles.stream().filter(role -> role.getName().equalsIgnoreCase(userDTO.getPersonRole())).findFirst().orElseThrow(() -> new IllegalArgumentException("Podana rola nie istnieje w systemie"));
        PersonRelatedWithSchool personRelatedWithSchool = signedUserMapper.mapPersonRelatedWithSchoolToSpecificImplementation(userDTO);
        //TODO: stworzyć fabrykę serwisów
        if (personRelatedWithSchool instanceof Teacher){
            teacherService.save(personRelatedWithSchool);
        } else if (personRelatedWithSchool instanceof Parent){
            parentService.save(personRelatedWithSchool);

        } else if (personRelatedWithSchool instanceof Student){
            studentService.save(personRelatedWithSchool);

        } else if (personRelatedWithSchool instanceof Director){
            directorService.save(personRelatedWithSchool);
        }
        User user = new User();
        user.setRoles(Collections.singleton(userRole));
        user.setPersonRelatedWithSchool(personRelatedWithSchool);
        user.setPassword(userDTO.getPassword());
        userRepository.save(user);
    }


}
