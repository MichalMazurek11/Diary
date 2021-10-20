package org.school.diary.service;

import org.school.diary.model.Director;

public interface DirectorService {


    public void save(Director director);

    public void deleteDirector(long id);
}
