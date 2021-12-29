package org.school.diary.service;

import org.school.diary.dao.PresenceRepository;
import org.school.diary.model.Presence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PresenceServiceImpl implements PresenceService {

    @Autowired
    PresenceRepository presenceRepository;

    @Override
    public List<Presence> findAll() {
        return presenceRepository.findAll();
    }
}
