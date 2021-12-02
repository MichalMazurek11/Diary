package org.school.diary.service;


import org.school.diary.model.Role;
import org.school.diary.model.Weekday;

import java.util.List;

public interface RoleService {

    public List<Role> listOfRoles();

    void saveAllRoles(List<Role> roles);

    Role findRoleByName(String name);
}
