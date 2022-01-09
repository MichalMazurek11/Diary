package org.school.diary.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.school.diary.model.NoteToJournal;
import org.school.diary.model.common.Student;
import org.school.diary.model.common.Teacher;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClassGroupDTO {


    private long id;


    private String name;


    private Teacher supervisor;


    private Set<Student> students = new HashSet<>();

    private Set<NoteToJournal> noteToJournalsClassGroup = new HashSet<>();


}
