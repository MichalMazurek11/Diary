package org.school.diary.dao;


import org.school.diary.model.Mark;
import org.school.diary.model.NoteToJournal;
import org.school.diary.model.common.Student;
import org.school.diary.model.common.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteToJournalRepository extends JpaRepository<NoteToJournal,Long> {


        List<NoteToJournal> findAllByTeacher(Teacher teacher);

        List<NoteToJournal> findAllByStudent(Student student);
}
