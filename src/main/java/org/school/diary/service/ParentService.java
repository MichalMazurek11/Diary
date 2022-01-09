package org.school.diary.service;

import org.school.diary.model.common.Parent;
import org.school.diary.model.common.PersonRelatedWithSchool;
import org.school.diary.model.common.Student;

public interface ParentService {

    public void saveParent(Parent parent);

    public void save(PersonRelatedWithSchool parent);

    public void deleteParent(long id);

    Parent findByLogin(String login);
}
