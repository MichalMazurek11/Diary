package org.school.diary.service;

import org.school.diary.dao.RoleRepository;
import org.school.diary.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    RoleRepository roleRepository;

    @Override
    public List<Role> listOfRoles() {
        return roleRepository.findAll();
    }
}
