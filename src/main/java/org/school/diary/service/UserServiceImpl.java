package org.school.diary.service;


import lombok.RequiredArgsConstructor;
import org.school.diary.NotFoundException;
import org.school.diary.dao.RoleRepository;
import org.school.diary.dao.UserRepository;
import org.school.diary.dto.UserDTO;
import org.school.diary.mappers.SignedUserMapper;
import org.school.diary.model.common.Parent;
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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(user.getRoles());
        user.setPersonRelatedWithSchool(user.getPersonRelatedWithSchool());
        userRepository.save(user);
    }

    @Override
    public List<User> listUsers() {
//        return null;
        return userRepository.findAll();
    }

    @Override
    public void saveNewUser(UserDTO userDTO) {

        String personRole = userDTO.getPersonRole();
        Role role = roleRepository.findByName(personRole.toUpperCase()).orElseThrow(NotFoundException::new);
        PersonRelatedWithSchool personRelatedWithSchool = signedUserMapper.mapPersonRelatedWithSchoolToSpecificImplementation(userDTO);

        //TODO: stworzyć fabrykę serwisów
        if (personRelatedWithSchool instanceof Teacher){
            personRelatedWithSchool.setLogin(userDTO.getPesel());
            teacherService.save(personRelatedWithSchool);
        } else if (personRelatedWithSchool instanceof Parent){
            personRelatedWithSchool.setLogin(userDTO.getPesel()+"r");
            parentService.save(personRelatedWithSchool);

        } else if (personRelatedWithSchool instanceof Student){
            personRelatedWithSchool.setLogin(userDTO.getPesel());
            studentService.save(personRelatedWithSchool);

        } else if (personRelatedWithSchool instanceof Director){
            personRelatedWithSchool.setLogin(userDTO.getPesel());
            directorService.save(personRelatedWithSchool);
        }
        User user = new User();
        user.setRoles(Collections.singleton(role));
        user.setPersonRelatedWithSchool(personRelatedWithSchool);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(user);
    }

//    @Override
//    public User findUserById(long id) {
//        return userRepository.findUserById(id);
//    }

    @Override
    public void updatePassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
      //  user.setPersonRelatedWithSchool(user.getPersonRelatedWithSchool());
        this.userRepository.save(user);
    }

    @Override
    public void updateAccount(User user) {
        this.userRepository.save(user);
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public User test(long id) {
        return userRepository.test(id);
    }

    @Override
    public User findByPersonRelatedWithSchool(PersonRelatedWithSchool personRelatedWithSchool) {
        return userRepository.findByPersonRelatedWithSchool(personRelatedWithSchool);
    }

    @Override
    public boolean existsUserByPersonRelatedWithSchoolEmail(String email) {
        return userRepository.existsUserByPersonRelatedWithSchoolEmail(email);
    }

    @Override
    public boolean existsUserByPersonRelatedWithSchoolPesel(String pesel) {
        return userRepository.existsUserByPersonRelatedWithSchoolPesel(pesel);
    }


//    @Override
//    public Boolean existContractForPerson(String personId) {
//        return userRepository.existContractForPerson(personId);
//    }


}
