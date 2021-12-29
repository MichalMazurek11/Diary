package org.school.diary.dao;

import org.school.diary.model.Presence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PresenceRepository extends JpaRepository<Presence, Long> {

    @Override
    List<Presence> findAll();



}
