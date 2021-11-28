package org.school.diary.service;


import org.school.diary.dao.ParentRepository;
import org.school.diary.model.common.Parent;
import org.school.diary.model.common.PersonRelatedWithSchool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParentServiceImpl implements ParentService{

    @Autowired
    private ParentRepository parentRepository;

    @Override
    public void save(PersonRelatedWithSchool parent) {
        parentRepository.save((Parent)parent);
    }

    @Override
    public void deleteParent(long id) {
        parentRepository.deleteById(id);
    }
}
