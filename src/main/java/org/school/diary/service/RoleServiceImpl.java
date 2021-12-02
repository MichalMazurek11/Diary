package org.school.diary.service;

import org.school.diary.dao.RoleRepository;
import org.school.diary.model.Role;
import org.school.diary.model.Weekday;
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


    @Override
    public void saveAllRoles(List<Role> roles) {
        roleRepository.saveAll(roles);

    }

    @Override
    public Role findRoleByName(String name) {
        return roleRepository.findRoleByName(name);
    }
}
