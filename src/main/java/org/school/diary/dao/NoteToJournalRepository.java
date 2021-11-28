package org.school.diary.dao;


import org.school.diary.model.Mark;
import org.school.diary.model.NoteToJournal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteToJournalRepository extends JpaRepository<NoteToJournal,Long> {



}
