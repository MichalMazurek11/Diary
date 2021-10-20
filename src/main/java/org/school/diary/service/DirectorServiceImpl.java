package org.school.diary.service;


import org.school.diary.dao.DirectorRepository;
import org.school.diary.model.Director;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DirectorServiceImpl implements DirectorService {

    @Autowired
    private DirectorRepository directorRepository;

    @Override
    public void save(Director director) {
        directorRepository.save(director);

    }

    @Override
    public void deleteDirector(long id) {
        directorRepository.deleteById(id);
    }


}
