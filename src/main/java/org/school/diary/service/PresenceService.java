package org.school.diary.service;

import org.school.diary.model.Presence;

import java.util.List;

public interface PresenceService {

    List<Presence> findAll();

    List<Presence> generateEmptyPresencesForStudentsGroup(Integer groupId);

    void saveAll(List<Presence> presences);
}
