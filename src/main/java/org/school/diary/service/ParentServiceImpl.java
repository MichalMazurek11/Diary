package org.school.diary.service;


import lombok.AllArgsConstructor;
import org.school.diary.dao.LessonHourRepository;
import org.school.diary.dao.ParentRepository;
import org.school.diary.model.common.Parent;
import org.school.diary.model.common.PersonRelatedWithSchool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ParentServiceImpl implements ParentService{

    private ParentRepository parentRepository;

    @Override
    public void saveParent(Parent parent) {
        parentRepository.save(parent);
    }

    @Override
    public void save(PersonRelatedWithSchool parent) {
        parentRepository.save((Parent)parent);
    }

    @Override
    public void deleteParent(long id) {
        parentRepository.deleteById(id);
    }

    @Override
    public Parent findByLogin(String login) {
        return parentRepository.findByLogin(login);
    }
}
