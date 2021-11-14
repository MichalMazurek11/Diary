package org.school.diary.dao;


import org.school.diary.model.Role;
import org.school.diary.model.common.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {


}
