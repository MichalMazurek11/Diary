package org.school.diary.service;


import org.school.diary.dao.NoteToJournalRepository;
import org.school.diary.model.NoteToJournal;
import org.school.diary.model.common.Student;
import org.school.diary.model.common.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteToJournalServiceImpl implements  NoteToJournalService{

    @Autowired
    NoteToJournalRepository noteToJournalRepository;

    @Override
    public void save(NoteToJournal noteToJournal) {
        noteToJournalRepository.save(noteToJournal);
    }

    @Override
    public List<NoteToJournal> findAll() {
        return noteToJournalRepository.findAll();
    }

    @Override
    public List<NoteToJournal> findAllByTeacher(Teacher teacher) {
        return noteToJournalRepository.findAllByTeacher(teacher);
    }

    @Override
    public void deleteNoteToJournal(long id) {
        noteToJournalRepository.deleteById(id);
    }

    @Override
    public List<NoteToJournal> findAllByStudent(Student student) {
        return noteToJournalRepository.findAllByStudent(student);
    }


}
