package org.school.diary.service;


import lombok.RequiredArgsConstructor;
import org.school.diary.dao.DirectorRepository;
import org.school.diary.model.common.Director;
import org.school.diary.model.common.PersonRelatedWithSchool;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DirectorServiceImpl implements DirectorService {

    private final DirectorRepository directorRepository;


    @Override
    public void save(PersonRelatedWithSchool director) {
        directorRepository.save((Director) director);

    }

    @Override
    public void deleteDirector(long id) {
        directorRepository.deleteById(id);
    }


}
