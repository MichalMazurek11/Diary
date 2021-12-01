package org.school.diary.service;


import org.school.diary.dao.NoteToJournalRepository;
import org.school.diary.model.NoteToJournal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteToJournalRepositoryImpl implements  NoteToJournalService{

    @Autowired
    NoteToJournalRepository noteToJournalRepository;

    @Override
    public void save(NoteToJournal noteToJournal) {
        noteToJournalRepository.save(noteToJournal);
    }
}
