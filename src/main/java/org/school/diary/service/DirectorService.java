package org.school.diary.service;

import org.school.diary.model.common.Director;
import org.school.diary.model.common.PersonRelatedWithSchool;

public interface DirectorService {


    public void save(PersonRelatedWithSchool director);

    public void deleteDirector(long id);
}
