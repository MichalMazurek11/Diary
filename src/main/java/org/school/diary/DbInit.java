package org.school.diary;

import org.school.diary.model.common.Director;
import org.school.diary.service.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DbInit implements CommandLineRunner {

    @Autowired
    private DirectorService directorService;

    @Override
    public void run(String... args) throws Exception {
        Director director = new Director();

        director.setFirstName("Mariusz");
        director.setLastName("Mroczek");
        director.setPesel("98609736754");
        director.setDateBirth(LocalDate.parse("1980-01-01"));
        director.setEmail("dyrektor@wp.pl");
        directorService.save(director);

    }
}
