package org.school.diary.service;

import org.school.diary.model.ClassRoom;
import org.school.diary.model.NoteToJournal;
import org.school.diary.model.common.Student;
import org.school.diary.model.common.Teacher;
import org.school.diary.model.common.User;

import java.util.List;

public interface NoteToJournalService {

    public void save(NoteToJournal noteToJournal);

    List<NoteToJournal> findAll();

    List<NoteToJournal> findAllByTeacher(Teacher teacher);

    public void deleteNoteToJournal(long id);

    List<NoteToJournal> findAllByStudent(Student student);

}

